package nts.uk.ctx.at.schedule.pub.appreflectprocess.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author thanh_nx
 *
 *         反映状態
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SCReflectStatusResultExport {

	// 反映状態
	private SCReflectedStateExport reflectStatus;

	// 日別実績反映不可理由
	private SCReasonNotReflectDailyExport reasonNotReflectWorkRecord;

	// 予定反映不可理由
	private SCReasonNotReflectExport reasonNotReflectWorkSchedule;
}
