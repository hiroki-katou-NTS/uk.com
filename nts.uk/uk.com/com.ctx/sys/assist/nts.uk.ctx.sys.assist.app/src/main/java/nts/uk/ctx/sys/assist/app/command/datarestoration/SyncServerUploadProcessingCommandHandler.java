package nts.uk.ctx.sys.assist.app.command.datarestoration;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import lombok.val;
import nts.arc.layer.app.command.AsyncCommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.data.TaskDataSetter;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.datarestoration.UploadProcessingService;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.CompanyDeterminationProcess;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.DataExtractionService;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.EmployeeRestoration;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.ServerUploadProcessingService;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.TableItemValidation;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.TableListRestorationService;
import nts.uk.ctx.sys.assist.dom.datarestoration.common.ThresholdConfigurationCheck;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.shr.com.context.AppContexts;

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
	
	@Override
	protected void handle(CommandHandlerContext<SyncServerUploadProcessingCommand> context) {
		val asyncTask = context.asAsync();
		TaskDataSetter setter = asyncTask.getDataSetter();
		String processId = context.getCommand().getProcessingId();
		String fileId = context.getCommand().getFileId();
		String fileName = context.getCommand().getFileName();
		String password = context.getCommand().getPassword();
		ServerPrepareMng serverPrepareMng = new ServerPrepareMng(processId, null, null, null, 0, null, ServerPrepareOperatingCondition.UPLOADING.value);
		serverPrepareMngRepository.add(serverPrepareMng);
		setter.setData("status", convertToStatus(0, serverPrepareMng));
		serverPrepareMng = serverUploadProcessingService.serverUploadProcessing(serverPrepareMng, fileId, fileName, password);
		setter.updateData("status", convertToStatus(0, serverPrepareMng));
		if (serverPrepareMng.getOperatingCondition() == ServerPrepareOperatingCondition.UPLOAD_COMPLETED){
			serverPrepareMng = dataExtractionService.extractData(serverPrepareMng);
			setter.updateData("status", convertToStatus(1, serverPrepareMng));
			if (checkNormalFile(serverPrepareMng)){
				PerformDataRecovery performDataRecovery = new PerformDataRecovery(serverPrepareMng.getDataRecoveryProcessId(), AppContexts.user().companyId(), serverPrepareMng.getFileId().get(), serverPrepareMng.getUploadFileName().get());
				//アルゴリズム「テーブル一覧の復元」を実行する
				List<Object> restoreTableResult = tableListRestorationService.restoreTableList(serverPrepareMng);
				serverPrepareMng = (ServerPrepareMng) restoreTableResult.get(0);
				List<TableList> tableList = (List<TableList>)(restoreTableResult.get(1));
				setter.updateData("status", convertToStatus(2, serverPrepareMng));
				if (checkNormalFile(serverPrepareMng)){
					// アルゴリズム「テーブル一覧の復元」を実行する
					serverPrepareMng = thresholdConfigurationCheck.checkFileConfiguration(serverPrepareMng, tableList);
					setter.updateData("status", convertToStatus(2, serverPrepareMng));
					if (checkNormalFile(serverPrepareMng)){
						// アルゴリズム「別会社判定処理」を実行する
						List<Object> sperateCompanyResult = companyDeterminationProcess.sperateCompanyDeterminationProcess(serverPrepareMng, performDataRecovery, tableList);
						serverPrepareMng = (ServerPrepareMng) sperateCompanyResult.get(0);
						performDataRecovery = (PerformDataRecovery) sperateCompanyResult.get(1);
						tableList = (List<TableList>)(sperateCompanyResult.get(2));
						setter.updateData("status", convertToStatus(2, serverPrepareMng));
						if (checkNormalFile(serverPrepareMng)){
							// アルゴリズム「テーブル項目チェック」を実行する
							serverPrepareMng = tableItemValidation.checkTableItem(serverPrepareMng, tableList);
							setter.updateData("status", convertToStatus(2, serverPrepareMng));
							if (checkNormalFile(serverPrepareMng)){
								// アルゴリズム「対象社員の復元」を実行する
								employeeRestoration.restoreTargerEmployee(serverPrepareMng, performDataRecovery, tableList);
								setter.updateData("status", convertToStatus(2, serverPrepareMng));
							}
						}
						
					}
				}
			}
		}
	}
	private String convertToStatus(int processNum, ServerPrepareMng serverPrepareMng){
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		try {
			return ow.writeValueAsString(new ServerPrepareDto(processNum, serverPrepareMng.getOperatingCondition().nameId));
		} catch (JsonProcessingException e) {
			return null;
		}
	}
	
	public boolean checkNormalFile (ServerPrepareMng serverPrepareMng){
		return serverPrepareMng.getOperatingCondition() == ServerPrepareOperatingCondition.CHECKING_FILE_STRUCTURE;
	}
}
