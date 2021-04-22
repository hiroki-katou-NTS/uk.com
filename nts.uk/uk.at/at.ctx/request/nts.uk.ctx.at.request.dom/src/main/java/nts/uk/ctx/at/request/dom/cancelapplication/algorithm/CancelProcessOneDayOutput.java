package nts.uk.ctx.at.request.dom.cancelapplication.algorithm;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.request.dom.applicationreflect.object.OneDayReflectStatusOutput;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author thanh_nx
 *
 *         1日分の取消処理Ouput
 */
@AllArgsConstructor
@Data
public class CancelProcessOneDayOutput {

	// <output>1日の反映状態
	private OneDayReflectStatusOutput oneDayReflect;

	// 勤務予定
	private IntegrationOfDaily schedule;

	// 日別実績
	private IntegrationOfDaily workRecord;
	
	private AtomTask task;
}
