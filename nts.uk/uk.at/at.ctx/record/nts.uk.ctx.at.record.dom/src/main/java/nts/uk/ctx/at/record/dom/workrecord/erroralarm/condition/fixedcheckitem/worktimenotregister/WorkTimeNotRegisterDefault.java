package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.worktimenotregister;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.worktime.worktimeset.CheckExistWorkTimeAdapter;
import nts.uk.ctx.at.record.dom.workrecord.daily.erroralarm.createerrorforemployee.CreateErrorForEmployeeService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTimeNotRegisterDefault implements WorkTimeNotRegisterService {

	@Inject
	private CheckExistWorkTimeAdapter checkExistWorkTimeAdapter;
	
	@Inject
	private CreateErrorForEmployeeService createErrorForEmployeeService;
	
	@Override
	public boolean checkWorkTimeNotRegister(String employeeID, GeneralDate date, String workTimeCD) {
		String companyID = AppContexts.user().companyId();
		//就業時間帯CDがドメインモデル「就業時間帯の設定」に存在するかをチェックする
		boolean check = checkExistWorkTimeAdapter.checkExistWorkTimeAdapter(workTimeCD);
		//ドメインに存在する場合
		if(check)
			return true;
		String errorCode = "S024";
		List<Integer> listTimeItemID = new ArrayList<>();
		listTimeItemID.add(13);
		return createErrorForEmployeeService.createErrorForEmployeeService(companyID, employeeID, date, errorCode, listTimeItemID);
	}

}
