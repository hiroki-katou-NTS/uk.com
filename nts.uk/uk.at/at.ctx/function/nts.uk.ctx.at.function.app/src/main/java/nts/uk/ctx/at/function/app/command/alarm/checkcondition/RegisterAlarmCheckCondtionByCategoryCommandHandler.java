package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.AlarmCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategory;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckConditionByCategoryRepository;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.AlarmCheckTargetCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.ExtractionCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.daily.DailyAlarmCondition;
import nts.uk.ctx.at.function.dom.alarm.checkcondition.fourweekfourdayoff.AlarmCheckCondition4W4D;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class RegisterAlarmCheckCondtionByCategoryCommandHandler
		extends CommandHandler<AlarmCheckConditionByCategoryCommand> {

	@Inject
	private AlarmCheckConditionByCategoryRepository conditionRepo;

	@Override
	protected void handle(CommandHandlerContext<AlarmCheckConditionByCategoryCommand> context) {
		AlarmCheckConditionByCategoryCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		if (command.getAction() == 0
				&& conditionRepo.isCodeExist(companyId, command.getCategory(), command.getCode())) {
			throw new BusinessException("Msg_3");
		}

		// msg_832 extractcondition > 0

		Optional<AlarmCheckConditionByCategory> domainOpt = conditionRepo.find(companyId, command.getCategory(),
				command.getCode());
		if (domainOpt.isPresent()) {
			// update
			AlarmCheckConditionByCategory domain = domainOpt.get();
			domain.changeState(command.getName(), command.getTargetCondition().isFilterByEmployment(),
					command.getTargetCondition().isFilterByClassification(),
					command.getTargetCondition().isFilterByJobTitle(),
					command.getTargetCondition().isFilterByBusinessType(),
					command.getTargetCondition().getTargetEmployment(),
					command.getTargetCondition().getTargetClassification(),
					command.getTargetCondition().getTargetJobTitle(),
					command.getTargetCondition().getTargetBusinessType(), command.getAvailableRoles());
			//TODO: update WorkRecordExtractCondition
			//TODO: update FixedWorkRecordExtractCondition
			conditionRepo.update(domain);
		} else {
			// add
			ExtractionCondition extractionCondition = null;
			AlarmCategory category = AlarmCategory.values()[command.getCategory()];
			switch (category) {
			case DAILY:
				extractionCondition = command.getDailyAlarmCheckCondition() == null ? null
						: new DailyAlarmCondition(IdentifierUtil.randomUniqueId(),
								command.getDailyAlarmCheckCondition().getConditionToExtractDaily(),
								command.getDailyAlarmCheckCondition().isAddApplication(),
								command.getDailyAlarmCheckCondition().getListExtractConditionWorkRecork(),
								command.getDailyAlarmCheckCondition().getListFixedExtractConditionWorkRecord(),
								command.getDailyAlarmCheckCondition().getListErrorAlarmCode());
				break;
			case SCHEDULE_4WEEK:
				extractionCondition = command.getSchedule4WeekAlarmCheckCondition() == null ? null
						: new AlarmCheckCondition4W4D(
								command.getSchedule4WeekAlarmCheckCondition().getSchedule4WeekCheckCondition());
				break;
			default:
				break;
			}
			AlarmCheckConditionByCategory domain = new AlarmCheckConditionByCategory(companyId, command.getCategory(),
					command.getCode(), command.getName(),
					new AlarmCheckTargetCondition(IdentifierUtil.randomUniqueId(),
							command.getTargetCondition().isFilterByBusinessType(),
							command.getTargetCondition().isFilterByJobTitle(),
							command.getTargetCondition().isFilterByEmployment(),
							command.getTargetCondition().isFilterByClassification(),
							command.getTargetCondition().getTargetBusinessType(),
							command.getTargetCondition().getTargetJobTitle(),
							command.getTargetCondition().getTargetEmployment(),
							command.getTargetCondition().getTargetClassification()),
					command.getAvailableRoles(), extractionCondition);
			
			//TODO: add WorkRecordExtractCondition
			//TODO: add FixedWorkRecordExtractCondition
			conditionRepo.add(domain);
		}
	}

}
