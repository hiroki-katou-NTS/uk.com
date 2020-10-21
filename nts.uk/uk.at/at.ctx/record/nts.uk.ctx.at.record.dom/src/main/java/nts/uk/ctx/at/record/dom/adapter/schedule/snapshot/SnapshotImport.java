package nts.uk.ctx.at.record.dom.adapter.schedule.snapshot;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;

@Data
@Builder
public class SnapshotImport {

	/** 勤務種類コード */
	private String workType;

	/** 就業時間帯コード */
	private Optional<String> workTime;
	
	/** 所定時間: 勤怠時間 */
	private int predetermineTime;
	
	public SnapShot toDomain() {
		
		return SnapShot.of(new WorkInformation(workType, workTime.orElse(null)),
							new AttendanceTime(predetermineTime));
	}
}