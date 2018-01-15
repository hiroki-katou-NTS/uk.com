package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.UseAtr;
/**
 * 残業申請設定
 * @author loivt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class AppOvertimeSetting extends AggregateRoot{
	
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	/**
	 * フレックス超過利用設定
	 */
	private FlexExcessUseSetAtr flexJExcessUseSetAtr;
	
	/**
	 * 勤務種類、就業時間帯、勤務時間
	 */
	private UseAtr preTypeSiftReflectFlg;
	/**
	 * 残業時間
	 */
	private UseAtr preOvertimeReflectFlg;
	/**
	 * 勤務種類、就業時間帯
	 */
	private UseAtr postTypeSiftReflectFlg;
	/**
	 * 休憩時間
	 */
	private UseAtr postBreakReflectFlg;
	/**
	 * 勤務時間
	 */
	private UseAtr postWorktimeReflectFlg;
	
	/**
	 * カレンダー表示区分
	 */
	private UseAtr calendarDispAtr;
	/**
	 * 早出残業使用区分
	 */
	private UseAtr earlyOvertimeUseAtr;
	/**
	 * 残業指示超過区分
	 */
	private UseAtr instructExcessOTAtr;
	
	/**
	 * 実績超過打刻優先設定
	 */
	private PriorityStampSetAtr priorityStampSetAtr;
	/**
	 * 申請時間指定単位
	 */
	private UnitAssignmentOvertime unitAssignmentOvertime;
	/**
	 * 通常残業使用区分
	 */
	private UseAtr normalOvertimeUseAtr;
}
