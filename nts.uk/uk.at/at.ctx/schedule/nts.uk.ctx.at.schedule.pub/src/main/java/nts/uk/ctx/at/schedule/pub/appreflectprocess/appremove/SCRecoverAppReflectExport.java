package nts.uk.ctx.at.schedule.pub.appreflectprocess.appremove;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.schedule.pub.appreflectprocess.appremove.export.ReflectStatusResultExport;

/**
 * @author thanh_nx
 *
 *         勤務予定を申請反映前に戻すExport
 */
@Data
@AllArgsConstructor
public class SCRecoverAppReflectExport {

	// output>反映状態
	private ReflectStatusResultExport reflectStatus;

	// 勤務予定
	private Object domainSchedule;

	//
	private AtomTask atomTask;

}
