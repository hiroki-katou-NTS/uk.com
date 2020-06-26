package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting;

import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.ApplicationType;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.受付制限設定
 * @author Doan Duy Hung
 *
 */
@Getter
public class ReceptionRestrictionSetting {
	
	/**
	 * 残業申請事前の受付制限
	 */
	private OTAppBeforehandAcceptanceRestriction otAppBeforehandAcceptanceRestriction;
	
	/**
	 * 事後の受付制限
	 */
	private AfterhandRestriction afterhandRestriction;
	
	/**
	 * 事前の受付制限
	 */
	private BeforehandRestriction beforehandRestriction;
	
	/**
	 * 申請種類
	 */
	private ApplicationType appType;
	
}
