package com.zhuliyi.app.wishwall.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.zhuliyi.app.wishwall.R;
import com.zhuliyi.app.wishwall.adapter.WishAdapter;
import com.zhuliyi.app.wishwall.manager.ToastManager;

/**
 * 心愿榜
 * by zhuliyi
 * on 2015/11/29
 */
public class WishFragment extends Fragment {
    private static WishFragment fragment;
    private View viewLayout;
    private PullToRefreshListView listView;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment WishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WishFragment newInstance() {
        if(fragment==null) {
            fragment = new WishFragment();
        }
        return fragment;
    }

    public WishFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewLayout=inflater.inflate(R.layout.fragment_wish, container, false);
        initListView();
        return viewLayout;

    }

    /**
     * 初始化和监听ListView
     */
    private void initListView(){
        listView= (PullToRefreshListView) viewLayout.findViewById(R.id.listView_wish);
        listView.setAdapter(new WishAdapter(getContext()));
        //设置点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastManager.showToast(getContext(),"这是第"+position+"个", Toast.LENGTH_LONG);
            }
        });
        //同时实现下拉刷新和上拉加载,实现OnRefreshListener2
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        // 处理刷新任务
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void reslst)
                    {
                        // 更行内容，通知 PullToRefresh 刷新结束
                        listView.onRefreshComplete();
                    }
                }.execute();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {

            }


        });
        //设置PullRefreshListView上提加载时的加载提示
        listView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载...");
        listView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载...");
        listView.getLoadingLayoutProxy(false, true).setReleaseLabel("松开加载更多...");
        // 设置PullRefreshListView下拉加载时的加载提示
        listView.getLoadingLayoutProxy(true, false).setPullLabel("下拉刷新...");
        listView.getLoadingLayoutProxy(true, false).setRefreshingLabel("正在刷新...");
        listView.getLoadingLayoutProxy(true, false).setReleaseLabel("松开刷新...");
    }





}
