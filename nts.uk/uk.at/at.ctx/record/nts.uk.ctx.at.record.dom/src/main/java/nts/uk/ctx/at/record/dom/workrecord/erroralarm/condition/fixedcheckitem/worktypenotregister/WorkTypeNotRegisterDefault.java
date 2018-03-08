package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.worktypenotregister;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.worktype.CheckExistWorkTypeAdapter;
import nts.uk.ctx.at.record.dom.workrecord.daily.erroralarm.createerrorforemployee.CreateErrorForEmployeeService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionDataRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class WorkTypeNotRegisterDefault implements WorkTypeNotRegisterService {

	@Inject
	private CheckExistWorkTypeAdapter checkExistWorkTypeAdapter;
	
	@Inject
	private CreateErrorForEmployeeService createErrorForEmployeeService;
	
	@Inject
	private FixedConditionDataRepository fixedConditionDataRepository;
	@Override
	public ValueExtractAlarmWR checkWorkTypeNotRegister(String workplaceID,String employeeID, GeneralDate date, String workTypeCD) {
		String companyID = AppContexts.user().companyId();
		// 勤務種類CDがドメインモデル「勤務種類」に存在するかをチェックする
		boolean check = checkExistWorkTypeAdapter.checkExistWorkType(workTypeCD);
		//ドメインに存在する場合
		if(check)
			return null;
		//社員の日別実績のエラーを作成する
		String errorCode = "S023";
		List<Integer> listTimeItemID  = new ArrayList<>();
		listTimeItemID.add(12);
		String comment = fixedConditionDataRepository.getAllFixedConditionData().get(0).getMessage().v();
		
		ValueExtractAlarmWR valueExtractAlarmWR = createErrorForEmployeeService.createErrorForEmployeeService(workplaceID,companyID, employeeID, date, errorCode, listTimeItemID);
		valueExtractAlarmWR.setAlarmItem(TextResource.localize("KAL010_6"));
		valueExtractAlarmWR.setAlarmValueMessage(TextResource.localize("KAL010_7",errorCode));
		valueExtractAlarmWR.setComment(comment);
		return valueExtractAlarmWR;
		
	}

}

