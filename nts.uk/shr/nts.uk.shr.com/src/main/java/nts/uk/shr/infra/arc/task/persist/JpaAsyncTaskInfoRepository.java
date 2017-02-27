package nts.uk.shr.infra.arc.task.persist;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.task.AsyncTaskError;
import nts.arc.task.AsyncTaskInfo;
import nts.arc.task.AsyncTaskInfoRepository;
import nts.arc.task.AsyncTaskStatus;
import nts.arc.time.GeneralDateTime;
import nts.gul.util.Nullable;

@Stateless
public class JpaAsyncTaskInfoRepository extends JpaRepository implements AsyncTaskInfoRepository {

	@Override
	public Optional<AsyncTaskInfo> find(String id) {
		return this.queryProxy().find(id, CisdtAsyncTask.class)
				.map(e -> toDomain(e));
	}

	@Override
	public void add(AsyncTaskInfo taskInfo) {
		this.commandProxy().insert(toEntity(taskInfo));
	}

	@Override
	public void update(AsyncTaskInfo taskInfo) {
		this.commandProxy().update(toEntity(taskInfo));
	}
	
	private static AsyncTaskInfo toDomain(CisdtAsyncTask entity) {
		return new AsyncTaskInfo(
				entity.taskId,
				EnumAdaptor.valueOf(entity.taskSts, AsyncTaskStatus.class),
				GeneralDateTime.localDateTime(entity.createdAt),
				Nullable.get(entity.startedAt, d -> GeneralDateTime.localDateTime(d)),
				Nullable.get(entity.finishedAt, d -> GeneralDateTime.localDateTime(d)),
				Nullable.get(entity.abort, d -> toDomainAbort(d)));
	}
	
	private static AsyncTaskError toDomainAbort(CisdtAsyncTaskAbort entity) {
		return new AsyncTaskError(
				entity.errorType == 1,
				entity.errorMessage,
				entity.messageId);
	}

	private static CisdtAsyncTask toEntity(AsyncTaskInfo taskInfo) {
		val entity = new CisdtAsyncTask();
		entity.taskId = taskInfo.getId();
		entity.taskSts = taskInfo.getStatus().value;
		entity.createdAt = taskInfo.getCreatedAt().localDateTime();
	    entity.startedAt = Nullable.get(taskInfo.getStartedAt(), d -> d.localDateTime());
		entity.finishedAt = Nullable.get(taskInfo.getFinishedAt(), d -> d.localDateTime());
		entity.abort = Nullable.get(taskInfo.getError(), d -> toEntityAbort(taskInfo.getId(), d));
		
		return entity;
	}
	
	private static CisdtAsyncTaskAbort toEntityAbort(String id, AsyncTaskError domain) {
		val entity = new CisdtAsyncTaskAbort();
		entity.taskId = id;
		entity.errorType = domain.isBusinessException() ? 1 : 0;
		entity.messageId = domain.getMessageId();
		entity.errorMessage = domain.getMessage();
		
		return entity;
	}
}
