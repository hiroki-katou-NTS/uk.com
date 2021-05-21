package nts.uk.ctx.at.request.dom.cancelapplication.algorithm;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

/**
 * @author thanh_nx
 *
 *         申請の取消処理Output
 */
@AllArgsConstructor
@Data
public class AppCancellationProcessOutput {

	// 取消処理後の申請
	private Application app;

	// 勤務予定
	private List<IntegrationOfDaily> schedule;

	// 日別実績
	private List<IntegrationOfDaily> workRecord;
	
	private AtomTask atomTask;

}
