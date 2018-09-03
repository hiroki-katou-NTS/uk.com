package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class CommonCheckParameter {
	/**
	 * 反映先区分
	 */
	private DegreeReflectionAtr degressAtr;
	/**
	 * 実行種別
	 */
	private ExecutionType executiontype;
	/**
	 * 実績反映状態
	 */
	private ReflectedStateRecord stateReflectionReal;
	/**
	 * 予定反映状態
	 */
	private ReflectedStateRecord stateReflection;
}
