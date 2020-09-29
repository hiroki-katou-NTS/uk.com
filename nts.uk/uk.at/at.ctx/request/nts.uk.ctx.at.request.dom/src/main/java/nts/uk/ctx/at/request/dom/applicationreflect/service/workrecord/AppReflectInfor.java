package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.ReflectedState;
@AllArgsConstructor
@Setter
@Getter
public class AppReflectInfor {
	/**
	 * 反映先区分
	 */
	private AppDegreeReflectionAtr degressAtr;
	
	/**
	 * 実行種別
	 */
	private AppExecutionType executiontype;
	/**
	 * 実績反映状態
	 */
	private ReflectedState stateReflection;
	/**
	 * 予定反映状態
	 */
	private ReflectedState stateReflectionReal;
	
}
