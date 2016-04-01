package com.xmx.homedoctor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.xmx.homedoctor.Constants;
import com.xmx.homedoctor.Patients.Patient;
import com.xmx.homedoctor.Patients.PatientsAdapter;
import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Tools.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientsFragment extends BaseFragment
        implements BGARefreshLayout.BGARefreshLayoutDelegate {

    BGARefreshLayout mRefreshLayout;
    ListView mList;
    PatientsAdapter mAdapter;
    boolean loadedFlag = false;
    boolean allFlag = false;
    ArrayList<Patient> mItems = new ArrayList<>();

    @Override
    public void onResume() {
        super.onResume();
        onBGARefreshLayoutBeginRefreshing(mRefreshLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patients, container, false);

        mList = (ListView) view.findViewById(R.id.patients_list);
        initPatientsList();

        mRefreshLayout = (BGARefreshLayout) view.findViewById(R.id.patients_refresh);
        initRefreshLayout();

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Patient item = (Patient) parent.getItemAtPosition(position);
                if (item != null && item.getId() != null) {
                    showToast(item.getId());
                }
            }
        });

        return view;
    }

    private void initPatientsList() {
        mItems.add(new Patient(null, getString(R.string.loading), getString(R.string.loading)));
        mAdapter = new PatientsAdapter(getContext(), mItems);
        mList.setAdapter(mAdapter);

        AVQuery<AVObject> query = createQuery(false);
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                mItems.clear();
                if (e == null) {
                    for (int i = 0; i < avObjects.size(); ++i) {
                        Patient item = createItem(avObjects.get(i));
                        mItems.add(item);
                    }
                } else {
                    filterException(e);
                }
                mAdapter.setItems(mItems);

                loadedFlag = true;
            }
        });
    }

    private void initRefreshLayout() {
        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGARefreshViewHolder refreshViewHolder = new BGANormalRefreshViewHolder(getContext(), true);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);

        // 设置正在加载更多时的文本
        refreshViewHolder.setLoadingMoreText("正在加载");

        // 为了增加下拉刷新头部和加载更多的通用性，提供了以下可选配置选项  -------------START
        // 设置整个加载更多控件的背景颜色资源id
        //refreshViewHolder.setLoadMoreBackgroundColorRes(loadMoreBackgroundColorRes);
        // 设置整个加载更多控件的背景drawable资源id
        //refreshViewHolder.setLoadMoreBackgroundDrawableRes(loadMoreBackgroundDrawableRes);
        // 设置下拉刷新控件的背景颜色资源id
        //refreshViewHolder.setRefreshViewBackgroundColorRes(refreshViewBackgroundColorRes);
        // 设置下拉刷新控件的背景drawable资源id
        //refreshViewHolder.setRefreshViewBackgroundDrawableRes(refreshViewBackgroundDrawableRes);
        // 设置自定义头部视图（也可以不用设置）     参数1：自定义头部视图（例如广告位）， 参数2：上拉加载更多是否可用
        //mRefreshLayout.setCustomHeaderView(mBanner, false);
        // 可选配置  -------------END
    }

    private AVQuery<AVObject> createQuery(boolean loadFlag) {
        AVQuery<AVObject> query = new AVQuery<>("PatientsData");
        //query.whereEqualTo("status", 0);
        if (loadFlag) {
            query.setSkip(mItems.size());
        }
        query.limit(Constants.LONGEST_LOAD_TIME);
        query.orderByDescending("createdAt");

        return query;
    }

    private Patient createItem(AVObject avObject) {
        String id = avObject.getObjectId();
        String username = avObject.getString("username");
        String nickname = avObject.getString("nickname");
        return new Patient(id, username, nickname);
    }

    private void refresh() {
        AVQuery<AVObject> query = createQuery(false);
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    boolean flag = false;
                    for (int i = avObjects.size() - 1; i >= 0; --i) {
                        Patient item = createItem(avObjects.get(i));
                        if (!mItems.contains(item)) {
                            mItems.add(0, item);
                            flag = true;
                        }
                    }

                    if (flag) {
                        mAdapter.setItems(mItems);
                    } else {
                        showToast(R.string.no_more);
                    }
                } else {
                    filterException(e);
                }
                mRefreshLayout.endRefreshing();
            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        if (loadedFlag) {
            refresh();
        } else {
            mRefreshLayout.endRefreshing();
        }
    }

    private void loadMore() {
        AVQuery<AVObject> query = createQuery(true);
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    boolean flag = false;
                    for (int i = 0; i < avObjects.size(); ++i) {
                        Patient item = createItem(avObjects.get(i));
                        if (!mItems.contains(item)) {
                            mItems.add(item);
                            flag = true;
                        }
                    }

                    if (flag) {
                        mAdapter.setItems(mItems);
                    } else {
                        allFlag = true;
                        showToast("已加载全部");
                    }
                } else {
                    filterException(e);
                }
                mRefreshLayout.endLoadingMore();
            }
        });
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        if (loadedFlag) {
            if (!allFlag) {
                loadMore();
                return true;
            } else {
                showToast(R.string.already_all);
                return false;
            }
        } else {
            return false;
        }
    }
}
