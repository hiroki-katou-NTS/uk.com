package nts.uk.ctx.at.request.dom.vacation.history.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 指定期間の計画年休の上限チェック PARAM
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CheckMaximumOfPlanParam {
	/**
	 * ・会社ID
	 */
	private String cid;
	/**
	 * ・社員ID
	 */
	private String employeeId;
	/**
	 * ・勤務種類 
	 */
	private String workTypeCode;
	/**
	 * チェック開始日, チェック終了日 
	 */
	private DatePeriod dateCheck;
	/**
	 * 上限日数
	 */
	private int maxNumber;
	/**
	 * 申請開始日, 申請終了日
	 */
	private DatePeriod appDate;
}
