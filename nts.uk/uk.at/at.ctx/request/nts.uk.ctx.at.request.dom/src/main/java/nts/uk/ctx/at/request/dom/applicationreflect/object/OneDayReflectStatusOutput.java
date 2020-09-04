package nts.uk.ctx.at.request.dom.applicationreflect.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.ReflectedState;

/**
 * @author thanh_nx
 *
 *         1日の反映状態
 */
@Data
@AllArgsConstructor
public class OneDayReflectStatusOutput {

	// 勤務予定の反映状態
	private ReflectStatusResult statusWorkSchedule;

	// 勤務実績の反映状態
	private ReflectStatusResult statusWorkRecord;
	
	public OneDayReflectStatusOutput() {
		this.statusWorkRecord = new ReflectStatusResult();
		this.statusWorkRecord = new ReflectStatusResult();
	}
	
	public void setReflectStatusAll(ReflectedState stateWorkSchedule, ReflectedState stateWorkRecord) {
		this.statusWorkRecord.setReflectStatus(stateWorkRecord);
		this.statusWorkSchedule.setReflectStatus(stateWorkSchedule);
	}

}
