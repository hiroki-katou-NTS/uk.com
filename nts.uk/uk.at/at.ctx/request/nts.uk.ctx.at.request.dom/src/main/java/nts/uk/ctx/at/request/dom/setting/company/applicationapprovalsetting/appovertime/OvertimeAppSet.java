package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.残業申請設定.残業申請設定
 * @author Doan Duy Hung
 *
 */
@Getter
public class OvertimeAppSet implements DomainAggregate {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 残業休出申請共通設定
	 */
	private OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet;
	
	/**
	 * 残業枠設定
	 */
	private List<OvertimeQuotaSetUse> overtimeQuotaSet;
	
	/**
	 * 申請詳細設定
	 */
	private ApplicationDetailSetting applicationDetailSetting;
	
}
