package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;

@AllArgsConstructor
@Setter
@Getter
public class ReflectRecordInfor {
	/**
	 * 反映先区分
	 */
	private AppDegreeReflectionAtr degressAtr;
	
	/**
	 * 実行種別
	 */
	private AppExecutionType executiontype;
	/**
	 *　申請情報 
	 */
	private Application appInfor;
}
