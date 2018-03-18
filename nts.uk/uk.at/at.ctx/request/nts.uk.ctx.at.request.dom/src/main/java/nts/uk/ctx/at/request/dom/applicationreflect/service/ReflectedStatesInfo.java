package nts.uk.ctx.at.request.dom.applicationreflect.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
/**
 * 勤務予定に反映の反映状態
 * @author dudt
 *
 */
@AllArgsConstructor
@Setter
@Getter
public class ReflectedStatesInfo {
	/** 反映状態	 */
	private ReflectedState_New reflectedSate;
	/** 反映不可理由	 */
	private ReasonNotReflect_New notReflectReson;

}
