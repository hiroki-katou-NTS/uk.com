package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;

@Stateless
@Transactional
public class UpdateResidentalTaxCommandHandler extends CommandHandler<UpdateResidentalTaxCommand> {

	// @Inject
	// private ResidentalTaxRepository resiTaxRepository;
	@Override
	protected void handle(CommandHandlerContext<UpdateResidentalTaxCommand> context) {
		// UpdateResidentalTaxCommand update = context.getCommand();
		// this.resiTaxRepository(ResidentalTax.createFromJavaType(update.getCompanyCode(),
		// update.getCompanyAccountNo(), update.getCompanySpecifiedNo(),
		// update.getCordinatePostOffice(),
		// update.getCordinatePostalCode(),update.getMemo(),
		// update.getPrefectureCode(),
		// update.getRegisteredName(), resiTaxAutonomy, resiTaxCode,
		// resiTaxReportCode));
		// this.resiTaxRepository.update(ResidentalTax.createFromJavaType(companyCode,
		// companyAccountNo, companySpecifiedNo, cordinatePostOffice,
		// cordinatePostalCode, memo, prefectureCode, registeredName,
		// resiTaxAutonomy, resiTaxCode, resiTaxReportCode));

	}

}
