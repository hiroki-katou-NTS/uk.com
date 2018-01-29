package nts.uk.ctx.at.function.app.command.alarm.checkcondition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.FixedConWorkRecordAdapterDto;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapter;
import nts.uk.ctx.at.function.dom.adapter.WorkRecordExtraConAdapterDto;
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
	
	@Inject
	private WorkRecordExtraConAdapter workRecordExtraConRepo;
	
	@Inject
	private FixedConWorkRecordAdapter fixedConWorkRecordRepo;

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
			AlarmCheckTargetCondition targetConditionValue = new AlarmCheckTargetCondition("",
					command.getTargetCondition().isFilterByBusinessType(),
					command.getTargetCondition().isFilterByJobTitle(),
					command.getTargetCondition().isFilterByEmployment(),
					command.getTargetCondition().isFilterByClassification(),
					command.getTargetCondition().getTargetBusinessType(),
					command.getTargetCondition().getTargetJobTitle(),
					command.getTargetCondition().getTargetEmployment(),
					command.getTargetCondition().getTargetClassification());
			
			ExtractionCondition extractionCondition = null;
			AlarmCategory category = AlarmCategory.values()[command.getCategory()];
			switch (category) {
			case DAILY:
				extractionCondition = command.getDailyAlarmCheckCondition() == null ? null
						: new DailyAlarmCondition(IdentifierUtil.randomUniqueId(),
								command.getDailyAlarmCheckCondition().getConditionToExtractDaily(),
								command.getDailyAlarmCheckCondition().isAddApplication(),
								command.getDailyAlarmCheckCondition().getListExtractConditionWorkRecork().stream().map(item -> item.getErrorAlarmCheckID()).collect(Collectors.toList()),
								command.getDailyAlarmCheckCondition().getListErrorAlarmCode());
				//update WorkRecordExtractCondition
				DailyAlarmCondition dailyAlramCondition = (DailyAlarmCondition) domain.getExtractionCondition();
				this.workRecordExtraConRepo.checkUpdateListErAl(
						dailyAlramCondition.getExtractConditionWorkRecord(),
						command.getDailyAlarmCheckCondition().getListExtractConditionWorkRecork());

				// update FixedWorkRecordExtractCondition
				for(FixedConWorkRecordAdapterDto fixedConWorkRecordAdapterDto : command.getDailyAlarmCheckCondition().getListFixedExtractConditionWorkRecord()) {
					if (fixedConWorkRecordAdapterDto.getDailyAlarmConID() == null || fixedConWorkRecordAdapterDto.getDailyAlarmConID().equals("")) {
						fixedConWorkRecordAdapterDto.setDailyAlarmConID(dailyAlramCondition.getDailyAlarmConID());
						this.fixedConWorkRecordRepo.addFixedConWorkRecordPub(fixedConWorkRecordAdapterDto);
					} else {
						this.fixedConWorkRecordRepo.updateFixedConWorkRecordPub(fixedConWorkRecordAdapterDto);
					}
				}
				break;
			case SCHEDULE_4WEEK:
				extractionCondition = command.getSchedule4WeekAlarmCheckCondition() == null ? null
						: new AlarmCheckCondition4W4D(IdentifierUtil.randomUniqueId(),
								command.getSchedule4WeekAlarmCheckCondition().getSchedule4WeekCheckCondition());
				break;
			default:
				break;
			}
			domain.changeState(command.getName(), command.getAvailableRoles(), targetConditionValue, extractionCondition);
			
			
			conditionRepo.update(domain);
		} else {
			// add
			ExtractionCondition extractionCondition = null;
			AlarmCategory category = AlarmCategory.values()[command.getCategory()];
			switch (category) {
			case DAILY:
				String dailyAlarmId = IdentifierUtil.randomUniqueId();
				//add WorkRecordExtractCondition
				List<String> listErrorAlarmId = this.workRecordExtraConRepo.addNewListErAl(command.getDailyAlarmCheckCondition().getListExtractConditionWorkRecork());
				
				extractionCondition = command.getDailyAlarmCheckCondition() == null ? null
						: new DailyAlarmCondition(dailyAlarmId,
								command.getDailyAlarmCheckCondition().getConditionToExtractDaily(),
								command.getDailyAlarmCheckCondition().isAddApplication(),
								listErrorAlarmId,
								command.getDailyAlarmCheckCondition().getListErrorAlarmCode()
								);
				
				//add FixedWorkRecordExtractCondition
				for(FixedConWorkRecordAdapterDto fixedConWorkRecordAdapterDto : command.getDailyAlarmCheckCondition().getListFixedExtractConditionWorkRecord()) {
					fixedConWorkRecordAdapterDto.setDailyAlarmConID(dailyAlarmId);
					this.fixedConWorkRecordRepo.addFixedConWorkRecordPub(fixedConWorkRecordAdapterDto);
				}
				break;
			case SCHEDULE_4WEEK:
				extractionCondition = command.getSchedule4WeekAlarmCheckCondition() == null ? null
						: new AlarmCheckCondition4W4D(IdentifierUtil.randomUniqueId(),
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
			
			conditionRepo.add(domain);
		}
	}

}
