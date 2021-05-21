package nts.uk.ctx.at.schedule.dom.appremove;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.change.state.SCReflectStatusResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author thanh_nx
 *
 *         勤務予定を申請反映前に戻すOutput
 */
@Data
@AllArgsConstructor
public class SCRecoverAppReflectOutput {

	// output>反映状態
	private SCReflectStatusResult reflectStatus;

	// 勤務予定
	private Optional<IntegrationOfDaily> schedule;

	//
	private AtomTask atomTask;

}
