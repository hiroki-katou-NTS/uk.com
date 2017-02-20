package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

/**
 * AddResidentalTaxCommandHandler
 * @author lanlt
 *
 */
@Stateless
@Transactional
public class AddResidentalTaxCommandHandler extends CommandHandler<AddResidentalTaxCommand>{

	
	@Override
	protected void handle(CommandHandlerContext<AddResidentalTaxCommand> context) {
		// TODO Auto-generated method stub
		
	}

}
