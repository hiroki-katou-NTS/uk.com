package nts.uk.ctx.at.schedule.app.command.budget.premium;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculation;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculationDomainService;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculationRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumName;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumRate;
import nts.uk.ctx.at.schedule.dom.budget.premium.PremiumSetting;
import nts.uk.ctx.at.schedule.dom.budget.premium.UnitPrice;
import nts.uk.ctx.at.schedule.dom.budget.premium.UseClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.primitive.Memo;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
@Transactional
public class UpdatePremiumBudgetCommandHandler extends CommandHandler<UpdatePremiumBudgetCommand>{

	@Inject
	private PersonCostCalculationDomainService personCostCalculationDomainService;
	
	@Override
	protected void handle(CommandHandlerContext<UpdatePremiumBudgetCommand> context) {
		String companyID = AppContexts.user().companyId();
		UpdatePremiumBudgetCommand budgetCommand = context.getCommand();
		this.personCostCalculationDomainService.updatePersonCostCalculation(
				new PersonCostCalculation(
						companyID, 
						budgetCommand.getHistoryID(), 
						new Memo(budgetCommand.getMemo()), 
						EnumAdaptor.valueOf(budgetCommand.getUnitPrice(), UnitPrice.class), 
						GeneralDate.fromString(budgetCommand.getStartDate(), "yyyy/MM/dd"), 
						GeneralDate.fromString(budgetCommand.getEndDate(), "yyyy/MM/dd"),
						budgetCommand.getPremiumSets().stream()
							.map(x -> new PremiumSetting(
									x.getCompanyID(), 
									x.getHistoryID(), 
									x.getAttendanceID(), 
									new PremiumRate(x.getPremiumRate()), 
									new PremiumName(x.getPremiumName()), 
									x.getInternalID(), 
									EnumAdaptor.valueOf(x.getUseAtr(), UseClassification.class), 
									x.getTimeItemIDs()))
							.collect(Collectors.toList())
				)
		);
	}

}
