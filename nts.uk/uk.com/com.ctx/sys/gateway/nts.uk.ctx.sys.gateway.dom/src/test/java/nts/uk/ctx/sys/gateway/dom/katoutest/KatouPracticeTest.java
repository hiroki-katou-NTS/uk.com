package nts.uk.ctx.sys.gateway.dom.katoutest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.enterprise.concurrent.ManagedExecutorService;

import org.junit.Test;

import nts.arc.task.parallel.internal.ParallelWithManagedThread;

public class KatouPracticeTest {

	
	@Test
	public void test() {
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		ManagedExecutorService managedExecutor = new ManagedExecutorService() {
			
			@Override
			public void execute(Runnable command) {
				executor.execute(command);
			}
			
			@Override
			public <T> Future<T> submit(Runnable task, T result) {
				return executor.submit(task, result);
			}
			
			@Override
			public Future<?> submit(Runnable task) {
				return executor.submit(task);
			}
			
			@Override
			public <T> Future<T> submit(Callable<T> task) {
				return executor.submit(task);
			}
			
			@Override
			public List<Runnable> shutdownNow() {
				return executor.shutdownNow();
			}
			
			@Override
			public void shutdown() {
				executor.shutdown();
			}
			
			@Override
			public boolean isTerminated() {
				return executor.isTerminated();
			}
			
			@Override
			public boolean isShutdown() {
				return executor.isShutdown();
			}
			
			@Override
			public <T> T invokeAny(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
					throws InterruptedException, ExecutionException, TimeoutException {
				return executor.invokeAny(tasks, timeout, unit);
			}
			
			@Override
			public <T> T invokeAny(Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
				return executor.invokeAny(tasks);
			}
			
			@Override
			public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
					throws InterruptedException {
				return executor.invokeAll(tasks,timeout,unit);
			}
			
			@Override
			public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks) throws InterruptedException {
				return executor.invokeAll(tasks);
			}
			
			@Override
			public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
				return executor.awaitTermination(timeout, unit);
			}
		};
		
		List<Integer> parentSource = new ArrayList<>();
		for(int i = 1 ; i<= 1; i++) {
			parentSource.add(i);
		}
		
		List<Integer> childSource = new ArrayList<>();
		for(int i = 1 ; i<= 30; i++) {
			childSource.add(i);
		}
		
		ParallelWithManagedThread.forEach(managedExecutor, parentSource, parent ->{
				System.out.println("parent:" + parent);
				System.out.println(Thread.currentThread().toString());
				ParallelWithManagedThread.forEach(managedExecutor, childSource, child ->{
						System.out.println("child:" + parent);
						System.out.println(Thread.currentThread().toString());
				});
				System.out.println("Child2へ");
				ParallelWithManagedThread.forEach(managedExecutor, childSource, child2 ->{
					System.out.println("child2:" + parent);
					System.out.println(Thread.currentThread().toString());
				});		
				System.out.println("次のパラレルへ");
		});
		System.out.println("終了");
	}
}
