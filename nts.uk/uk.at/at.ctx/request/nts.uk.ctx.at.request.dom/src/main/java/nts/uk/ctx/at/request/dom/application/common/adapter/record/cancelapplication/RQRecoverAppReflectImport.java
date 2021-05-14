package nts.uk.ctx.at.request.dom.application.common.adapter.record.cancelapplication;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.request.dom.applicationreflect.object.ReflectStatusResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author thanh_nx
 *
 *         勤務予定を申請反映前に戻すImport
 */
@AllArgsConstructor
@Data
public class RQRecoverAppReflectImport {

	// output>反映状態
	private ReflectStatusResult reflectStatus;

	// 勤務予定
	private Optional<IntegrationOfDaily> workRecord;

	//
	private AtomTask atomTask;
}
