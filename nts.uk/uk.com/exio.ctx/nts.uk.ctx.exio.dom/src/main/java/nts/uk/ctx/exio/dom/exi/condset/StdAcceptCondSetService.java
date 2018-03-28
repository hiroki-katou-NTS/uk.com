package nts.uk.ctx.exio.dom.exi.condset;

public interface StdAcceptCondSetService {
	
	//アルゴリズム「コピーの作成」を実行する
	//(Execute the algorithm "Create copy")
	void copyConditionSetting(StdAcceptCondSetCopyParam param);
	
	//アルゴリズム「受入設定の削除」を実行する
	//(Execute the algorithm "Delete acceptance setting")
	void deleteConditionSetting(String cid, int sysType, String conditionSetCd);
	
	//アルゴリズム「受入設定の登録」を実行する
	//(Execute the algorithm "registration of acceptance setting")
	void registerConditionSetting(StdAcceptCondSet domain);
}
