package nts.uk.ctx.basic.app.command.company;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.company.Company;
import nts.uk.ctx.basic.dom.company.CompanyRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * AddCompanyCommandHandlers
 * @author lanlt
 *
 */
@Stateless
@Transactional
public class AddCompanyCommandHandler extends CommandHandler<AddCompanyCommand>{
	/** CompanyRepository */
	@Inject
	private CompanyRepository companyRepository;
	

	@Override
	protected void handle(CommandHandlerContext<AddCompanyCommand> context) {
		AddCompanyCommand addCompany = context.getCommand();
		String companyCode = AppContexts.user().companyCode();
		Company company = companyRepository.getCompanyDetail(companyCode)
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found company")));
		companyRepository.add(company);
		
	}
	

}
