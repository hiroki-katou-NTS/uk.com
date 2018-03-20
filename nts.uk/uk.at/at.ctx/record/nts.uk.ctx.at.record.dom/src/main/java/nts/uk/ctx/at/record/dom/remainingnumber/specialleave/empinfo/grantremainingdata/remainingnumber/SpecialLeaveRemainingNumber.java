package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// 特別休暇残数
public class SpecialLeaveRemainingNumber {
	// 日数
	public DayNumberOfRemain dayNumberOfRemain;
	// 時間
	public Optional<TimeOfRemain> timeOfRemain;

}
