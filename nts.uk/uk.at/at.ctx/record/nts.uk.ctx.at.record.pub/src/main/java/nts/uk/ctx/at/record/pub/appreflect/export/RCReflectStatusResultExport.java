package nts.uk.ctx.at.record.pub.appreflect.export;

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
public class RCReflectStatusResultExport {

	// 反映状態
	private RCReflectedStateExport reflectStatus;

	// 日別実績反映不可理由
	private RCReasonNotReflectDailyExport reasonNotReflectWorkRecord;

	// 予定反映不可理由
	private RCReasonNotReflectExport reasonNotReflectWorkSchedule;
}
