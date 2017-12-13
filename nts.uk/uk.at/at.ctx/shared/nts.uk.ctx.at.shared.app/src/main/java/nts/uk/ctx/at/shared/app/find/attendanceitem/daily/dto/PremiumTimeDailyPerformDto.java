package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 割増時間 */
@Data
public class PremiumTimeDailyPerformDto {

	/** 割増時間: 割増時間 */
	@AttendanceItemLayout(layout="A", isList= true)
	private List<PremiumTimeDto> premiumTimes;
}
