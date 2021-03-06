package nts.uk.ctx.at.record.pub.cancelapplication;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.pub.appreflect.export.RCReflectStatusResultExport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author thanh_nx
 *
 *         勤務実績を申請反映前に戻すExport
 */
@AllArgsConstructor
@Data
public class RCRecoverAppReflectOutputExport {

	// output>反映状態
	private RCReflectStatusResultExport reflectStatus;

	// 日別実績
	private Optional<IntegrationOfDaily> workRecord;

	//
	private AtomTask atomTask;
}
