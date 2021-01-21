package nts.uk.ctx.at.request.dom.applicationreflect.service.workschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
@AllArgsConstructor
@Getter
@Setter
public class ScheReflectedStatesInfo {
	/** 反映状態	 */
	private ReflectedState reflectedSate;
	/** 反映不可理由	 */
	private ReasonNotReflect notReflectReson;
}
