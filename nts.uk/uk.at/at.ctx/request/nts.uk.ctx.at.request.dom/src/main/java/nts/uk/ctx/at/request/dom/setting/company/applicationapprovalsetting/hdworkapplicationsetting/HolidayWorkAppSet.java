package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.ApplicationDetailSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.休日出勤申請設定.休出申請設定
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class HolidayWorkAppSet implements DomainAggregate {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 打刻漏れ計算区分
	 */
	private CalcStampMiss calcStampMiss;
	
	/**
	 * 直行直帰の機能の利用設定
	 */
	private boolean useDirectBounceFunction;
	
	/**
	 * 申請詳細設定
	 */
	private ApplicationDetailSetting applicationDetailSetting;
	
	/**
	 * 残業休出申請共通設定
	 */
	private OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet;
	
}
