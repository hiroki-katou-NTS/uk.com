package nts.uk.ctx.workflow.dom.service;


import nts.uk.ctx.workflow.dom.approvermanagement.setting.UseClassification;
import nts.uk.ctx.workflow.dom.service.output.SettingUseUnitOutput;
public interface SettingUseUnitService {
	/**
	 * Refactor5
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.Q：利用単位の設定.アルゴリズム.起動処理
	 * @param companyId 会社ID
	 * @param systemCategory システム区分
	 * @return 利用単位の設定の情報
	 */
	public SettingUseUnitOutput start(String companyId, Integer systemCategory);
	/**
	 * Refactor5
	 * UKDesign.UniversalK.共通.CMM_マスタメンテナンス.CMM018_承認者の登録.CMM018_承認者の登録（就業・人事）.Q：利用単位の設定.アルゴリズム.登録の前チェック.登録の前チェック
	 * @param companyUnit
	 * @param workplaceUnit
	 * @param employeeUnit
	 */
	public void checkBeforeRegister(UseClassification companyUnit, UseClassification workplaceUnit, UseClassification employeeUnit);
}
