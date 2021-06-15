package nts.uk.ctx.at.record.dom.adapter.request.application.state;

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
public class RCReflectStatusResult {

	// 反映状態
	private RCReflectedState reflectStatus;

	// 日別実績反映不可理由
	private RCReasonNotReflectDaily reasonNotReflectWorkRecord;

	// 予定反映不可理由
	private RCReasonNotReflect reasonNotReflectWorkSchedule;
}
