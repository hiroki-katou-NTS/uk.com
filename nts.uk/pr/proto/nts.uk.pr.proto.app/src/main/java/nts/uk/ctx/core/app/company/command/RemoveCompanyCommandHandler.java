package nts.uk.ctx.core.app.company.command;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.core.dom.company.CompanyRepository;

/**
 * RemoveCompanyCommandHandler
 */
@RequestScoped
@Transactional
public class RemoveCompanyCommandHandler extends CommandHandler<RemoveCompanyCommand> {

	/** CompanyRepository */
	@Inject
	private CompanyRepository companyRepository;
	
	/**
	 * Handle command.
	 * 
	 * @param context context
	 */
	@Override
	protected void handle(CommandHandlerContext<RemoveCompanyCommand> context) {
		
		val companyCode = new CompanyCode(context.getCommand().getCompanyCode());
		this.companyRepository.remove(companyCode);
	}

}
