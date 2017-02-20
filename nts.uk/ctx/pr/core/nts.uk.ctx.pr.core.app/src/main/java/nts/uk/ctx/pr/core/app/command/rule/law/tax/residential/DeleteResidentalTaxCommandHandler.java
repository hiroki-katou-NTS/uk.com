package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentalTaxRepository;

public class DeleteResidentalTaxCommandHandler extends CommandHandler<DeleteResidentalTaxCommand>{
	@Inject
	private ResidentalTaxRepository resiTaxRepository;
	@Override
	protected void handle(CommandHandlerContext<DeleteResidentalTaxCommand> context) {
		DeleteResidentalTaxCommand delete = context.getCommand();
		this.resiTaxRepository.delele(delete.getCompanyCode(), delete.getResiTaxCode());
		
	}

}
