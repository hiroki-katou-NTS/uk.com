package nts.uk.ctx.sys.assist.app.command.mastercopy;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.sys.assist.dom.mastercopy.CopyMethod;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyData;
import nts.uk.ctx.sys.assist.dom.mastercopy.MasterCopyDataRepository;
import nts.uk.ctx.sys.assist.dom.mastercopy.TargetTableInfo;
import nts.uk.ctx.sys.assist.dom.mastercopy.handler.CopyDataRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class MasterCopyDataCommandHanlder extends AsyncCommandHandler<MasterCopyDataCommand> {

	/** The master copy data finder. */
	@Inject
	private MasterCopyDataRepository repository;

	@Inject
	CopyDataRepository copyDataRepository;

	/** The Constant NUMBER_OF_SUCCESS. */
	private static final String NUMBER_OF_SUCCESS = "NUMBER_OF_SUCCESS";

	/** The Constant NUMBER_OF_ERROR. */
	private static final String NUMBER_OF_ERROR = "NUMBER_OF_ERROR";

	/** The Constant DEFAULT_VALUE. */
	private static final int DEFAULT_VALUE = 0;

	/** The Constant DATA_PREFIX. */
//	private static final String DATA_PREFIX = "DATA_";

	/** The Constant MAX_ERROR_RECORD. */
//	private static final int MAX_ERROR_RECORD = 5;

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

        Map<Integer, Integer> categoryCopyMethod = command.getMasterDataList().stream()
                .collect(Collectors.toMap(MasterCopyCategoryDto::getMasterCopyId, MasterCopyCategoryDto::getCopyMethod));

        new ArrayList<>(categoryCopyMethod.keySet());

        List<MasterCopyData> masterCopyDataList = repository.findByListCategoryNo(new ArrayList<>(categoryCopyMethod.keySet()));


        if (CollectionUtil.isEmpty(masterCopyDataList)) {
            for (MasterCopyCategoryDto categoryDto : command.getMasterDataList()) {
                if (isInterrupted) break;
                ErrorContentDto errorContentDto = createErrorReport(categoryDto);
                errorList.add(errorContentDto);
			}
		} else {
			//初期値コピー処理
			for (MasterCopyData masterCopyData : masterCopyDataList) {
				for (TargetTableInfo targetTableInfo : masterCopyData.getTargetTables()) {
					if (isInterrupted) break;
					copyDataRepository.copy(companyId, targetTableInfo,
							categoryCopyMethod.get(masterCopyData.getCategoryNo().v()));

				}
			}
		}
	}

    private ErrorContentDto createErrorReport(MasterCopyCategoryDto categoryDto) {
        ErrorContentDto errorContentDto = new ErrorContentDto();
        errorContentDto.setCategoryName(categoryDto.getCategoryName());
        errorContentDto.setOrder(categoryDto.getOrder());
        errorContentDto.setSystemType(categoryDto.getSystemType().toString());
        errorContentDto.setMessage(TextResource.localize("Msg_1146"));
        return errorContentDto;
    }

	public void interrupt() {
		isInterrupted = true;
	}

}
