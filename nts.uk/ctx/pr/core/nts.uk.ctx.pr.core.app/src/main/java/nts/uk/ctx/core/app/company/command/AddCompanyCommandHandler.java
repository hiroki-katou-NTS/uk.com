package nts.uk.ctx.core.app.company.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.core.dom.company.Company;
import nts.uk.ctx.core.dom.company.CompanyRepository;

/**
 * AddCompanyCommandHandler
 */
@Stateless
@Transactional
public class AddCompanyCommandHandler extends CommandHandler<AddCompanyCommand> {

	/** CompanyRepository */
	@Inject
	private CompanyRepository companyRepository;
	
	/**
	 * Handle command.
	 * 
	 * @param context context
	 */
	@Override
	protected void handle(CommandHandlerContext<AddCompanyCommand> context) {

		Company company = context.getCommand().toDomain();
		this.companyRepository.add(company);
	}

}
