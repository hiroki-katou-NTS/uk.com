package nts.uk.ctx.pr.formula.app.find.formulaeasyhead;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyHeaderRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author hungnm
 *
 */
@Stateless
public class FormulaEasyHeadFinder {
	@Inject
	private FormulaEasyHeaderRepository repository;

	Optional<FormulaEasyHeadDto> find( String formulaCode, String historyId) {
		LoginUserContext login = AppContexts.user(); 
		
		return repository.findByPriKey(login.companyCode(), new FormulaCode(formulaCode), historyId)
				.map(f -> FormulaEasyHeadDto.fromDomain(f));
	}
}
