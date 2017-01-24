package nts.uk.ctx.basic.app.command.company;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.company.Company;
import nts.uk.ctx.basic.dom.company.CompanyRepository;
import nts.uk.shr.com.context.AppContexts;

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
