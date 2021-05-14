package nts.uk.ctx.at.record.dom.applicationcancel.removeappreflect;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.adapter.request.application.state.RCReflectStatusResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author thanh_nx
 *
 *         勤務実績を申請反映前に戻すOutput
 */
@AllArgsConstructor
@Data
public class RCRecoverAppReflectOutput {

	// output>反映状態
	private RCReflectStatusResult reflectStatus;

	// 日別実績
	private Optional<IntegrationOfDaily> workRecord;

	//
	private AtomTask atomTask;
}
