package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 上限日数期間
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseUpperLimitPeriod {
	/** 期間 */
	private DatePeriod period;
	/** 上限日数 */
	private ChildCareNurseUpperLimit limitDays;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseUpperLimitPeriod(){
		this.period =  new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.limitDays =  new ChildCareNurseUpperLimit(0);
	}
	/**
	 * ファクトリー
	 * @param period 期間
	 * @param limitDays 上限日数
	 * @return 上限日数期間
	 */
	public static ChildCareNurseUpperLimitPeriod of(
			DatePeriod period,
			 ChildCareNurseUpperLimit limitDays) {

		ChildCareNurseUpperLimitPeriod domain = new ChildCareNurseUpperLimitPeriod();
		domain.period = period;
		domain.limitDays = limitDays;
		return domain;
	}
}
