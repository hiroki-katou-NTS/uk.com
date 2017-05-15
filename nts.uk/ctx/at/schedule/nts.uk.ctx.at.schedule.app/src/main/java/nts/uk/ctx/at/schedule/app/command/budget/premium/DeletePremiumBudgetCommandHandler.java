package nts.uk.ctx.at.schedule.app.command.budget.premium;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
public class DeletePremiumBudgetCommandHandler extends CommandHandler<DeletePremiumBudgetCommand>{

	@Inject
	private PersonCostCalculationDomainService personCostCalculationDomainService;
	
	@Override
	protected void handle(CommandHandlerContext<DeletePremiumBudgetCommand> context) {
		String companyID = AppContexts.user().companyId();
		DeletePremiumBudgetCommand budgetCommand = context.getCommand();
		this.personCostCalculationDomainService.deletePersonCostCalculation(
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
