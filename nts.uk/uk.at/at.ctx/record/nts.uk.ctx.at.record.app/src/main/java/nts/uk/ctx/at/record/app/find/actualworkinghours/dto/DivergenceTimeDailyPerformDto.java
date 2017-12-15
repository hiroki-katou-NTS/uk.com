package nts.uk.ctx.at.record.app.find.actualworkinghours.dto;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.shared.app.util.attendanceitem.annotation.AttendanceItemLayout;

/** 日別実績の乖離時間 */
@Data
public class DivergenceTimeDailyPerformDto {
	
	/** 乖離時間: 乖離時間 */
	@AttendanceItemLayout(layout="A", isList=true)
	private List<DivergenceTimeDto> divergenceTime;
}
