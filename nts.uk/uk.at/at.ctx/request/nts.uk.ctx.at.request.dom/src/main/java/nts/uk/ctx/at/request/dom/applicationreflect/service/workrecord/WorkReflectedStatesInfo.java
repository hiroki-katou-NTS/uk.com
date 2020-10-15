package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

/*import nts.uk.ctx.at.request.dom.application.ReasonNotReflect_New;*/
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
/**
 * 勤務予定に反映の反映状態
 * @author dudt
 *
 */
@AllArgsConstructor
@Setter
@Getter
public class WorkReflectedStatesInfo {
	/** 反映状態	 */
	private ReflectedState reflectedSate;
	/** 反映不可理由	 */
	private ReasonNotReflectDaily notReflectReson;

}
