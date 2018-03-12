package nts.uk.ctx.at.schedule.pub.applicationreflectprocess.applicationsmanager;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ReflectedStatesScheInfoDto {
	/** 反映状態	 */
	private ReflectedStateSchePub reflectedSate;
	/** 反映不可理由	 */
	private ReasonNotReflectSchePub notReflectReson;
}
