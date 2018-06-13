package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;

@Stateless
public class ServerPreparationService {
	
	@Inject
	private DataExtractionService dataExtractionService;
	
	// アルゴリズム「サーバー準備処理」を実行する
	public ServerPrepareMng serverPreparationProcessing(ServerPrepareMng serverPrepareMng){
		serverPrepareMng = dataExtractionService.extractData(serverPrepareMng);
		return serverPrepareMng;
	}
}
