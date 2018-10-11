package com.hyphenate.easeui.widget.chatrow;

import android.os.AsyncTask;

import java.util.concurrent.Executor;

/**
 * <pre>
 *      作者  ：肖坤
 *      时间  ：2018/10/10
 *      描述  ：
 *      版本  ：1.0
 * </pre>
 */
public final class AsyncTaskCompat {

    /**
     * Executes the task with the specified parameters, allowing multiple tasks to run in parallel
     * on a pool of threads managed by {@link android.os.AsyncTask}.
     *
     * @param task   The {@link android.os.AsyncTask} to execute.
     * @param params The parameters of the task.
     * @return the instance of AsyncTask.
     * @deprecated Use {@link android.os.AsyncTask#executeOnExecutor(Executor, Object[])} directly.
     */
    @Deprecated
    public static <Params, Progress, Result> AsyncTask<Params, Progress, Result> executeParallel(
            AsyncTask<Params, Progress, Result> task,
            Params... params) {
        if (task == null) {
            throw new IllegalArgumentException("task can not be null");
        }
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);

        return task;
    }

    private AsyncTaskCompat() {
    }

}
