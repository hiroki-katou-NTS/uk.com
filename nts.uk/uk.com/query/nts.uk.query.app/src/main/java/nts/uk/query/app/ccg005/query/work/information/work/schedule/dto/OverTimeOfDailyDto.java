package nts.uk.query.app.ccg005.query.work.information.work.schedule.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OverTimeOfDailyDto {
	// 残業枠時間帯
	private List<OverTimeFrameTimeSheetDto> overTimeWorkFrameTimeSheet;
	// 残業枠時間
	private List<OverTimeFrameTimeDto> overTimeWorkFrameTime;
	// 法定外深夜時間 (所定外深夜時間)
	private ExcessOverTimeWorkMidNightTimeDto excessOverTimeWorkMidNightTime;
	// 残業拘束時間
	private Integer overTimeWorkSpentAtWork;
	// 変形法定内残業
	private Integer irregularWithinPrescribedOverTimeWork;
	// フレックス時間
	private FlexTimeDto flexTime;
}
