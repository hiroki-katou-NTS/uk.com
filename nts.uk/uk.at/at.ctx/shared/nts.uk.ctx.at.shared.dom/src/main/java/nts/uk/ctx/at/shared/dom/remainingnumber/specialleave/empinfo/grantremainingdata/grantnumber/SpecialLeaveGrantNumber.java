package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.grantnumber;

import java.math.BigDecimal;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//特別休暇付与数
public class SpecialLeaveGrantNumber {

	//日数
	public DayNumberOfGrant dayNumberOfGrant;
	//時間
	public Optional<TimeOfGrant> timeOfGrant;
	
	private SpecialLeaveGrantNumber(BigDecimal days, Integer minutes) {
		this.dayNumberOfGrant = new DayNumberOfGrant(days == null? 0.0d: days.doubleValue());
		this.timeOfGrant = minutes != null ? Optional.of(new TimeOfGrant(minutes)) : Optional.empty();
	}

	public static SpecialLeaveGrantNumber createFromJavaType(BigDecimal days, Integer minutes) {
		return new SpecialLeaveGrantNumber(days, minutes);
	}
	private SpecialLeaveGrantNumber(double days, Integer minutes) {
		this.dayNumberOfGrant = new DayNumberOfGrant(days);
		this.timeOfGrant = minutes != null ? Optional.of(new TimeOfGrant(minutes)) : Optional.empty();
	}

	public static SpecialLeaveGrantNumber createFromJavaType(double days, Integer minutes) {
		return new SpecialLeaveGrantNumber(days, minutes);
	}

	
}
