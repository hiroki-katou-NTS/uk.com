package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// 特別休暇使用数
public class SpecialLeaveUsedNumber {

	public DayNumberOfUse dayNumberOfUse;

	public Optional<TimeOfUse> timeOfUse;

	public Optional<DayNumberOfUse> useSavingDays;

	public Optional<SpecialLeaveOverNumber> specialLeaveOverLimitNumber;

	private SpecialLeaveUsedNumber(Double dayNumberOfUse, Integer timeOfUse, Double useSavingDays,
			int dayNumberOfExeeded, Integer timeOfExeeded) {
		this.dayNumberOfUse = new DayNumberOfUse(dayNumberOfUse);
		this.timeOfUse = timeOfUse != null ? Optional.of(new TimeOfUse(timeOfUse)) : Optional.empty();
		this.useSavingDays = useSavingDays != null ? Optional.of(new DayNumberOfUse(useSavingDays))
				: Optional.empty();
		this.specialLeaveOverLimitNumber = Optional
				.of(SpecialLeaveOverNumber.createFromJavaType(dayNumberOfExeeded, timeOfExeeded));
	}

	public static SpecialLeaveUsedNumber createFromJavaType(Double dayNumberOfUse, Integer timeOfUse,
			Double dayNumberOfUsed, int dayNumberOfExeeded, Integer timeOfExeeded) {
		return new SpecialLeaveUsedNumber(dayNumberOfUse, timeOfUse, dayNumberOfUsed, dayNumberOfExeeded,
				timeOfExeeded);
	}

}
