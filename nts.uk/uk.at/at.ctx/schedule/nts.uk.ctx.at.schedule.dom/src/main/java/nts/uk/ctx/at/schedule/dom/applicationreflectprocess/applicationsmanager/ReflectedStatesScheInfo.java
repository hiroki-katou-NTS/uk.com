package nts.uk.ctx.at.schedule.dom.applicationreflectprocess.applicationsmanager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * out put 反映状態
 * @author dudt
 *
 */
@AllArgsConstructor
@Setter
@Getter
public class ReflectedStatesScheInfo {
	/** 反映状態	 */
	private ReflectedStateSche reflectedSate;
	/** 反映不可理由	 */
	private ReasonNotReflectSche notReflectReson;
}
