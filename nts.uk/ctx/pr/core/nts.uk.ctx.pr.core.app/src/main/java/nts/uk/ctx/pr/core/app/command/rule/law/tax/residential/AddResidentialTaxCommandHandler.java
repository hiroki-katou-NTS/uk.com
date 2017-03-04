package nts.uk.ctx.pr.core.app.command.rule.law.tax.residential;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentialTax;
import nts.uk.ctx.pr.core.dom.rule.law.tax.residential.ResidentialTaxRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * AddResidentalTaxCommandHandler
 * @author lanlt
 *
 */
@Stateless
@Transactional
public class AddResidentialTaxCommandHandler extends CommandHandler<AddResidentialTaxCommand>{
    @Inject
	private ResidentialTaxRepository repo;
	
	@Override
	protected void handle(CommandHandlerContext<AddResidentialTaxCommand> context) {
		String companyCode = AppContexts.user().companyCode();
		AddResidentialTaxCommand add = context.getCommand();
//		add.setCompanyAccountNo("1111");
//		add.setCompanySpecifiedNo("111");
//		add.setCordinatePostalCode("111");
//		add.setCordinatePostOffice("â");
//		add.setMemo("aaaaa");
//		add.setPrefectureCode("067880");
//		add.setRegisteredName("bbbb");
//		add.setResiTaxAutonomy("aaaaa");
//		add.setResiTaxCode("1111");
//		add.setResiTaxReportCode("11111");
//		add.setCompanyCode("0000");

		List<ResidentialTax> listResiTax = this.repo.getAllResidentialTax(companyCode);
		//ERROR5 resiTaxCode duplicate 
		boolean error = true;
		for(int i = 0; i < listResiTax.size(); i++){
			System.out.println(listResiTax.get(i).getResiTaxCode().toString());
			System.out.println(add.getResiTaxCode().toString());
			if(listResiTax.get(i).getResiTaxCode().toString().equals(add.getResiTaxCode().toString())){ 
				error = false;
				System.out.println(error);
				break;
			}
		}
		System.out.println(error);
		if(error){
			
			this.repo.add(ResidentialTax.createFromJavaType(companyCode, add.getCompanyAccountNo(), add.getCompanySpecifiedNo(),
					add.getCordinatePostOffice(), add.getCordinatePostalCode(), add.getMemo(), 
					add.getPrefectureCode(), add.getRegisteredName(), add.getResiTaxAutonomy(), 
					add.getResiTaxCode(), add.getResiTaxReportCode()));
			
		}else{
			
			throw new BusinessException("入力した 住民税納付先コード  は既に存在しています。\r\n 住民税納付先コードを確認してください。");
		}

	}


}
