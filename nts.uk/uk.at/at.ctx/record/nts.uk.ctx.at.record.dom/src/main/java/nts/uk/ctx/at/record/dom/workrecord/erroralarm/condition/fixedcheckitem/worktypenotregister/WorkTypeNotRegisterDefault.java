package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.worktypenotregister;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.worktype.CheckExistWorkTypeAdapter;
import nts.uk.ctx.at.record.dom.workrecord.daily.erroralarm.createerrorforemployee.CreateErrorForEmployeeService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTypeNotRegisterDefault implements WorkTypeNotRegisterService {

	@Inject
	private CheckExistWorkTypeAdapter checkExistWorkTypeAdapter;
	
	@Inject
	private CreateErrorForEmployeeService createErrorForEmployeeService;
	@Override
	public boolean checkWorkTypeNotRegister(String employeeID, GeneralDate date, String workTypeCD) {
		String companyID = AppContexts.user().companyId();
		// 勤務種類CDがドメインモデル「勤務種類」に存在するかをチェックする
		boolean check = checkExistWorkTypeAdapter.checkExistWorkType(workTypeCD);
		//ドメインに存在する場合
		if(check)
			return false;
		//社員の日別実績のエラーを作成する
		String errorCode = "S023";
		List<Integer> listTimeItemID  = new ArrayList<>();
		listTimeItemID.add(12);
		return createErrorForEmployeeService.createErrorForEmployeeService(companyID, employeeID, date, errorCode, listTimeItemID);
	}

}
