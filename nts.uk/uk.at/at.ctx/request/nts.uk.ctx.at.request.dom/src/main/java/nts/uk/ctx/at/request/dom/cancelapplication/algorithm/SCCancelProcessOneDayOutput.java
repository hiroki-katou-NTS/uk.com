package nts.uk.ctx.at.request.dom.cancelapplication.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.request.dom.applicationreflect.object.ReflectStatusResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author thanh_nx
 *
 *         勤務予定の取消処理Output
 */
@AllArgsConstructor
@Data
public class SCCancelProcessOneDayOutput {

	// <output>反映状態
	private ReflectStatusResult statusWorkSchedule;

	// 日別実績
	private IntegrationOfDaily schedule;

	//
	private AtomTask atomTask;
}
