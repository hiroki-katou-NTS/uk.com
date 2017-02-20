package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentalTax;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentalTaxRepository;

public class UpdateResidentalTaxCommandHandler extends CommandHandler<UpdateResidentalTaxCommand> {

	@Inject
	private ResidentalTaxRepository resiTaxRepository;
	@Override
	protected void handle(CommandHandlerContext<UpdateResidentalTaxCommand> context) {
		UpdateResidentalTaxCommand update = context.getCommand();
		//this.resiTaxRepository.update(ResidentalTax.createFromJavaType(companyCode, companyAccountNo, companySpecifiedNo, cordinatePostOffice, cordinatePostalCode, memo, prefectureCode, registeredName, resiTaxAutonomy, resiTaxCode, resiTaxReportCode));
		
	}

}
