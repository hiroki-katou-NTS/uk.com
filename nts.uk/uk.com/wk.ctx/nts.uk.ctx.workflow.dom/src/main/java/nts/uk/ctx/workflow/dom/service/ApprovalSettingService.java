package nts.uk.ctx.workflow.dom.service;

import javax.ejb.Stateless;

import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;
@Stateless
public interface ApprovalSettingService {
	/**
	 * Refactor5
	 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".ワークフロー.承認者管理.承認設定.アルゴリズム.承認単位の利用設定を取得する
	 * @param companyId
	 * @param systemCategory
	 * @return 承認単位の利用設定
	 */
	ApproverRegisterSet getSettingUseUnit(String companyId, Integer systemCategory);
}
