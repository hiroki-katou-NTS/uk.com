package nts.uk.ctx.basic.app.command.company;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import lombok.Getter;
import lombok.Setter;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.app.finder.company.CompanyFinder;
import nts.uk.ctx.basic.dom.company.Company;
import nts.uk.ctx.basic.dom.company.CompanyRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * UpdateCompanyCommandHandler
 * @author lanlt
 *
 */
@Stateless
@Transactional
public class UpdateCompanyCommandHandler extends CommandHandler<UpdateCompanyCommand>{
	@Inject
	private CompanyRepository companyReposity;
	@Inject
	private CompanyFinder companyDto;
	@Override
	protected void handle(CommandHandlerContext<UpdateCompanyCommand> context) {
		String companyCode =AppContexts.user().companyCode();
		UpdateCompanyCommand update= context.getCommand();
		Company company= companyReposity.getCompanyDetail(companyCode)
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found company")));
		List<Company> companyOrginal = companyReposity.getHistoryBefore(companyCode);
		companyReposity.update(company);
		
	}

}
