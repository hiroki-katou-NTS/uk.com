package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveGrantTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveGrantNumber;

@Getter
@Setter
//特別休暇付与数
public class SpecialLeaveGrantNumber extends LeaveGrantNumber {

//	//日数
//	public DayNumberOfGrant dayNumberOfGrant;
//	//時間
//	public Optional<TimeOfGrant> timeOfGrant;
//	
//	private SpecialLeaveGrantNumber(BigDecimal days, Integer minutes) {
//		this.dayNumberOfGrant = new DayNumberOfGrant(days == null? 0.0d: days.doubleValue());
//		this.timeOfGrant = minutes != null ? Optional.of(new TimeOfGrant(minutes)) : Optional.empty();
//	}
//
//	public static SpecialLeaveGrantNumber createFromJavaType(BigDecimal days, Integer minutes) {
//		return new SpecialLeaveGrantNumber(days, minutes);
//	}
//	private SpecialLeaveGrantNumber(double days, Integer minutes) {
//		this.dayNumberOfGrant = new DayNumberOfGrant(days);
//		this.timeOfGrant = minutes != null ? Optional.of(new TimeOfGrant(minutes)) : Optional.empty();
//	}
//
//	public static SpecialLeaveGrantNumber createFromJavaType(double days, Integer minutes) {
//		return new SpecialLeaveGrantNumber(days, minutes);
//	}
	
	protected SpecialLeaveGrantNumber(double days, Integer minutes) {
		//super(days, minutes);
		this.days = new SpecialLeaveGrantDayNumber(days);
		this.minutes = minutes != null ? Optional.of(new SpecialLeaveGrantTime(minutes)) : Optional.empty();
	}

	public static SpecialLeaveGrantNumber createFromJavaType(double days, Integer minutes) {
		return new SpecialLeaveGrantNumber(days, minutes);
	}


	
}
