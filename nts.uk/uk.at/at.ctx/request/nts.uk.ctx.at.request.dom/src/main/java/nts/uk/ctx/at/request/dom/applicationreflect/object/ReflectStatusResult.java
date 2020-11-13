package nts.uk.ctx.at.request.dom.applicationreflect.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily;
import nts.uk.ctx.at.request.dom.application.ReflectedState;

/**
 * @author thanh_nx
 *
 *         反映状態
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReflectStatusResult {

	// 反映状態
	private ReflectedState reflectStatus;

	// 日別実績反映不可理由
	private ReasonNotReflectDaily reasonNotReflectWorkRecord;

	// 予定反映不可理由
	private ReasonNotReflect reasonNotReflectWorkSchedule;
}
