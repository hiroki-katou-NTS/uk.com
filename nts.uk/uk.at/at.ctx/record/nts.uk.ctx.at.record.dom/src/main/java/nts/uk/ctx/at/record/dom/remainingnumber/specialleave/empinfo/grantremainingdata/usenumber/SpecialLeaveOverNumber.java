package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 特別休暇上限超過消滅数
public class SpecialLeaveOverNumber {
	
	// 日数
	public DayNumberOfExeeded dayNumberOfExeeded;
	// 時間
	public Optional<TimeOfExeeded> timeOfExeeded;
	
	private SpecialLeaveOverNumber(int days, Integer minutes) {
		this.dayNumberOfExeeded = new DayNumberOfExeeded(days);
		this.timeOfExeeded = minutes != null ? Optional.of(new TimeOfExeeded(minutes)) : Optional.empty();
	}

	public static SpecialLeaveOverNumber createFromJavaType(int days, Integer minutes) {
		return new SpecialLeaveOverNumber(days, minutes);
	}

}
