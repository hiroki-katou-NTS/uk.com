package nts.uk.ctx.pr.core.app.command.rule.law.tax.commutelimit;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.law.tax.commutelimit.CommuNoTaxLimitCode;
import nts.uk.ctx.pr.core.dom.rule.law.tax.commutelimit.CommuNoTaxLimitName;
import nts.uk.ctx.pr.core.dom.rule.law.tax.commutelimit.CommuNoTaxLimitValue;
import nts.uk.ctx.pr.core.dom.rule.law.tax.commutelimit.CommuteNoTaxLimit;
import nts.uk.ctx.pr.core.dom.rule.law.tax.commutelimit.CommuteNoTaxLimitRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class InsertCommuteNoTaxLimitCommandHandler extends CommandHandler<InsertCommuteNoTaxLimitCommand> {

	@Inject
	private CommuteNoTaxLimitRepository repository;

	@Override
	protected void handle(CommandHandlerContext<InsertCommuteNoTaxLimitCommand> context) {
		// get context
		String companyCode = AppContexts.user().companyCode();
		InsertCommuteNoTaxLimitCommand ic = context.getCommand();
		CommuteNoTaxLimit commuteNoTaxLimit = new CommuteNoTaxLimit(
				companyCode,
				new CommuNoTaxLimitCode(ic.getCommuNoTaxLimitCode()), 
				new CommuNoTaxLimitName(ic.getCommuNoTaxLimitName()), 
				new CommuNoTaxLimitValue(ic.getCommuNoTaxLimitValue()));
		
		this.repository.add(commuteNoTaxLimit);
		
	}

}
