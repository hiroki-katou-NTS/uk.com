package nts.uk.ctx.pr.formula.app.find.formula;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.app.find.formulaeasydetail.FormulaEasyDetailDto;
import nts.uk.ctx.pr.formula.dom.primitive.EasyFormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyDetailRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;


@Stateless
public class FormulaEasyFinder {
	
	@Inject
	FormulaEasyDetailRepository formulaEasyDetailRepository;
	
	public FormulaEasyFinderDto init(String formulaCode, String historyId, String easyFormulaCode){
		LoginUserContext login = AppContexts.user();
		String companyCode = login.companyCode();
		
		Optional<FormulaEasyDetailDto> formulaEasyDetailDto = formulaEasyDetailRepository.findByPriKey(companyCode, new FormulaCode(formulaCode), historyId,
				new EasyFormulaCode(easyFormulaCode)).map(f -> FormulaEasyDetailDto.fromDomain(f));
		
		FormulaEasyFinderDto formulaEasyFinderDto = new FormulaEasyFinderDto();
		
		
		
		return formulaEasyFinderDto;
	}

}
