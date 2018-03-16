package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect;

import javax.ejb.Stateless;

@Stateless
public class CommonProcessCheckServiceImpl implements CommonProcessCheckService{

	@Override
	public boolean commonProcessCheck(CommonCheckParameter para) {
		ReflectedStateRecord state;
		if(para.getExecutiontype() == ExecutionType.RETURN) {
			return true;
		}
		//実績反映状態
		if(para.getDegressAtr() == DegreeReflectionAtr.RECORD) {
			state = para.getStateReflectionReal();
		} else {
			state = para.getStateReflection();
		}
		if(state == ReflectedStateRecord.WAITREFLECTION) {
			return true;
		}
		return false;
	}

}
