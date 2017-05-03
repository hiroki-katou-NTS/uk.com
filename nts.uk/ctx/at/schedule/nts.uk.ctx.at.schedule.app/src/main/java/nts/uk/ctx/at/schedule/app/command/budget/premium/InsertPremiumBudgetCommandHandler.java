package nts.uk.ctx.at.schedule.app.command.budget.premium;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculation;
import nts.uk.ctx.at.schedule.dom.budget.premium.PersonCostCalculationRepository;
import nts.uk.ctx.at.schedule.dom.budget.premium.UnitPrice;
import nts.uk.shr.com.primitive.Memo;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
@Transactional
public class InsertPremiumBudgetCommandHandler extends CommandHandler<InsertPremiumBudgetCommand>{
	
	@Inject
	private PersonCostCalculationRepository personCostCalculationRepository;
	
	@Override
	protected void handle(CommandHandlerContext<InsertPremiumBudgetCommand> context) {
		InsertPremiumBudgetCommand budgetCommand = context.getCommand();
		Optional<PersonCostCalculation> optional = this.personCostCalculationRepository.find(budgetCommand.getCID(), budgetCommand.getHID());
		if(optional.isPresent()) throw new RuntimeException("Item already Exist");
		this.personCostCalculationRepository.add(
				PersonCostCalculation.createFromJavaType(
							budgetCommand.getCID(), 
							new Memo(budgetCommand.getMemo()), 
							EnumAdaptor.valueOf(budgetCommand.getUnitprice(), UnitPrice.class), 
							budgetCommand.getStartDate(), 
							budgetCommand.getEndDate()
					)
		);
	}
	
}
