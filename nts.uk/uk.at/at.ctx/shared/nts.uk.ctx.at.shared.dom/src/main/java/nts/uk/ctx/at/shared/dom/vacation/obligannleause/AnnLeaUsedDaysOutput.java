package nts.uk.ctx.at.shared.dom.vacation.obligannleause;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 年休使用数Output　（義務日数計算期間内）
 * @author shuichi_ishida
 */
@Getter
@Setter
public class AnnLeaUsedDaysOutput {

	/** 年休使用数 */
	private Optional<AnnualLeaveUsedDayNumber> days;
	/** 期間 */
	private Optional<DatePeriod> period;
	
	/**
	 * コンストラクタ
	 */
	public AnnLeaUsedDaysOutput(){
		this.days = Optional.empty();
		this.period = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param days 年休使用数
	 * @param period 期間
	 * @return 年休使用数Output
	 */
	public static AnnLeaUsedDaysOutput of(
			AnnualLeaveUsedDayNumber days,
			DatePeriod period){
	
		AnnLeaUsedDaysOutput domain = new AnnLeaUsedDaysOutput();
		domain.days = Optional.ofNullable(days);
		domain.period = Optional.ofNullable(period);
		return domain;
	}
}
