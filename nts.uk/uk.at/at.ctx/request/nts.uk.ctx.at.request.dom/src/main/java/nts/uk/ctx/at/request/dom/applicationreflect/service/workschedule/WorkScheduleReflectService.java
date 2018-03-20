package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;

import nts.uk.ctx.at.request.dom.applicationreflect.service.ReflectedStatesInfo;

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
	public ReflectedStatesInfo workscheReflect(ReflectScheDto reflectSheDto);

}
