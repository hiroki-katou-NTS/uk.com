package nts.uk.ctx.at.schedule.pub.schedule.workschedule.snapshot;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SnapShotExport {

	/** 勤務種類コード */
	private String workType;

	/** 就業時間帯コード */
	private Optional<String> workTime;
	
	/** 所定時間: 勤怠時間 */
	private int predetermineTime;
}
