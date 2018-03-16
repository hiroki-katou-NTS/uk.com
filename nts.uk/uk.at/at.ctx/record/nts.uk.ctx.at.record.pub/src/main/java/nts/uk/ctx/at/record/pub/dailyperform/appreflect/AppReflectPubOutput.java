package nts.uk.ctx.at.record.pub.dailyperform.appreflect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Getter
@Setter
public class AppReflectPubOutput {
	/**
	 * 反映状態
	 */
	private ReflectedStatePubRecord reflectedState;
	/**
	 * 反映不可理由
	 */
	private ReasonNotReflectPubRecord reasonNotReflect;
}
