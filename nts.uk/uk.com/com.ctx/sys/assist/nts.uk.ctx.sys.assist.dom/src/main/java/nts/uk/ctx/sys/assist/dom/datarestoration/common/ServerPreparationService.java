package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareOperatingCondition;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
public class ServerPreparationService {
	
	@Inject
	private DataExtractionService dataExtractionService;
	
	@Inject
	private TableListRestorationService tableListRestorationService;
	
	@Inject
	private TableListRepository tableListRepository;
	
	@Inject
	private PerformDataRecoveryRepository performDataRecoveryRepository;
	
	@Inject
	private ServerPrepareMngRepository serverPrepareMngRepository;
	
	@Inject
	private ThresholdConfigurationCheck thresholdConfigurationCheck;
	
	@Inject
	private CompanyDeterminationProcess companyDeterminationProcess;
	
	// アルゴリズム「サーバー準備処理」を実行する
	public ServerPrepareMng serverPreparationProcessing(ServerPrepareMng serverPrepareMng){
		serverPrepareMng = dataExtractionService.extractData(serverPrepareMng);
		//ドメインモデル「データ復旧の実行」に新規に書き出す
		PerformDataRecovery performDataRecovery = new PerformDataRecovery(serverPrepareMng.getDataRecoveryProcessId(), AppContexts.user().companyId(), serverPrepareMng.getFileId().get(), serverPrepareMng.getUploadFileName().get());
		
		//アルゴリズム「テーブル一覧の復元」を実行する
		List<Object> restoreTableResult = tableListRestorationService.restoreTableList(serverPrepareMng);
		List<TableList> tableList = (List<TableList>)(restoreTableResult.get(0));
		serverPrepareMng = (ServerPrepareMng) restoreTableResult.get(1);
		Optional<ServerPrepareMng> op_serverPrepareMng = serverPrepareMngRepository.getServerPrepareMngById(serverPrepareMng.getDataRecoveryProcessId());
		if (!op_serverPrepareMng.isPresent()){
			return null;
			
		} else {
			serverPrepareMng = op_serverPrepareMng.get();
			
		}
		if (!tableList.isEmpty()){
			serverPrepareMng = thresholdConfigurationCheck.checkFileConfiguration(serverPrepareMng, tableList);
			if (serverPrepareMng.getOperatingCondition() != ServerPrepareOperatingCondition.CAN_NOT_SAVE_SURVEY){
				List<Object> sperateCompanyResult = companyDeterminationProcess.sperateCompanyDeterminationProcess(serverPrepareMng, performDataRecovery, tableList);
			}
		}
		
		return serverPrepareMng;
	}
}
