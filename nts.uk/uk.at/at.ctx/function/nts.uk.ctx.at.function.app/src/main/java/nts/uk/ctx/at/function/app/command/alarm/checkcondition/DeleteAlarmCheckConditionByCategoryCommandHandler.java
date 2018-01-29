package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapter;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
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
	
	@Override
	protected void handle(CommandHandlerContext<AlarmCheckConditionByCategoryCommand> context) {
		AlarmCheckConditionByCategoryCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		//TODO:
				// When alarm check condition by category is deleted
				// Delete the "time item check of work record (勤務実績の勤怠項目チェック)"
				// and "error item condition of time item (勤怠項目のエラーアラーム条件)"
				// linked to error work alarm check ID of work record
		//TODO: delete List Work Record Extract Condition by list Error Alarm Code
		List<String> listErrorAlarmCheckId =  command.getDailyAlarmCheckCondition().getListExtractConditionWorkRecork().stream().map(item -> item.getErrorAlarmCheckID()).collect(Collectors.toList());
		this.workRecordExtraConRepo.deleteWorkRecordExtraConPub(listErrorAlarmCheckId);
		
		//TODO: delete List Fixed Work Record Extract Condition by list Error Alarm Code
		String dailyAlarmConID =  command.getDailyAlarmCheckCondition().getListFixedExtractConditionWorkRecord().get(0).getDailyAlarmConID();
		this.fixedConWorkRecordRepo.deleteFixedConWorkRecordPub(dailyAlarmConID);
		
		conditionRepo.delete(companyId, command.getCategory(), command.getCode());
	}

}
