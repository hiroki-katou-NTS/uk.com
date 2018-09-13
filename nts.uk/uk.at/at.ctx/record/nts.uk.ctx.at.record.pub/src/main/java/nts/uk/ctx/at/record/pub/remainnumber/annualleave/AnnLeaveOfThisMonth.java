package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;

@Getter
@Setter
@NoArgsConstructor
public class AnnLeaveOfThisMonth {
	
	/**
	 * 付与年月日
	 */
	private GeneralDate grantDate;
	
	/**
	 * 付与日数
	 */
	private Double grantDays;
	
	/**
	 * 月初残日数
	 */
	private Double firstMonthRemNumDays;
	
	/**
	 * 月初残時間
	 */
	private int firstMonthRemNumMinutes;
	
	/**
	 * 使用日数
	 */
	private AnnualLeaveUsedDayNumber usedDays;
	
	/**
	 * 使用時間
	 */
	private Optional<Integer> usedMinutes;
	
	/**
	 * 残日数
	 */
	private AnnualLeaveRemainingDayNumber remainDays;
	
	/**
	 * 残時間
	 */
	private Optional<Integer> remainMinutes;

	public AnnLeaveOfThisMonth(GeneralDate grantDate, Double grantDays, Double firstMonthRemNumDays,
			int firstMonthRemNumMinutes, AnnualLeaveUsedDayNumber usedDays,
			Optional<Integer> usedMinutes, AnnualLeaveRemainingDayNumber remainDays,
			Optional<Integer> remainMinutes) {
		super();
		this.grantDate = grantDate;
		this.grantDays = grantDays;
		this.firstMonthRemNumDays = firstMonthRemNumDays;
		this.firstMonthRemNumMinutes = firstMonthRemNumMinutes;
		this.usedDays = usedDays;
		this.usedMinutes = usedMinutes;
		this.remainDays = remainDays;
		this.remainMinutes = remainMinutes;
	}

	// avoid compile error
	public void setGrantDays(Double days) {
		this.grantDays = days;
	}
	
}
