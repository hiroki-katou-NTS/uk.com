package nts.uk.ctx.at.schedule.pub.schedule.workschedule.snapshot;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;

@Data
@Builder
public class DailySnapshotWorkExport {

	/** 社員ID: 社員ID */
	private String sid;
	
	/** 年月日: 年月日 */
	private GeneralDate ymd;
	
	/** スナップショット: スナップショット（仮） */
	private SnapShotExport snapshot;
	
}
