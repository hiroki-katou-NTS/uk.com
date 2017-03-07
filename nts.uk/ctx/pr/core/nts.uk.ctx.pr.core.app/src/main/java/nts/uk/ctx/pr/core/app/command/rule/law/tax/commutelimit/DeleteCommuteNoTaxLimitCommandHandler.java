package nts.uk.ctx.pr.core.app.command.rule.law.tax.commutelimit;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.law.tax.commutelimit.CommuteNoTaxLimitRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class DeleteCommuteNoTaxLimitCommandHandler extends CommandHandler<DeleteCommuteNoTaxLimitCommand> {

	@Inject
	private CommuteNoTaxLimitRepository repository;

	@Override
	protected void handle(CommandHandlerContext<DeleteCommuteNoTaxLimitCommand> context) {
		String companyCode = AppContexts.user().companyCode();

		this.repository.delele(companyCode, context.getCommand().getCommuNoTaxLimitCode());
	}

}
