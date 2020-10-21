package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.snapshot;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.snapshot.SnapShot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;

/** スナップショットを作成する */
public class CreateNoScheduleSnapshotService {
	
	/** スケジュール管理しない場合の作成 (勤務情報: 勤務情報): スナップショット（仮） */
	public static SnapShot createForScheduleNoManaged(WorkInfoOfDailyAttendance workInfo) {
		
		/** スナップショットを作成する */
		return SnapShot.of(workInfo.getRecordInfo(), new AttendanceTime(0));
	}
}
