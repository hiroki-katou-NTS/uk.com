package nts.uk.ctx.at.shared.dom.application.reflect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.application.common.ReasonNotReflectDailyShare;
import nts.uk.ctx.at.shared.dom.application.common.ReasonNotReflectShare;
import nts.uk.ctx.at.shared.dom.application.common.ReflectedStateShare;

/**
 * @author thanh_nx
 *
 *         反映状態
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReflectStatusResultShare {

	// 反映状態
	private ReflectedStateShare reflectStatus;

	// 日別実績反映不可理由
	private ReasonNotReflectDailyShare reasonNotReflectWorkRecord;

	// 予定反映不可理由
	private ReasonNotReflectShare reasonNotReflectWorkSchedule;
}
