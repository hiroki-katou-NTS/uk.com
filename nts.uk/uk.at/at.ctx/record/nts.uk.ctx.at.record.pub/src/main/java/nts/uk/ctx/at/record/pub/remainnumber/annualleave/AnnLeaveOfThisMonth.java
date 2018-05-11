package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.TimeAnnualLeaveUsedTime;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantDays;

public class AnnLeaveOfThisMonth {
	
	/**
	 * 付与年月日
	 */
	private GeneralDate grantDate;
	
	/**
	 * 付与日数
	 */
	private GrantDays grantDays;
	
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
	private Optional<TimeAnnualLeaveUsedTime> usedMinutes;
	
	/**
	 * 残日数
	 */
	private AnnualLeaveRemainingDayNumber remainDays;
	
	/**
	 * 残時間
	 */
	private Optional<RemainingMinutes> remainMinutes;

	public AnnLeaveOfThisMonth(GeneralDate grantDate, GrantDays grantDays, Double firstMonthRemNumDays,
			int firstMonthRemNumMinutes, AnnualLeaveUsedDayNumber usedDays,
			Optional<TimeAnnualLeaveUsedTime> usedMinutes, AnnualLeaveRemainingDayNumber remainDays,
			Optional<RemainingMinutes> remainMinutes) {
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

	
	
}
