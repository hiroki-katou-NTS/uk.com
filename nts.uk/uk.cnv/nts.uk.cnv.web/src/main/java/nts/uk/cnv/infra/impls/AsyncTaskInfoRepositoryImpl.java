package nts.uk.cnv.infra.impls;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.task.AsyncTaskInfo;
import nts.arc.task.AsyncTaskInfoRepository;
import nts.arc.task.AsyncTaskStatus;
import nts.arc.task.data.AsyncTaskData;
import nts.arc.time.GeneralDateTime;

@Stateless
public class AsyncTaskInfoRepositoryImpl implements AsyncTaskInfoRepository {

	@Override
	public Optional<AsyncTaskInfo> find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(AsyncTaskInfo taskInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AsyncTaskInfo taskInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAllTaskData(String taskId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTaskData(String taskId, String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertTaskData(String taskId, AsyncTaskData data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTaskData(String taskId, AsyncTaskData data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<AsyncTaskData> getTaskData(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeOldTasks(GeneralDateTime baseDateTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AsyncTaskStatus getStatus(String taskId) {
		// TODO Auto-generated method stub
		return null;
	}

}
