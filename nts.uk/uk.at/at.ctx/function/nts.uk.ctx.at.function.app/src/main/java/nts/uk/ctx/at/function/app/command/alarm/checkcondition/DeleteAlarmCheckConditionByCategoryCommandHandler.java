package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.DeleteAgreeCondOtCommand;
import nts.uk.ctx.at.function.app.command.alarm.checkcondition.agree36.DeleteAgreeConditionErrorCommand;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapter;
import nts.uk.ctx.at.function.dom.adapter.monthlycheckcondition.FixedExtraMonFunAdapter;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeCondOtRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.agree36.IAgreeConditionErrorRepository;
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
	private WorkRecordExtraConAdapter workRecordExtraConRepo;
	
	@Inject
	private FixedConWorkRecordAdapter fixedConWorkRecordRepo;
	
	//monthly
	@Inject
	private FixedExtraMonFunAdapter fixedExtraMonFunAdapter;
	// ３６協定
	@Inject
	private IAgreeConditionErrorRepository conErrRep;

	@Inject
	private IAgreeCondOtRepository otRep;
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
		
		if (command.getCategory() == AlarmCategory.MONTHLY.value) {
			// delete List Work Record Extract Condition by list Error Alarm Code
			
			// delete List Fixed Work Record Extract Condition by list Error Alarm Code
			if(command.getMonAlarmCheckCon().getListFixExtraMon().size()>0) {
				String monAlarmCheckID =  command.getMonAlarmCheckCon().getListFixExtraMon().get(0).getMonAlarmCheckID();
				this.fixedExtraMonFunAdapter.deleteFixedExtraMon(monAlarmCheckID);
			}
		}
		if (command.getCategory() == AlarmCategory.AGREEMENT.value) {
			List<DeleteAgreeConditionErrorCommand> listErrorDel = context.getCommand().getDeleteCondError();  
			for(DeleteAgreeConditionErrorCommand obj : listErrorDel){
				this.conErrRep.delete(obj.getCode(), obj.getCategory());
			}
			
			
			List<DeleteAgreeCondOtCommand> listOtDel = context.getCommand().getDeleteCondOt();
			for(DeleteAgreeCondOtCommand item : listOtDel){
				this.otRep.delete(item.getCode(), item.getCategory());
			}
		}
		conditionRepo.delete(companyId, command.getCategory(), command.getCode());
	}

}
