package nts.uk.ctx.basic.app.command.company;

import java.util.List;
import java.util.Optional;

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
//		addCompany.setAddress1("hanoi");
//		addCompany.setAddress2("sai gon");
//		addCompany.setAddressKana1("hanoi1");
//		addCompany.setAddressKana2("sai gon 2");
//		addCompany.setCompanyCode("0004");
//		addCompany.setCompanyName("Toshiba");
//		addCompany.setCompanyNameAbb("Toshiba");
//		addCompany.setCompanyNameGlobal("Toshiba");
//		addCompany.setCompanyNameKana("Toshiba");
//		addCompany.setCorporateMyNumber("11111111111");
//		addCompany.setDepWorkPlaceSet(1);
//		addCompany.setDisplayAttribute(1);
//		addCompany.setFaxNo("1111111111111");
//		addCompany.setPostal("1111111");
//		addCompany.setPresidentJobTitle("Director");
//		addCompany.setPresidentName("Lee Min Hoo");
//		addCompany.setTelephoneNo("0966092265");
//		addCompany.setTermBeginMon(1);
//		addCompany.setUse_Ac_Set(1);
//		addCompany.setUse_Bi_Set(1);
//		addCompany.setUse_Gr_Set(1);
//		addCompany.setUse_Gw_Set(1);
//		addCompany.setUse_Hc_Set(1);
//		addCompany.setUse_Jj_Set(1);
//		addCompany.setUse_Kt_Set(1);
//		addCompany.setUse_Kt_Set(1);
//		addCompany.setUse_Lc_Set(1);
//		addCompany.setUse_Qy_Set(1);
//		addCompany.setUse_Rs01_Set(1);
//		addCompany.setUse_Rs02_Set(1);
//		addCompany.setUse_Rs03_Set(1);
//		addCompany.setUse_Rs04_Set(1);
//		addCompany.setUse_Rs05_Set(1);
//		addCompany.setUse_Rs06_Set(1);
//		addCompany.setUse_Rs07_Set(1);
//		addCompany.setUse_Rs08_Set(1);
//		addCompany.setUse_Rs09_Set(1);
//		addCompany.setUse_Rs10_Set(1);
//		//addCompany=new AddCompanyCommand()
//		System.out.println( "11111111111111111111");
		System.out.println(addCompany.getCompanyCode());
		Company company= addCompany.toDomain(); 
		Optional<Company> lst;	
		lst = this.companyRepository.getCompanyDetail(addCompany.getCompanyCode());
		//notification error 005 check in account -> add account (sign up)
		if(lst.isPresent()){
			throw new BusinessException(new RawErrorMessage("入力した company code は既に存在しています。\r\n。 "
					+ "を確認してください again。"));
		}else{
			this.companyRepository.add(company);
			List<Company> ls = this.companyRepository.getAllCompanys();
			for(int i= 0; i< ls.size(); i++){
			 System.out.println(ls.get(i).getCompanyCode());
			}
		}
}
	

}
