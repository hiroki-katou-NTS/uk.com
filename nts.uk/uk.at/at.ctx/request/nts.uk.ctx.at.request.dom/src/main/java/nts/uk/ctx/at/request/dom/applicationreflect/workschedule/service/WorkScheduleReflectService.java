<<<<<<< HEAD:nts.uk/uk.at/at.ctx/request/nts.uk.ctx.at.request.dom/src/main/java/nts/uk/ctx/at/request/dom/applicationreflect/workschedule/service/WorkScheduleReflectService.java
package nts.uk.ctx.at.request.dom.applicationreflect.workschedule.service;
import nts.uk.ctx.at.request.dom.applicationreflect.workschedule.ReflectScheDto;
import nts.uk.ctx.at.request.dom.applicationreflect.workschedule.ReflectedStatesInfo;
=======
package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;

>>>>>>> c6494c277b... 勤務実績に反映: 残業申請 (事前) ALL:nts.uk/uk.at/at.ctx/request/nts.uk.ctx.at.request.dom/src/main/java/nts/uk/ctx/at/request/dom/applicationreflect/service/workschedule/WorkScheduleReflectService.java
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
	public ScheReflectedStatesInfo workscheReflect(ReflectScheDto reflectSheDto);

}
