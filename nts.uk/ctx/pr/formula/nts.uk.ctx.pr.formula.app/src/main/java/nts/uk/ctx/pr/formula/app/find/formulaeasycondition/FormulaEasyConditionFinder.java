package nts.uk.ctx.pr.formula.app.find.formulaeasycondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyConditionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author hungnm
 *
 */
@Stateless
public class FormulaEasyConditionFinder {

	@Inject
	private FormulaEasyConditionRepository repository;

	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @param referenceMasterCode
	 * @return FormulaEasyConditionDto
	 */
	public List<FormulaEasyConditionDto> find(String formulaCode, String historyId) {
		LoginUserContext login = AppContexts.user();
//
//		return repository.find(login.companyCode(), new FormulaCode(formulaCode), historyId).stream()
//				.map(f -> {return FormulaEasyConditionDto}).collect(Collectors.toList());
		return null;
	}
}
