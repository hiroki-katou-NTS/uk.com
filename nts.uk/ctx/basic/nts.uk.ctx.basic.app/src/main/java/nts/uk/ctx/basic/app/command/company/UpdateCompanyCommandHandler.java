package nts.uk.ctx.basic.app.command.company;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.basic.dom.company.Company;
import nts.uk.ctx.basic.dom.company.CompanyRepository;

/**
 * UpdateCompanyCommandHandler
 * 
 * @author lanlt
 *
 */
@Stateless
@Transactional
public class UpdateCompanyCommandHandler extends CommandHandler<UpdateCompanyCommand> {
	@Inject
	private CompanyRepository companyReposity;

	@Override
	protected void handle(CommandHandlerContext<UpdateCompanyCommand> context) {
		UpdateCompanyCommand update = context.getCommand();
		// error 26
		Company company = companyReposity.getCompanyDetail(update.getCompanyCode())
				.orElseThrow(() -> new BusinessException(new RawErrorMessage("更新対象のデータが存在しません。")));
		if (company.getCompanyCode().toString().equals(update.getCompanyCode())) {
			company = Company.createFromJavaType(update.getCompanyCode(), update.getCompanyName(),
					update.getCompanyNameGlobal(), update.getCompanyNameAbb(), update.getCompanyNameKana(),
					update.getCorporateMyNumber(), update.getFaxNo().toString(), update.getPostal().toString(), update.getPresidentName(),
					update.getPresidentJobTitle(), update.getTelephoneNo().toString(), update.getDepWorkPlaceSet(),
					update.getDisplayAttribute(), update.getAddress1(), update.getAddress2(), update.getAddressKana1(),
					update.getAddressKana2(), update.getTermBeginMon(), update.getUse_Gr_Set(), update.getUse_Kt_Set(),
					update.getUse_Qy_Set(), update.getUse_Jj_Set(), update.getUse_Ac_Set(), update.getUse_Gw_Set(),
					update.getUse_Hc_Set(), update.getUse_Lc_Set(), update.getUse_Bi_Set(), update.getUse_Rs01_Set(),
					update.getUse_Rs02_Set(), update.getUse_Rs03_Set(), update.getUse_Rs04_Set(),
					update.getUse_Rs05_Set(), update.getUse_Rs06_Set(), update.getUse_Rs07_Set(),
					update.getUse_Rs08_Set(), update.getUse_Rs09_Set(), update.getUse_Rs10_Set());
			companyReposity.update(company);
		}

	}

}
