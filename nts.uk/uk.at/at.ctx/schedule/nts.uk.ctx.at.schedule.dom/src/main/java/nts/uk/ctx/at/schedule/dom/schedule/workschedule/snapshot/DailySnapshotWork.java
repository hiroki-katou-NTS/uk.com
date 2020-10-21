package nts.uk.ctx.at.schedule.dom.schedule.workschedule.snapshot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;

@Getter
@NoArgsConstructor
/** スナップショット（仮）作業中 */
public class DailySnapshotWork {

	/** 社員ID: 社員ID */
	private String sid;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;
	
	/** スナップショット: スナップショット（仮） */
	private SnapShot snapshot;
	
	public static DailySnapshotWork of(String sid, GeneralDate ymd, SnapShot snapshot) {
		
		DailySnapshotWork snapshotWork = new DailySnapshotWork();
		snapshotWork.sid = sid;
		snapshotWork.ymd = ymd;
		snapshotWork.snapshot = snapshot;
		
		return snapshotWork;
	}
}
