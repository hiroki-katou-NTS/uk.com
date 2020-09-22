package nts.uk.ctx.at.record.app.command.workrule.specific;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.totalrestrainttime.CalculateOfTotalConstraintTime;
import nts.uk.ctx.at.shared.dom.workrule.specific.SpecificWorkRuleRepository;
import nts.uk.ctx.at.shared.dom.workrule.specific.TimeOffVacationPriorityOrder;
import nts.uk.ctx.at.shared.dom.workrule.specific.UpperLimitTotalWorkingHour;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AddSpecificWorkRuleCommandHandler.
 * @author HoangNDH
 */
@Stateless
public class AddSpecificWorkRuleCommandHandler extends CommandHandler<AddSpecificWorkRuleCommand> {
	
	/** The repository. */
	@Inject
	SpecificWorkRuleRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AddSpecificWorkRuleCommand> context) {
		AddSpecificWorkRuleCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		// ドメインモデル「総労働時間の上限値制御」に登録する
		CalculateOfTotalConstraintTime calcSetting = CalculateOfTotalConstraintTime.createFromJavaType(companyId, command.getConstraintCalcMethod());
		Optional<CalculateOfTotalConstraintTime> optCalcSetting = repository.findCalcMethodByCid(companyId);
		if (optCalcSetting.isPresent()) {
			repository.updateCalcMethod(calcSetting);
		}
		else {
			repository.insertCalcMethod(calcSetting);
		}
		
		// ドメインモデル「総拘束時間の計算」に登録する
		UpperLimitTotalWorkingHour limitSetting = UpperLimitTotalWorkingHour.createFromJavaType(companyId, command.getUpperLimitSet());
		Optional<UpperLimitTotalWorkingHour> optLimitSetting = repository.findUpperLimitWkHourByCid(companyId);
		if (optLimitSetting.isPresent()) {
			repository.updateUpperLimitWkHour(limitSetting);
		}
		else {
			repository.insertUpperLimitWkHour(limitSetting);
		}
		
		// ドメインモデル「時間休暇相殺優先順位」に登録する
		TimeOffVacationPriorityOrder orderSetting = TimeOffVacationPriorityOrder.createFromJavaType(companyId, command.getSubstituteHoliday(),
				command.getSixtyHourVacation(), command.getSpecialHoliday(), command.getAnnualHoliday());
		Optional<TimeOffVacationPriorityOrder> optOrderSetting = repository.findTimeOffVacationOrderByCid(companyId);
		if (optOrderSetting.isPresent()) {
			repository.updateTimeOffVacationOrder(orderSetting);
		}
		else {
			repository.insertTimeOffVacationOrder(orderSetting);
		}
	}
}
