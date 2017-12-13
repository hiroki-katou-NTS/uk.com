package nts.uk.ctx.at.shared.app.find.attendanceitem.daily.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 日別実績の臨時時間 */
@Data
public class TemporaryTimeDto {

	/**
	 * 臨時時間: 日別実績の臨時枠時間
	 */
	@AttendanceItemLayout(layout="A", isList=true)
	private List<TemporaryTimeFrameDto> temporaryTimeFrame;
}
