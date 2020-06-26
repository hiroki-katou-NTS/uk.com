package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.BeforeAddCheckMethod;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.残業申請事前の受付制限
 * @author Doan Duy Hung
 *
 */
@Getter
public class OTAppBeforehandAcceptanceRestriction {
	
	/**
	 * チェック方法
	 */
	private BeforeAddCheckMethod methodCheck;
	
	/**
	 * 日数
	 */
	private AppAcceptLimitDay dateBeforehandRestrictions;
	
	/**
	 * 利用する
	 */
	private boolean toUse;
	
	/**
	 * 時刻（早出残業）
	 */
	private Optional<AttendanceClock> earlyOvertime;
	
	/**
	 * 時刻（通常残業）
	 */
	private Optional<AttendanceClock> normalOvertime;
	
	/**
	 * 時刻（早出残業・通常残業）
	 */
	private Optional<AttendanceClock> earlyNormalOvertime;
	
}
