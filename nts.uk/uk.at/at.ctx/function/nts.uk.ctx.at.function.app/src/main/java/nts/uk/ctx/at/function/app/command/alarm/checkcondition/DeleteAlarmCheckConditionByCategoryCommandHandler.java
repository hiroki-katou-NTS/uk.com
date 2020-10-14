package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.DeleteAgreeCondOtCommand;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.DeleteAgreeConditionErrorCommand;
import nts.uk.ctx.at.function.app.find.alarm.checkcondition.AlarmCheckConditionByCategoryFinder;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunAdapter;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeCondOtRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeConditionErrorRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.IAlarmCheckConAgrRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.annualholiday.IAlarmCheckSubConAgrRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.appapproval.AppApprovalFixedExtractConditionRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.mastercheck.MasterCheckFixedExtractConditionRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.monthly.MonAlarmCheckConEvent;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.multimonth.MulMonAlarmCondEvent;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class DeleteAlarmCheckConditionByCategoryCommandHandler extends CommandHandler<AlarmCheckConditionByCategoryCommand> {

	@Inject
	private AlarmCheckConditionByCategoryRepository conditionRepo;
	
	@Inject 
	private AppApprovalFixedExtractConditionRepository appApprovalFixedExtractConditionRepository;
	
	@Inject
	private WorkRecordExtraConAdapter workRecordExtraConRepo;
	
	@Inject
	private FixedConWorkRecordAdapter fixedConWorkRecordRepo;
	
	//monthly
	@Inject
	private FixedExtraMonFunAdapter fixedExtraMonFunAdapter;
	
	@Inject
	private AlarmCheckConditionByCategoryFinder alarmCheckConByCategoryFinder;
	
	// ３６協定
	@Inject
	private IAgreeConditionErrorRepository conErrRep;

	@Inject
	private IAgreeCondOtRepository otRep;
	
	@Inject
	private IAlarmCheckSubConAgrRepository alarmCheckSubConAgrRepository;
	
	@Inject
	private IAlarmCheckConAgrRepository alarmCheckConAgrRepository;
	
	@Inject
	private MasterCheckFixedExtractConditionRepository fixedMasterCheckConditionRepo;
	
	@Override
	protected void handle(CommandHandlerContext<AlarmCheckConditionByCategoryCommand> context) {
		AlarmCheckConditionByCategoryCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();

		if (command.getCategory() == AlarmCategory.DAILY.value) {
			// delete List Work Record Extract Condition by list Error Alarm Code
			List<String> listErrorAlarmCheckId =  command.getDailyAlarmCheckCondition().getListExtractConditionWorkRecork().stream().map(item -> item.getErrorAlarmCheckID()).collect(Collectors.toList());
			this.workRecordExtraConRepo.deleteWorkRecordExtraConPub(listErrorAlarmCheckId);
			
			// delete List Fixed Work Record Extract Condition by list Error Alarm Code
			String dailyAlarmConID =  command.getDailyAlarmCheckCondition().getListFixedExtractConditionWorkRecord().get(0).getDailyAlarmConID();
			this.fixedConWorkRecordRepo.deleteFixedConWorkRecordPub(dailyAlarmConID);
		}
		
		if (command.getCategory() == AlarmCategory.APPLICATION_APPROVAL.value) {
			String appAlarmConId = command.getApprovalAlarmCheckConDto().getListAppFixedConditionWorkRecordDto().get(0).getAppAlarmConId();
			this.appApprovalFixedExtractConditionRepository.delete(appAlarmConId);
		}
		
		if (command.getCategory() == AlarmCategory.MONTHLY.value) {
			String monAlarmCheckID = "";
			
			// delete List Fixed Work Record Extract Condition by list Error Alarm Code
			if(command.getMonAlarmCheckCon().getListFixExtraMon().size()>0) {
				monAlarmCheckID =  command.getMonAlarmCheckCon().getListFixExtraMon().get(0).getMonAlarmCheckID();
				this.fixedExtraMonFunAdapter.deleteFixedExtraMon(monAlarmCheckID);
			}
			// delete List Work Record Extract Condition by list Error Alarm Code
			List<String> listEralCheckIDOld = alarmCheckConByCategoryFinder.getDataByCode(command.getCategory(), command.getCode())
					.getMonAlarmCheckConDto().getListEralCheckIDOld();
			
			MonAlarmCheckConEvent event = new MonAlarmCheckConEvent(monAlarmCheckID,false,false,true,command.getMonAlarmCheckCon().getArbExtraCon(),listEralCheckIDOld);
			event.toBePublished();
		}
		if (command.getCategory() == AlarmCategory.AGREEMENT.value) {
			List<DeleteAgreeConditionErrorCommand> listErrorDel = command.getCondAgree36().getListCondError().stream()
																		.map(c -> DeleteAgreeConditionErrorCommand.changeType(c)).collect(Collectors.toList());  
			if(!listErrorDel.isEmpty()){
				for(DeleteAgreeConditionErrorCommand obj : listErrorDel){
					this.conErrRep.delete(obj.getCode(), obj.getCategory());
				}
			}

			List<DeleteAgreeCondOtCommand> listOtDel = command.getCondAgree36().getListCondOt().stream()
																.map(x -> DeleteAgreeCondOtCommand.changeType(x)).collect(Collectors.toList());
			if(!listOtDel.isEmpty()){
				for(DeleteAgreeCondOtCommand item : listOtDel){
					this.otRep.delete(item.getCode(), item.getCategory());
				}
			}
		}
		
		if (command.getCategory() == AlarmCategory.MULTIPLE_MONTH.value) {
			String mulMonAlarmCheckID = "";
			
			// delete List Work Record Extract Condition by list Error Alarm Code
			List<String> listEralCheckIDOld = alarmCheckConByCategoryFinder.getDataByCode(command.getCategory(), command.getCode())
					.getMulMonAlarmCheckConDto().getErrorAlarmCheckIDOlds();
			
			MulMonAlarmCondEvent event = new MulMonAlarmCondEvent(mulMonAlarmCheckID,false,false,true,command.getMulMonCheckCond().getListMulMonCheckConds(),listEralCheckIDOld);
			event.toBePublished();
		}
		
		if (command.getCategory() == AlarmCategory.ATTENDANCE_RATE_FOR_HOLIDAY.value) {
			alarmCheckSubConAgrRepository.delete(companyId, command.getCategory(), command.getCode());
			alarmCheckConAgrRepository.delete(companyId, command.getCategory(), command.getCode());
		}
		
		if (command.getCategory() == AlarmCategory.MASTER_CHECK.value) {
			String errorAlarmCheckId =  command.getMasterCheckAlarmCheckCondition().getListFixedMasterCheckCondition().get(0).getErrorAlarmCheckId();
			this.fixedMasterCheckConditionRepo.deleteMasterCheckFixedCondition(errorAlarmCheckId);
		}
		
		conditionRepo.delete(companyId, command.getCategory(), command.getCode());
	}

}
