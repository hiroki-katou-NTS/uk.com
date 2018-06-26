package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import nts.uk.ctx.sys.assist.dom.datarestoration.*;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ServerPreparationService {

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

	// アルゴリズム「サーバー準備処理」を実行する
	public String serverPreparationProcessing(ServerPrepareMng serverPrepareMng) {
		serverPrepareMng = dataExtractionService.extractData(serverPrepareMng);
		if (checkNormalFile(serverPrepareMng)) {
			PerformDataRecovery performDataRecovery = new PerformDataRecovery(
					serverPrepareMng.getDataRecoveryProcessId(), AppContexts.user().companyId(),
					serverPrepareMng.getFileId().get(), serverPrepareMng.getUploadFileName().get());
			// アルゴリズム「テーブル一覧の復元」を実行する
			List<Object> restoreTableResult = tableListRestorationService.restoreTableList(serverPrepareMng);
			serverPrepareMng = (ServerPrepareMng) restoreTableResult.get(0);
			List<TableList> tableList = (List<TableList>) (restoreTableResult.get(1));
			if (checkNormalFile(serverPrepareMng)) {
				if (!tableList.isEmpty()) {
					if (tableList.get(0).getSurveyPreservation() == NotUseAtr.USE)
						serverPrepareMng.setOperatingCondition(ServerPrepareOperatingCondition.CAN_NOT_SAVE_SURVEY);
				}
				if (checkNormalFile(serverPrepareMng)) {
					// アルゴリズム「テーブル一覧の復元」を実行する
					serverPrepareMng = thresholdConfigurationCheck.checkFileConfiguration(serverPrepareMng, tableList);
					if (checkNormalFile(serverPrepareMng)) {
						// アルゴリズム「別会社判定処理」を実行する
						List<Object> sperateCompanyResult = companyDeterminationProcess
								.sperateCompanyDeterminationProcess(serverPrepareMng, performDataRecovery, tableList);
						serverPrepareMng = (ServerPrepareMng) sperateCompanyResult.get(0);
						performDataRecovery = (PerformDataRecovery) sperateCompanyResult.get(1);
						tableList = (List<TableList>) (sperateCompanyResult.get(2));
						if (checkNormalFile(serverPrepareMng)) {
							// アルゴリズム「テーブル項目チェック」を実行する
							serverPrepareMng = tableItemValidation.checkTableItem(serverPrepareMng, tableList);
							if (checkNormalFile(serverPrepareMng)) {
								// アルゴリズム「対象社員の復元」を実行する
								employeeRestoration.restoreTargerEmployee(serverPrepareMng, performDataRecovery,
										tableList);
							}
						}
					}
				}
			}
		}
		return convertToMessage(serverPrepareMng.getOperatingCondition());
	}

	private boolean checkNormalFile(ServerPrepareMng serverPrepareMng) {
		return serverPrepareMng.getOperatingCondition() == ServerPrepareOperatingCondition.CHECKING_FILE_STRUCTURE;
	}

	private static String convertToMessage(ServerPrepareOperatingCondition condition){
		switch (condition) {
		case UPLOAD_FAILED:
			return "Msg_610";
		case PASSWORD_DIFFERENCE:
			return "Msg_606";
		case EXTRACTION_FAILED:
			return "Msg_607";
		case TABLE_LIST_FAULT:
			return "Msg_608";
		case CAN_NOT_SAVE_SURVEY:
			return "Msg_605";
		case FILE_CONFIG_ERROR:
			return "Msg_608";
		case NO_SEPARATE_COMPANY:
			return "Msg_631";
		case TABLE_ITEM_DIFFERENCE:
			return "Msg_609";
		case EM_LIST_ABNORMALITY:
			return "Msg_670";
		default:
			return "";
		}
	}
}
