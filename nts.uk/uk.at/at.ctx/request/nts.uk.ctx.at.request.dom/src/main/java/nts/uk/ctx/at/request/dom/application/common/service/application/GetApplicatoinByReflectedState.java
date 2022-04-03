package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ReflectedState;

/**
 * 指定した承認状況の申請を取得する
 */
public class GetApplicatoinByReflectedState {
	
	public List<Application> get(Require require, String employeeId, GeneralDate targetDate, ReflectedState states){
		return require.getApplicationBy(employeeId, targetDate, states);
	}
	
	public interface Require{
		List<Application> getApplicationBy(String employeeId, GeneralDate targetDate, ReflectedState states);
	}
}
