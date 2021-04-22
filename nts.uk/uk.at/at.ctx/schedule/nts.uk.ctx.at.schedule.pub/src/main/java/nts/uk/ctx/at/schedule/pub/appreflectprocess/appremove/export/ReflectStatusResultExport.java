package nts.uk.ctx.at.schedule.pub.appreflectprocess.appremove.export;

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
public class ReflectStatusResultExport {

	// 反映状態
	private ReflectedStateExport reflectStatus;

	// 日別実績反映不可理由
	private ReasonNotReflectDailyExport reasonNotReflectWorkRecord;

	// 予定反映不可理由
	private ReasonNotReflectExport reasonNotReflectWorkSchedule;
}
