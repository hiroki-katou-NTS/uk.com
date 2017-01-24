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
		Company company= Company.createFromJavaType(
				addCompany.getCompanyCode(), addCompany.getCompanyName(),
				addCompany.getCompanyNameAbb(), addCompany.getCompanyNameKana(), 
				addCompany.getCorporateMyNumber(), addCompany.getFaxNo(),
				addCompany.getPostal(), addCompany.getPresidentJobTitle(),
				addCompany.getTelephoneNo(), addCompany.getDepWorkPlaceSet(),
				addCompany.getDisplayAttribute(), addCompany.getAddress1(),
				addCompany.getAddress2(), addCompany.getAddressKana1(),
				addCompany.getAddressKana2(), addCompany.getTermBeginMon(),
				addCompany.getUse_Gw_Set(), addCompany.getUse_Kt_Set(),
				addCompany.getUse_Qy_Set(), addCompany.getUse_Jj_Set(),
				addCompany.getUse_Ac_Set(), addCompany.getUse_Gw_Set(),
				addCompany.getUse_Hc_Set(), addCompany.getUse_Lc_Set(),
				addCompany.getUse_Bi_Set(), addCompany.getUse_Rs01_Set(),
				addCompany.getUse_Rs02_Set(), addCompany.getUse_Rs03_Set(),
				addCompany.getUse_Rs04_Set(), addCompany.getUse_Rs05_Set(),
				addCompany.getUse_Rs06_Set(), addCompany.getUse_Rs07_Set(),
				addCompany.getUse_Rs08_Set(), addCompany.getUse_Rs09_Set(),
				addCompany.getUse_Rs10_Set());
		companyRepository.add(company);
		
	}
	

}
