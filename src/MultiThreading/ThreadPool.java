package MultiThreading;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
	public static void main(String[] args) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(2,4,5,TimeUnit.MINUTES,new ArrayBlockingQueue<>(2),
															new CustomThreadFactory(),new CustomRejectHandler());
		executor.allowCoreThreadTimeOut(true);
		
		for(int i =1;i<=7;i++) {
			executor.submit(()->{
				try {
				Thread.sleep(2000);
				}catch(Exception e) {
					//handle
				}
				System.out.println("Task processed by:"+ Thread.currentThread().getName());
			});
			
		}
		
			executor.shutdown();
	}
}

 class CustomThreadFactory implements ThreadFactory{

	@Override
	public Thread newThread(Runnable r) {
			Thread th = new Thread(r);
			//th.setName("custom :"+Thread.currentThread().getName());
			th.setPriority(Thread.NORM_PRIORITY);
			th.setDaemon(false);
		return th;
	}
	
}
 
 class CustomRejectHandler implements RejectedExecutionHandler{

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		System.out.println("Task Rejected: "+r.toString());
		
	}
	 
 }