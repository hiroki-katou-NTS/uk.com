package nts.uk.ctx.sys.assist.dom.datarestoration.common;

import javax.ejb.Stateless;

import nts.uk.ctx.sys.assist.dom.datarestoration.ServerPrepareMng;

@Stateless
public class ServerPreparationService {
	// アルゴリズム「サーバー準備処理」を実行する
	public ServerPrepareMng serverPreparationProcessing(ServerPrepareMng serverPrepareMng){
		return serverPrepareMng;
	}
}
