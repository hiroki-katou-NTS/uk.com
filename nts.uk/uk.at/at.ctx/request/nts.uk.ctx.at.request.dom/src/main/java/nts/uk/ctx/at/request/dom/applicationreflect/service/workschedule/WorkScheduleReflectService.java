package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application_New;

/**
 * 勤務予定に反映
 * @author dudt
 *
 */
public interface WorkScheduleReflectService {
	/**
	 * 勤務予定に反映
	 * @param executionTupe
	 * @return
	 */
	public boolean workscheReflect(ReflectScheDto reflectParam);
	/**
	 * 事前チェック処理
	 * @param employeeId
	 * @param baseDate
	 * @param isReflect
	 * @return
	 */
	public boolean checkBeforeReflected(Application_New application);

}
