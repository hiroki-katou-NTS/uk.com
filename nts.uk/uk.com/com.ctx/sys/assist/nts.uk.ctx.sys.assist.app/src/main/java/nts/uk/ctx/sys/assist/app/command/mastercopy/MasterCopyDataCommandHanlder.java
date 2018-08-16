package nts.uk.ctx.sys.assist.app.command.mastercopy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyTargetItem;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterDataCopyEvent;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterDataCopyEvent.MasterDataCopyEventBuilder;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class MasterCopyDataCommandHanlder extends AsyncCommandHandler<MasterCopyDataCommand> {

	/** The master copy data finder. */
	@Inject
	private MasterCopyDataRepository repository;

	/** The Constant NUMBER_OF_SUCCESS. */
	private static final String NUMBER_OF_SUCCESS = "NUMBER_OF_SUCCESS";

	/** The Constant NUMBER_OF_ERROR. */
	private static final String NUMBER_OF_ERROR = "NUMBER_OF_ERROR";

	/** The Constant DEFAULT_VALUE. */
	private static final int DEFAULT_VALUE = 0;

	/** The Constant DATA_PREFIX. */
	private static final String DATA_PREFIX = "DATA_";

	/** The Constant MAX_ERROR_RECORD. */
	private static final int MAX_ERROR_RECORD = 5;

	/** Interrupt flag */
	private static boolean isInterrupted = false;

	@Override
	protected void handle(CommandHandlerContext<MasterCopyDataCommand> context) {

		val asyncTask = context.asAsync();
		TaskDataSetter setter = asyncTask.getDataSetter();

		// get company id
		String companyId = AppContexts.user().companyId();

		// get command
		MasterCopyDataCommand command = context.getCommand();

		// setup data object
		PerCopyDataCorrectProcessDto dto = new PerCopyDataCorrectProcessDto();
		dto.setEndTime(GeneralDateTime.now());
		dto.setStartTime(GeneralDateTime.now());
		dto.setExecutionState(ExecutionState.PROCESSING);

		// Creating list of errors
		List<ErrorContentDto> errorList = new ArrayList<>();

		dto.setErrors(errorList);
		dto.setWithError(WithError.NO_ERROR);

		int countSuccess = DEFAULT_VALUE;
		int countError = DEFAULT_VALUE;

		// Variable to count amount of list of error records sent to DB
		int errorRecordCount = DEFAULT_VALUE;

		setter.setData(NUMBER_OF_SUCCESS, countSuccess);
		setter.setData(NUMBER_OF_ERROR, DEFAULT_VALUE);

		// Set interrupt flag to false to start execution
		isInterrupted = false;

		MasterDataCopyEventBuilder eventBuilder = MasterDataCopyEvent.builder();
		eventBuilder.companyId(command.getCompanyId());
		eventBuilder.taskId("taskId");
		
		Map<String, Integer> categoryCopyMethod = command.getMasterDataList().stream().collect(Collectors.toMap(MasterCopyCategoryDto::getMasterCopyId, MasterCopyCategoryDto::getCopyMethod));
		
		List<CopyTargetItem> copyTargetList = repository
				.findByMasterCopyIds(
						categoryCopyMethod.keySet().stream().collect(Collectors.toList()))
				.stream()
				.map(item -> new CopyTargetItem(item.getMasterCopyId(),
						item.getMasterCopyTarget().v(),
						CopyMethod.valueOf(categoryCopyMethod.get(item.getMasterCopyId()))))
				.collect(Collectors.toList());
		
		eventBuilder.copyTargetList(copyTargetList);

//		for (MasterCopyCategoryDto listData : command.getMasterDataList()) {
//			// Stop if being interrupted
//			if (isInterrupted) {
//				break;
//			}
//
//			Optional<MasterCopyData> masterCopyData = repository.findByMasterCopyId(listData.getMasterCopyId());
//			if (!masterCopyData.isPresent()) {
//
//				ErrorContentDto errorContentDto = new ErrorContentDto();
//				errorContentDto.setCategoryName(listData.getCategoryName());
//				errorContentDto.setOrder(listData.getOrder());
//				errorContentDto.setSystemType(listData.getSystemType().toString());
//				errorContentDto.setMessage(TextResource.localize("Msg_1146"));
//
//				// Add to error list (save to DB every 5 error records)
//				if (errorList.size() >= MAX_ERROR_RECORD) {
//					errorRecordCount++;
//					setter.setData(DATA_PREFIX + errorRecordCount, dto);
//
//					// Clear the list for the new batch of error record
//					errorList.clear();
//				}
//				countError += 1;
//				errorList.add(errorContentDto);
//				setter.updateData(NUMBER_OF_ERROR, countError); // update
//																		// the
//																		// number
//																		// of
//																		// errors
//				if (errorList.size() == 1)
//					dto.setWithError(WithError.WITH_ERROR); // if there is even
//															// one error, output
//															// it
//			} else {
//				countSuccess++;
//				setter.updateData(NUMBER_OF_SUCCESS, countSuccess);
//			}
//
//		}
		
		eventBuilder.build().toBePublished();

		// Send the last batch of errors if there is still records unsent
		if (!errorList.isEmpty()) {
			errorRecordCount++;
			setter.setData(DATA_PREFIX + errorRecordCount, dto);
		}

		dto.setEndTime(GeneralDateTime.now());
		dto.setExecutionState(ExecutionState.DONE);
		// setter.updateData(DATA_EXECUTION, dto);
	}

	public void interrupt() {
		isInterrupted = true;
	}

}
