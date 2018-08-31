package nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.worktimenotregister;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.worktime.worktimeset.CheckExistWorkTimeAdapter;
import nts.uk.ctx.at.record.dom.workrecord.daily.erroralarm.createerrorforemployee.CreateErrorForEmployeeService;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionDataRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.condition.fixedcheckitem.checkprincipalunconfirm.ValueExtractAlarmWR;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class WorkTimeNotRegisterDefault implements WorkTimeNotRegisterService {

	@Inject
	private CheckExistWorkTimeAdapter checkExistWorkTimeAdapter;
	
	@Inject
	private CreateErrorForEmployeeService createErrorForEmployeeService;
	
	@Inject
	private FixedConditionDataRepository fixedConditionDataRepository;
	
	private final static String ERROR_CODE = "S024";
	private final static List<Integer> LIST_TIME_ITEM_ID = new ArrayList<>();
	static {
		LIST_TIME_ITEM_ID.add(13);
	}
	
	@Override
	public Optional<ValueExtractAlarmWR> checkWorkTimeNotRegister( String workplaceID,String employeeID, GeneralDate date, String workTimeCD) {
		String companyID = AppContexts.user().companyId();
		//就業時間帯CDがドメインモデル「就業時間帯の設定」に存在するかをチェックする
		boolean check = checkExistWorkTimeAdapter.checkExistWorkTimeAdapter(workTimeCD);
		//ドメインに存在する場合 and 就業時間帯　=　NULLの場合
		if(check || workTimeCD==null)
			return Optional.empty();
		String comment = fixedConditionDataRepository.getFixedByNO(2).get().getMessage().v();
		Optional<ValueExtractAlarmWR> valueExtractAlarmWR = createErrorForEmployeeService.createErrorForEmployeeService(workplaceID,companyID, employeeID, date);
		if(valueExtractAlarmWR.isPresent()) {
			valueExtractAlarmWR.get().setAlarmItem(TextResource.localize("KAL010_8"));
			valueExtractAlarmWR.get().setAlarmValueMessage(TextResource.localize("KAL010_9",workTimeCD));
			valueExtractAlarmWR.get().setComment(Optional.ofNullable(comment));
			return valueExtractAlarmWR;
		}
		return Optional.empty();
	}

}
