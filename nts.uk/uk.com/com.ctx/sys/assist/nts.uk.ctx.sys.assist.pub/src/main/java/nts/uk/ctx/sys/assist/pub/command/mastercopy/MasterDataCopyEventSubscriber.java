/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.assist.pub.command.mastercopy;

import java.util.List;

import javax.inject.Inject;

import nts.arc.layer.dom.event.DomainEventSubscriber;
import nts.arc.task.AsyncTaskInfo;
import nts.arc.task.AsyncTaskInfoRepository;
import nts.arc.task.AsyncTaskInfoService;
import nts.arc.task.data.AsyncTaskData;

/**
 * The Class VacationMasterDataCopyEventSubscriber.
 */
public abstract class MasterDataCopyEventSubscriber
		implements DomainEventSubscriber<GlobalMasterDataCopyEvent> {

	/** The service. */
	@Inject
	private AsyncTaskInfoService service;

	/** The repository. */
	@Inject
	private AsyncTaskInfoRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.dom.event.DomainEventSubscriber#subscribedToEventType()
	 */
	@Override
	public Class<GlobalMasterDataCopyEvent> subscribedToEventType() {
		return GlobalMasterDataCopyEvent.class;
	}

	/**
	 * Copy master data.
	 *
	 * @param taskId
	 *            the task id
	 * @param repo
	 *            the repo
	 */
	public void copyDataWithLog(String taskId, GlobalCopyTargetItem targetItem, Runnable repo) {

		AsyncTaskInfo info = service.find(taskId);
		if (info.isRequestedToCancel())
			return; // if it is cancelled, do not process

		String taskKey = MasterCopyTaskStatusPubDto.DB_PREFIX
				+ targetItem.getMasterCopyId();

		// "key" should be unique among tasks, I use "prefix + master copy Id"
		// create data for this task
		MasterCopyTaskStatusPubDto dto = new MasterCopyTaskStatusPubDto();
		dto.setStatus(MasterCopyTaskStatusPubDto.STATUS_PROCESSING);
		repository.insertTaskData(taskId, new AsyncTaskData(taskKey, dto.toString()));

		try {
			repo.run();
		} catch (Exception e) {
			dto.setMessage(e.getMessage());
			dto.setStatus(MasterCopyTaskStatusPubDto.STATUS_ABORTED);
			// update number of error
			// repository.updateTaskData(taskId,
			// new AsyncTaskData(MasterCopyTaskStatusPubDto.NUMBER_OF_ERROR,
			// String.valueOf(data.getValueAsNumber() + 1)));
		}

		// TODO: Xử lý tạo log, log lỗi nếu có.

		// finished the task
		dto.setStatus(MasterCopyTaskStatusPubDto.STATUS_FINISHED);
		repository.updateTaskData(taskId, new AsyncTaskData(taskKey, dto.toString()));

		// update the dto into DB
		// get task data of current task
		List<AsyncTaskData> datas = repository.getTaskData(taskId);
		for (AsyncTaskData data : datas) {
			// update number of success
			if (MasterCopyTaskStatusPubDto.NUMBER_OF_SUCCESS.equals(data.getKey())) {
				repository.updateTaskData(taskId,
						new AsyncTaskData(MasterCopyTaskStatusPubDto.NUMBER_OF_SUCCESS,
								String.valueOf(data.getValueAsNumber() + 1)));
				// update the data into DB
			}
		}
	}

}