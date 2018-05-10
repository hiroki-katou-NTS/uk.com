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
	public DayNumberOver numberOverDays;
	// 時間
	public Optional<TimeOver> timeOver;
	
	private SpecialLeaveOverNumber(double days, Integer minutes) {
		this.numberOverDays = new DayNumberOver(days);
		this.timeOver = minutes != null ? Optional.of(new TimeOver(minutes)) : Optional.empty();
	}

	public static SpecialLeaveOverNumber createFromJavaType(double days, Integer minutes) {
		return new SpecialLeaveOverNumber(days, minutes);
	}

}
