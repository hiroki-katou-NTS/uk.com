package nts.uk.ctx.at.shared.dom.application.reflect;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.application.common.ReflectedStateShare;

/**
 * @author thanh_nx
 *
 *         1日の反映状態
 */
@Data
@AllArgsConstructor
public class OneDayReflectStatusShare {

	// 勤務予定の反映状態
	private ReflectStatusResultShare statusWorkSchedule;

	// 勤務実績の反映状態
	private ReflectStatusResultShare statusWorkRecord;

	public OneDayReflectStatusShare() {
		this.statusWorkRecord = new ReflectStatusResultShare();
		this.statusWorkRecord = new ReflectStatusResultShare();
	}

	public void setReflectStatusAll(ReflectedStateShare stateWorkSchedule, ReflectedStateShare stateWorkRecord) {
		this.statusWorkRecord.setReflectStatus(stateWorkRecord);
		this.statusWorkSchedule.setReflectStatus(stateWorkSchedule);
	}

}
