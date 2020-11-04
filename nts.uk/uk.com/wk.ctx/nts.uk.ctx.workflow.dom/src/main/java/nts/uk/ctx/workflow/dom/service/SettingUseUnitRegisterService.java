package nts.uk.ctx.workflow.dom.service;


import nts.uk.ctx.workflow.dom.service.output.SettingUseUnitOutput;
public interface SettingUseUnitRegisterService {
	
	/**
	 * Refactor5
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.Q：利用単位の設定.アルゴリズム.登録処理
	 * @param systemCategory システム区分
	 * @param settingUseUnitOutput 利用単位の設定の情報
	 */
	public void register(Integer systemCategory, SettingUseUnitOutput settingUseUnitOutput);
}
