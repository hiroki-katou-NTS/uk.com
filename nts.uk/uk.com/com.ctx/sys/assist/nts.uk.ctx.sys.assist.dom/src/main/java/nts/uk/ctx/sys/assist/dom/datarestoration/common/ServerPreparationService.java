package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;
import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMngRepository;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;
import nts.uk.ctx.sys.assist.dom.tablelist.TableListRepository;
import nts.uk.shr.com.context.AppContexts;

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
	// アルゴリズム「サーバー準備処理」を実行する
	public ServerPrepareMng serverPreparationProcessing(ServerPrepareMng serverPrepareMng){
		serverPrepareMng = dataExtractionService.extractData(serverPrepareMng);
		//ドメインモデル「データ復旧の実行」に新規に書き出す
		PerformDataRecovery performDataRecovery = new PerformDataRecovery(serverPrepareMng.getDataRecoveryProcessId(), AppContexts.user().companyId(), serverPrepareMng.getFileId().get(), serverPrepareMng.getUploadFileName().get());
		Optional<ServerPrepareMng> op_serverPrepareMng = serverPrepareMngRepository.getServerPrepareMngById(serverPrepareMng.getDataRecoveryProcessId());
		if (!op_serverPrepareMng.isPresent()){
			return null;
		} else {
			serverPrepareMng = op_serverPrepareMng.get();
			
		}
		//アルゴリズム「テーブル一覧の復元」を実行する
		TableList tableList = tableListRestorationService.restoreTableList(serverPrepareMng);
		return serverPrepareMng;
	}
}
