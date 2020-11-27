package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.val;
import nts.arc.i18n.I18NText;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.CompanyDeterminationProcess;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.DataExtractionService;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.EmployeeRestoration;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.ServerUploadProcessingService;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.TableItemValidation;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.TableListRestorationService;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.ThresholdConfigurationCheck;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class SyncServerUploadProcessingCommandHandler extends AsyncCommandHandler<SyncServerUploadProcessingCommand> {

	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;

	@Inject
	private ServerUploadProcessingService serverUploadProcessingService;

	@Inject
	private DataExtractionService dataExtractionService;

	@Inject
	private TableListRestorationService tableListRestorationService;

	@Inject
	private ThresholdConfigurationCheck thresholdConfigurationCheck;

	@Inject
	private CompanyDeterminationProcess companyDeterminationProcess;

	@Inject
	private TableItemValidation tableItemValidation;

	@Inject
	private EmployeeRestoration employeeRestoration;

	private static final String STATUS = "status";
	private static final String DATA_STORAGE_PROCESS_ID = "dataStorageProcessId";
	

	@SuppressWarnings("unchecked")
	@Override
	protected void handle(CommandHandlerContext<SyncServerUploadProcessingCommand> context) {
		val asyncTask = context.asAsync();
		TaskDataSetter setter = asyncTask.getDataSetter();
		String processId = context.getCommand().getProcessingId();
		String fileId = context.getCommand().getFileId();
		String fileName = context.getCommand().getFileName();
		String password = context.getCommand().getPassword();
		final int FIRST_LINE = 0;
		ServerPrepareMng serverPrepareMng = new ServerPrepareMng(processId, null, null, null, 0, null,
				ServerPrepareOperatingCondition.UPLOADING.value);
		serverPrepareMngRepository.add(serverPrepareMng);
		setter.setData(STATUS, convertToStatus(serverPrepareMng));
		//アルゴリズム「サーバーアップロード処理」を実行する
		// Check upload
		serverPrepareMng = serverUploadProcessingService.serverUploadProcessing(serverPrepareMng, fileId, fileName,
				password);
		// AsyncTask update validate file status
		setter.updateData(STATUS, convertToStatus(serverPrepareMng));
		if (!checkNormalFile(serverPrepareMng))
			return;
		serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.EXTRACTING);
		serverPrepareMngRepository.update(serverPrepareMng);
		setter.updateData(STATUS, convertToStatus(serverPrepareMng));
		// アルゴリズム「ファイル解凍処理」を実行する
		// Unzip file
		serverPrepareMng = dataExtractionService.extractData(serverPrepareMng);
		setter.updateData(STATUS, convertToStatus(serverPrepareMng));
		if (!checkNormalFile(serverPrepareMng))
			return;
		PerformDataRecovery performDataRecovery = new PerformDataRecovery(serverPrepareMng.getDataRecoveryProcessId(),
				AppContexts.user().companyId(), serverPrepareMng.getFileId().orElse(""),
				serverPrepareMng.getUploadFileName().orElse(""));
		// アルゴリズム「テーブル一覧の復元」を実行する
		// Read table list csv
		List<Object> restoreTableResult = tableListRestorationService.restoreTableList(serverPrepareMng);
		serverPrepareMng = (ServerPrepareMng) restoreTableResult.get(0);
		List<TableList> tableList = (List<TableList>) (restoreTableResult.get(1));
		setter.updateData(STATUS, convertToStatus(serverPrepareMng));
		if (!checkNormalFile(serverPrepareMng))
			return;
		// アルゴリズム「調査保存チェック」を実行する=
		// surveyPreservation is fixed for all record, only check in first record
		if (tableList.get(FIRST_LINE).getSurveyPreservation() == NotUseAtr.USE) {
			serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.CAN_NOT_SAVE_SURVEY);
			serverPrepareMngRepository.update(serverPrepareMng);
			setter.updateData(STATUS, convertToStatus(serverPrepareMng));
			return;
		}
		if (!checkNormalFile(serverPrepareMng))
			return;

		// アルゴリズム「テーブル一覧の復元」を実行する
		serverPrepareMng = thresholdConfigurationCheck.checkFileConfiguration(serverPrepareMng, tableList);
		setter.updateData(STATUS, convertToStatus(serverPrepareMng));
		if (!checkNormalFile(serverPrepareMng))
			return;
		// アルゴリズム「別会社判定処理」を実行する
		// Check any category can be recover
		List<Object> sperateCompanyResult = companyDeterminationProcess
				.sperateCompanyDeterminationProcess(serverPrepareMng, performDataRecovery, tableList);
		serverPrepareMng = (ServerPrepareMng) sperateCompanyResult.get(0);
		performDataRecovery = (PerformDataRecovery) sperateCompanyResult.get(1);
		tableList = (List<TableList>) (sperateCompanyResult.get(2));
		setter.updateData(STATUS, convertToStatus(serverPrepareMng));
		if (!checkNormalFile(serverPrepareMng))
			return;
		serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.CHECKING_TABLE_ITEMS);
		serverPrepareMngRepository.update(serverPrepareMng);
		// アルゴリズム「テーブル項目チェック」を実行する
		// Validate header and table column
		serverPrepareMng = tableItemValidation.checkTableItem(serverPrepareMng, tableList);
		setter.updateData(STATUS, convertToStatus(serverPrepareMng));
		if (!checkNormalFile(serverPrepareMng))
			return;
		// アルゴリズム「対象社員の復元」を実行する
		//Restore employee to database
		serverPrepareMng = employeeRestoration.restoreTargerEmployee(serverPrepareMng, performDataRecovery, tableList);
		setter.updateData(STATUS, convertToStatus(serverPrepareMng));
		serverPrepareMngRepository.update(serverPrepareMng);
		if (!tableList.isEmpty()) {
			setter.setData(DATA_STORAGE_PROCESS_ID, tableList.get(0).getDataStorageProcessingId());
		}
	}

	private String convertToStatus(ServerPrepareMng serverPrepareMng) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			return ow.writeValueAsString(procesingStatus(serverPrepareMng.getOperatingCondition()));
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public boolean checkNormalFile(ServerPrepareMng serverPrepareMng) {
		// Continue validate if previous phase is done
		return serverPrepareMng.getOperatingCondition() == ServerPrepareOperatingCondition.CHECKING_FILE_STRUCTURE
				|| serverPrepareMng.getOperatingCondition() == ServerPrepareOperatingCondition.CHECKING_TABLE_ITEMS
				|| serverPrepareMng.getOperatingCondition() == ServerPrepareOperatingCondition.UPLOAD_COMPLETED;
	}

	public ServerPrepareDto procesingStatus(ServerPrepareOperatingCondition condition) {
		// Server status
		/* First param: 3 phase
		 * 0: Uploading
		 * 1: Extracting
		 * 2: Validating
		 */
		
		/* Second param: Phase status
		 * 0: Processing
		 * 1: Success
		 * 2: Failed
		 */
		
		/* Third, fourth param: Server status (Enum value and name)
		 * 
		 * 
		 * 
		 */
		
		/* Fifth param: Error message
		 * 
		 * 
		 * 
		 */
		
		
		switch (condition) {
		case UPLOADING:
			return new ServerPrepareDto(1, 0, condition.value, I18NText.getText(condition.nameId), "");
		case UPLOAD_COMPLETED:
			return new ServerPrepareDto(1, 1, condition.value, I18NText.getText(condition.nameId), "");
		case UPLOAD_FAILED:
			return new ServerPrepareDto(1, 1, condition.value, I18NText.getText(condition.nameId), "Msg_610");
		case UPLOAD_FINISHED:
			return new ServerPrepareDto(1, 1, condition.value, I18NText.getText(condition.nameId), "");
		case EXTRACTING:
			return new ServerPrepareDto(2, 0, condition.value, I18NText.getText(condition.nameId), "");
		case PASSWORD_DIFFERENCE:
			return new ServerPrepareDto(2, 1, condition.value, I18NText.getText(condition.nameId), "Msg_606");
		case EXTRACTION_FAILED:
			return new ServerPrepareDto(2, 1, condition.value, I18NText.getText(condition.nameId), "Msg_607");
		case CHECKING_FILE_STRUCTURE:
			return new ServerPrepareDto(3, 0, condition.value, I18NText.getText(condition.nameId), "");
		case TABLE_LIST_FAULT:
			return new ServerPrepareDto(3, 1, condition.value, I18NText.getText(condition.nameId), "Msg_608");
		case CAN_NOT_SAVE_SURVEY:
			return new ServerPrepareDto(3, 1, condition.value, I18NText.getText(condition.nameId), "Msg_605");
		case FILE_CONFIG_ERROR:
			return new ServerPrepareDto(3, 1, condition.value, I18NText.getText(condition.nameId), "Msg_608");
		case NO_SEPARATE_COMPANY:
			return new ServerPrepareDto(3, 1, condition.value, I18NText.getText(condition.nameId), "Msg_631");
		case TABLE_ITEM_DIFFERENCE:
			return new ServerPrepareDto(3, 1, condition.value, I18NText.getText(condition.nameId), "Msg_609");
		case EM_LIST_ABNORMALITY:
			return new ServerPrepareDto(3, 1, condition.value, I18NText.getText(condition.nameId), "Msg_670");
		case CHECK_COMPLETED:
			return new ServerPrepareDto(3, 2, condition.value, I18NText.getText(condition.nameId), "");
		case CHECKING_TABLE_ITEMS:
			return new ServerPrepareDto(3, 0, condition.value, I18NText.getText(condition.nameId), "");
		default:
			return null;
		}
	}
}
