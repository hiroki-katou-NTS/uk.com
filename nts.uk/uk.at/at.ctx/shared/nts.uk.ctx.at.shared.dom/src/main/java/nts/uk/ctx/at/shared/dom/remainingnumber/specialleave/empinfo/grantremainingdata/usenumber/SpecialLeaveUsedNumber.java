package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseDays;

@Getter
@Setter
// 特別休暇使用数
public class SpecialLeaveUsedNumber extends LeaveUsedNumber{

//	/**
//	 * 日数
//	 */
//	public DayNumberOfUse dayNumberOfUse;
//	
//	/**
//	 * 時間
//	 */
//	public Optional<TimeOfUse> timeOfUse;
//	
//	/**
//	 * 積み崩し日数
//	 */
//	public Optional<DayNumberOfUse> useSavingDays;
//	
//	/**
//	 * 上限超過消滅日数
//	 */
//	public Optional<SpecialLeaveOverNumber> specialLeaveOverLimitNumber;


	public SpecialLeaveUsedNumber(double days, Integer minutes, Double stowageDays) {
		this.days = new LeaveUsedDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new AnnualLeaveUsedTime(minutes)) : Optional.empty();
		this.stowageDays = stowageDays != null ? Optional.of(new AnnualLeaveUsedDayNumber(stowageDays))
				: Optional.empty();
	}

	public static AnnualLeaveUsedNumber createFromJavaType(double days, Integer minutes, Double stowageDays) {
		return new AnnualLeaveUsedNumber(days, minutes, stowageDays);
	}

}
