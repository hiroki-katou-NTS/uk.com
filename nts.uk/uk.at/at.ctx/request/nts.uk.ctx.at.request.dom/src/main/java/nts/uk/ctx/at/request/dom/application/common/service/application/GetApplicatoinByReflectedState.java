package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ReflectedState;

/**
 * 指定した承認状況の申請を取得する
 */
public class GetApplicatoinByReflectedState {
	
	public List<Application> get(Require require, String employeeId, List<ReflectedState> states){
		val parseIntStates = states.stream().map(state -> state.value).collect(Collectors.toList());
		return require.getApplicationBy(employeeId, parseIntStates);
	}
	
	public interface Require{
		List<Application> getApplicationBy(String employeeId, List<Integer> states);
	}
}
