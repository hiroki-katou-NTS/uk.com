package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentialTax;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentialTaxRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * UpdateResidentialTaxReportCommandHandler
 * @author lanlt
 *
 */
@Stateless
@Transactional
public class UpdateResidentialTaxReportCommandHandler extends CommandHandler<UpdateResidentialTaxReportCommand> {
	 @Inject
	private ResidentialTaxRepository resiTaxRepository;
	@Override
	protected void handle(CommandHandlerContext<UpdateResidentialTaxReportCommand> context) {
		UpdateResidentialTaxReportCommand update = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		List<ResidentialTax> lstResidential= this.resiTaxRepository.getAllResidentialTax(companyCode, update.getResiTaxReportCode().toString());
		if(lstResidential.isEmpty()){
			return;
		}else{
			for(int i = 0; i < lstResidential.size(); i++){
				ResidentialTax  residentalTax = ResidentialTax.createFromJavaType(
						 companyCode, update.getCompanyAccountNo(), update.getCompanySpecifiedNo(),
							update.getCordinatePostOffice(), update.getCordinatePostalCode(), update.getMemo(), 
							update.getPrefectureCode(), update.getRegisteredName(), update.getResiTaxAutonomy(), 
							update.getResiTaxCode(), update.getResiTaxReportCode());;
			this.resiTaxRepository.update(residentalTax);
			}
		}
	}

}
