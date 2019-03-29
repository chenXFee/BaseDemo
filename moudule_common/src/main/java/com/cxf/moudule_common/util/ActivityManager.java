package com.cxf.moudule_common.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/***
 * activity队列管理
 */
public class ActivityManager {

    private static Stack<Activity> activityStack;
    private static ActivityManager instance;

    /**
     * 实例化一个activity管理类
     *
     * @return
     */
    public static synchronized ActivityManager getActivityManager() {

        if (instance == null) {
            instance = new ActivityManager();
            activityStack = new Stack<Activity>();
        }
        return instance;
    }

    /**
     * 回退到某个页面
     *
     * @param
     */
    public void backToActivity(Class<?> cls) {

        int index = -1;
        int size = activityStack.size();
        Activity activity = null;
        for (int i = 0; i < size; i++) {
            activity = activityStack.get(i);
            if (activity.getClass().equals(cls)) {
                index = i;
            }
        }

        if (index != -1) {
            for (int i = index + 1; i < size; i++) {
                activity = activityStack.get(index + 1);
                removeActivity(activity);
            }
        }
    }

    /**
     * 把activity加入到管理类堆栈底,如果已经存在堆栈中将推入栈顶
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        if (activity != null) {
            int i = activityStack.indexOf(activity);
            if (i != -1) {
                activityStack.add(activity);
            } else {
                activityStack.push(activity);
            }

        }
    }

    /**
     * 返回堆栈中activity的数量
     *
     * @return
     */
    public int getActivityNum() {
        return activityStack.size();
    }

    /**
     * 将activity推入栈顶
     *
     * @param activity
     */
    public void pushActivity(Activity activity) {
        int i = activityStack.indexOf(activity);
        if (i != -1) {
            activityStack.remove(i);
            activityStack.push(activity);
        }
    }

    /**
     * 获得栈顶的activity
     *
     * @return
     */
    public Activity getTopActivity() {
        if ((activityStack != null) && (activityStack.size() > 0)) {
            return (Activity) activityStack.lastElement();
        } else {
            return null;
        }
    }

    public Activity getBottomActivity() {
        if ((activityStack != null) && (activityStack.size() > 0)) {
            return (Activity) activityStack.firstElement();
        } else {
            return null;
        }
    }

    /**
     * 退出栈中指定activity
     *
     * @param activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            int i = activityStack.indexOf(activity);
            if (i != -1) {
                activity.finish();
                activityStack.remove(activity);
                activity = null;
            }
        }
    }

    public void removeActivity(Class<?> cls) {
        int index = -1;
        int size = activityStack.size();
        Activity activity = null;
        for (int i = 0; i < size; i++) {
            activity = activityStack.get(i);
            if (activity.getClass().equals(cls)) {
                index = i;
            }
        }

        if (index != -1) {
            activity = activityStack.get(index);
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 清除掉除cls以外的其他类 全部退出的时候要使用removeAllActivity()
     *
     * @param cls
     */
    public void removeAllActivityExceptOne(Class<?> cls) {
        int size = activityStack.size();
        Activity remain = null;
        for (int i = 0; i < size; i++) {
            Activity activity = getBottomActivity();
            if (activity == null) {
                break;
            }
            if (activity.getClass().equals(cls)) {
                remain = activity;
                activityStack.remove(activity);
                continue;
            }
            removeActivity(activity);
        }
        if (remain != null) {
            addActivity(remain);
        }
    }

    public void removeAllActivityExcept(Class<?>... classs){
        int size = activityStack.size();
        List<Activity> Alist= new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Activity activity = activityStack.get(i);
            if (activity == null) {
                break;
            }
            boolean isContain = false;
            for (Class<?> cls : classs){
                if (activity.getClass().equals(cls)) {
                    Alist.add(activity);
                    isContain = true;
                    break;
                }
            }
           if (!isContain){
               activity.finish();
           }
        }
        activityStack.clear();
        activityStack.addAll(Alist);
    }

    /**
     * 计算栈中指定类(cls)对象的个数 add by chenkehui @2013.07.10
     */
    public int activityCount(Class<?> cls) {
        int count = 0;
        int size = activityStack.size();
        Activity activity = null;
        for (int i = 0; i < size; i++) {
            activity = activityStack.get(i);
            if (activity.getClass().equals(cls)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 退出所有activity
     */
    public void removeAllActivity() {
        int size = activityStack.size();
        for (int i = 0; i < size; i++) {
            Activity activity = getBottomActivity();
            if (activity != null) {
                removeActivity(activity);
            }
        }
        System.gc();
    }

}
