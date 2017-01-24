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
	@Override
	protected void handle(CommandHandlerContext<UpdateCompanyCommand> context) {
		UpdateCompanyCommand update= context.getCommand();
		Company company= companyReposity.getCompanyDetail(update.getCompanyCode())
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("Not found company")));
		if(!(company.getAddress().getAddress1().equals(update.getAddress1())) ||
				!(company.getAddress().getAddress2().equals(update.getAddress2())) ||
				!(company.getAddress().getAddressKana1().equals(update.getAddressKana1())) ||
				!(company.getAddress().getAddressKana2().equals(update.getAddressKana2())) ||
				!(company.getCompanyName().equals(update.getCompanyName()))||
				!(company.getCompanyNameAbb().equals(update.getCompanyNameAbb()))||
				!(company.getCompanyNameKana().equals(update.getCompanyNameKana()))||
				!(company.getCompanyUseSet().getUse_Ac_Set().equals(update.getUse_Ac_Set()))||
				!(company.getCompanyUseSet().getUse_Bi_Set().equals(update.getUse_Bi_Set()))||
				!(company.getCompanyUseSet().getUse_Gr_Set().equals(update.getUse_Gr_Set()))||
				!(company.getCompanyUseSet().getUse_Gw_Set().equals(update.getUse_Gw_Set()))||
				!(company.getCompanyUseSet().getUse_Hc_Set().equals(update.getUse_Hc_Set()))||
				!(company.getCompanyUseSet().getUse_Jj_Set().equals(update.getUse_Jj_Set()))||
				!(company.getCompanyUseSet().getUse_Kt_Set().equals(update.getUse_Kt_Set()))||
				!(company.getCompanyUseSet().getUse_Lc_Set().equals(update.getUse_Lc_Set()))||
				!(company.getCompanyUseSet().getUse_Qy_Set().equals(update.getUse_Qy_Set()))||
				!(company.getCompanyUseSet().getUse_Rs01_Set().equals(update.getUse_Rs01_Set()))||
				!(company.getCompanyUseSet().getUse_Rs02_Set().equals(update.getUse_Rs02_Set()))||
				!(company.getCompanyUseSet().getUse_Rs03_Set().equals(update.getUse_Rs03_Set()))||
				!(company.getCompanyUseSet().getUse_Rs04_Set().equals(update.getUse_Rs04_Set()))||
				!(company.getCompanyUseSet().getUse_Rs05_Set().equals(update.getUse_Rs05_Set()))||
				!(company.getCompanyUseSet().getUse_Rs06_Set().equals(update.getUse_Rs06_Set()))||
				!(company.getCompanyUseSet().getUse_Rs07_Set().equals(update.getUse_Rs07_Set()))||
				!(company.getCompanyUseSet().getUse_Rs08_Set().equals(update.getUse_Rs08_Set()))||
				!(company.getCompanyUseSet().getUse_Rs09_Set().equals(update.getUse_Rs09_Set()))||
				!(company.getCompanyUseSet().getUse_Rs10_Set().equals(update.getUse_Rs10_Set()))||
				(company.getDisplayAttribute().value != update.getDisplayAttribute())||
				!(company.getDepWorkPlaceSet().value != update.getDepWorkPlaceSet())||
				!(company.getCorporateMyNumber().equals(update.getCorporateMyNumber()))||
				!(company.getFaxNo().equals(update.getFaxNo()))||
				!(company.getPostal().equals(update.getPostal()))||
				!(company.getPresidentJobTitle().equals(update.getPresidentJobTitle()))||
				!(company.getTelephoneNo().equals(update.getTelephoneNo()))||
				(company.getTermBeginMon().v() != update.getTermBeginMon())){
			company = Company.createFromJavaType(
					update.getCompanyCode(), update.getCompanyName(),
					update.getCompanyNameAbb(), update.getCompanyNameKana(), 
					update.getCorporateMyNumber(), update.getFaxNo(),
					update.getPostal(), update.getPresidentJobTitle(),
					update.getTelephoneNo(), update.getDepWorkPlaceSet(),
					update.getDisplayAttribute(), update.getAddress1(),
					update.getAddress2(), update.getAddressKana1(),
					update.getAddressKana2(), update.getTermBeginMon(),
					update.getUse_Gw_Set(), update.getUse_Kt_Set(),
					update.getUse_Qy_Set(), update.getUse_Jj_Set(),
					update.getUse_Ac_Set(), update.getUse_Gw_Set(),
					update.getUse_Hc_Set(), update.getUse_Lc_Set(),
					update.getUse_Bi_Set(), update.getUse_Rs01_Set(),
					update.getUse_Rs02_Set(), update.getUse_Rs03_Set(),
					update.getUse_Rs04_Set(), update.getUse_Rs05_Set(),
					update.getUse_Rs06_Set(), update.getUse_Rs07_Set(),
					update.getUse_Rs08_Set(), update.getUse_Rs09_Set(),
					update.getUse_Rs10_Set());

			companyReposity.update(company);
		}
		
	}

}
