package nts.uk.ctx.at.record.pub.dailyperform.appreflect;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AppCommonPara {
	/**
	 * 反映先区分
	 */
	private DegreeReflectionPubAtr degressAtr;
	/**
	 * 実行種別
	 */
	private ExecutionPubType executiontype;
	/**
	 * 実績反映状態
	 */
	private ReflectedStatePubRecord stateReflectionReal;
	/**
	 * 予定反映状態
	 */
	private ReflectedStatePubRecord stateReflection;
}
