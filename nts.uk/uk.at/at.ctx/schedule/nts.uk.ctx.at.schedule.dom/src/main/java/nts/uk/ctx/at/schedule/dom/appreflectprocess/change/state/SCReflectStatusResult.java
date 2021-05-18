package nts.uk.ctx.at.schedule.dom.appreflectprocess.change.state;

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
public class SCReflectStatusResult {

	// 反映状態
	private SCReflectedState reflectStatus;

	// 日別実績反映不可理由
	private SCReasonNotReflectDaily reasonNotReflectWorkRecord;

	// 予定反映不可理由
	private SCReasonNotReflect reasonNotReflectWorkSchedule;
}
