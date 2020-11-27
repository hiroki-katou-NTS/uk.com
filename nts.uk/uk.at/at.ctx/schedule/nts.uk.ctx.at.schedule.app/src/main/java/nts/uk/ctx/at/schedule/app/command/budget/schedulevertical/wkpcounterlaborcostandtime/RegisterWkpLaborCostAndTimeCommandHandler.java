package nts.uk.ctx.at.schedule.app.command.budget.schedulevertical.wkpcounterlaborcostandtime;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.LaborCostAndTime;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.LaborCostAndTimeType;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTime;
import nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.laborcostandtime.WorkplaceCounterLaborCostAndTimeRepo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 職場計の人件費・時間情報を登録する
 */
@Stateless
public class RegisterWkpLaborCostAndTimeCommandHandler extends CommandHandler<RegisterWkpLaborCostAndTimeCommand> {
	@Inject
	private WorkplaceCounterLaborCostAndTimeRepo repository;

	@Override
	protected void handle(CommandHandlerContext<RegisterWkpLaborCostAndTimeCommand> context) {
		RegisterWkpLaborCostAndTimeCommand command = context.getCommand();
		Map<LaborCostAndTimeType, LaborCostAndTime> laborCostAndTimeList = new HashMap<>();
		command.getLaborCostAndTimes().forEach(x -> laborCostAndTimeList.put(
			EnumAdaptor.valueOf(x.getLaborCostAndTimeType(), LaborCostAndTimeType.class),
			new LaborCostAndTime(
				NotUseAtr.valueOf(x.getUseClassification()),
				NotUseAtr.valueOf(x.getTime()),
				NotUseAtr.valueOf(x.getLaborCost()),
				x.getBudget() == null ? Optional.empty() : Optional.of(NotUseAtr.valueOf(x.getBudget())))
			));
		WorkplaceCounterLaborCostAndTime workplaceCounterLaborCostAndTime = WorkplaceCounterLaborCostAndTime.create(laborCostAndTimeList);

		//1 : 取得する(ログイン会社ID) : Optional<人件費・時間>
		Optional<WorkplaceCounterLaborCostAndTime> wokpLaborCostAndTime = repository.get(AppContexts.user().companyId());
		if (wokpLaborCostAndTime.isPresent()){
			//2 : Optional<人件費・時間>.isPresent==true
			repository.update(AppContexts.user().companyId(), workplaceCounterLaborCostAndTime);
		}else {
			//3 : Optional<人件費・時間>.isPresent==false
			repository.insert(AppContexts.user().companyId(), workplaceCounterLaborCostAndTime);
		}
	}
}
