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
// 特別休暇使用数
public class SpecialLeaveUsedNumber{

	public DayNumberOfUse dayNumberOfUse;

	public Optional<TimeOfUse> timeOfUse;

	public Optional<DayNumberOfUse> dayNumberOfUsed;

	public Optional<SpecialLeaveOverNumber> specialLeaveOverLimitNumber;

}
