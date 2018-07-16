package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.worktypenotregister;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	private final static String ERROR_CODE = "S023";
	private final static List<Integer> LIST_TIME_ITEM_ID = new ArrayList<>();
	static {
		LIST_TIME_ITEM_ID.add(12);
	}
	@Override
	public Optional<ValueExtractAlarmWR> checkWorkTypeNotRegister(String workplaceID,String employeeID, GeneralDate date, String workTypeCD) {
		String companyID = AppContexts.user().companyId();
		// 勤務種類CDがドメインモデル「勤務種類」に存在するかをチェックする
		boolean check = checkExistWorkTypeAdapter.checkExistWorkType(workTypeCD);
		//ドメインに存在する場合
		if(check || workTypeCD ==null)
			return Optional.empty();
		//社員の日別実績のエラーを作成する
		String comment = fixedConditionDataRepository.getFixedByNO(1).get().getMessage().v();
		
		Optional<ValueExtractAlarmWR> valueExtractAlarmWR = createErrorForEmployeeService.createErrorForEmployeeService(workplaceID,companyID, employeeID, date);
		if(valueExtractAlarmWR.isPresent()) {
		valueExtractAlarmWR.get().setAlarmItem(TextResource.localize("KAL010_6"));
		valueExtractAlarmWR.get().setAlarmValueMessage(TextResource.localize("KAL010_7", workTypeCD));
		valueExtractAlarmWR.get().setComment(Optional.ofNullable(comment));
		return valueExtractAlarmWR;
		}
		return Optional.empty();
		
	}

}

