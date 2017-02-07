package nts.uk.ctx.basic.app.command.company;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.company.CompanyRepository;

/**
 * Delete Company Command Handler
 * @author lanlt
 *
 */
@Stateless
@Transactional
public class DeleteCompanyCommandHandler extends CommandHandler<DeleteCompanyCommand>{
	@Inject
	private CompanyRepository companyReposity;
	
	@Override
	protected void handle(CommandHandlerContext<DeleteCompanyCommand> context) {
		DeleteCompanyCommand deleteCompany = context.getCommand();
		String companyCode = deleteCompany.getCompanyCode();
		companyReposity.delete(companyCode);
	}

}
