package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@AllArgsConstructor
@Setter
@Getter
public class ApplicationReflectOutput {
	/**
	 * 反映状態
	 */
	private ReflectedStateRecord reflectedState;
	/**
	 * 反映不可理由
	 */
	private ReasonNotReflectRecord reasonNotReflect;
}
