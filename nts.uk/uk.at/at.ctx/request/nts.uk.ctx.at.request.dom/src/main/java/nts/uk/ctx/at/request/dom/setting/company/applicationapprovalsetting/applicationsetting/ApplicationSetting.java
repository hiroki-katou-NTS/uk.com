package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdeadlineset.AppDeadlineSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.appdispset.AppDisplaySetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationrestrictionsetting.AppLimitSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.AppTypeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.ReceptionRestrictionSetting;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請設定
 * @author Doan Duy Hung
 *
 */
@Getter
public class ApplicationSetting implements DomainAggregate {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 申請制限設定
	 */
	private AppLimitSetting appLimitSetting;
	
	/**
	 * 種類別設定
	 */
	private AppTypeSetting appTypeSetting;
	
	/**
	 * 代行申請で利用できる申請設定
	 */
	private AppSetForProxyApp appSetForProxyApp;
	
	/**
	 * 締切設定
	 */
	private AppDeadlineSetting appDeadlineSetting;
	
	/**
	 * 申請表示設定
	 */
	private AppDisplaySetting appDisplaySetting;
	
	/**
	 * 受付制限設定
	 */
	private ReceptionRestrictionSetting receptionRestrictionSetting;
	
	/**
	 * 承認ルートの基準日
	 */
	private RecordDate recordDate;
	
}
