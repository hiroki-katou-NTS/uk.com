package nts.uk.ctx.at.record.dom.adapter.schedule.snapshot;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;

@Data
@Builder
public class DailySnapshotWorkImport {

	/** 社員ID: 社員ID */
	private String sid;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;
	
	/** スナップショット: スナップショット（仮） */
	private SnapshotImport snapshot;
	
	public static DailySnapshotWorkImport from(String sid, GeneralDate ymd, SnapShot snapshot) {
		
		return DailySnapshotWorkImport.builder()
				.sid(sid)
				.ymd(ymd)
				.snapshot(SnapshotImport.builder()
						.predetermineTime(snapshot.getPredetermineTime().valueAsMinutes())
						.workTime(snapshot.getWorkInfo().getWorkTimeCodeNotNull().map(c -> c.v()))
						.workType(snapshot.getWorkInfo().getWorkTypeCode().v())
						.build())
				.build();
	}
}
