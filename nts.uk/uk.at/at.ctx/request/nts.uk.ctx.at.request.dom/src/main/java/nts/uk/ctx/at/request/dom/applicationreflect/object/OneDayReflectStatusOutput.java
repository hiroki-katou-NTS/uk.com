package nts.uk.ctx.at.request.dom.applicationreflect.object;

import java.util.Arrays;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.DailyAttendanceUpdateStatus;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
import nts.uk.ctx.at.request.dom.application.ReflectionStatus;
import nts.uk.ctx.at.request.dom.application.ReflectionStatusOfDay;

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

	public ReflectionStatus createReflectStatus(GeneralDate date) {
		return new ReflectionStatus(Arrays.asList(new ReflectionStatusOfDay(statusWorkRecord.getReflectStatus(),
				statusWorkSchedule.getReflectStatus(), date,
				Optional.of(DailyAttendanceUpdateStatus.createNew(null, null,
						statusWorkRecord.getReasonNotReflectWorkRecord().value,
						statusWorkSchedule.getReasonNotReflectWorkSchedule().value)),
				Optional.empty())));
	}
	
	public boolean reflect() {
		return this.statusWorkRecord.getReflectStatus() == ReflectedState.REFLECTED
				|| this.statusWorkSchedule.getReflectStatus() == ReflectedState.REFLECTED;
	}
}
