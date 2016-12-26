package nts.uk.ctx.core.app.company.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.Company;
import nts.uk.ctx.core.dom.company.CompanyRepository;

/**
 * UpdateCompanyCommandHandler
 */
@Stateless
@Transactional
public class UpdateCompanyCommandHandler extends CommandHandler<UpdateCompanyCommand> {

	/** CompanyRepository */
	@Inject
	private CompanyRepository companyRepository;
	
	/**
	 * Handle command.
	 * 
	 * @param context context
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateCompanyCommand> context) {
		
		Company company = context.getCommand().toDomain();
		company.validate();
		
		this.companyRepository.update(company);
	}


}
