package nts.uk.ctx.pr.formula.app.find.formulaeasycondition;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.primitive.FormulaCode;
import nts.uk.ctx.pr.formula.dom.primitive.ReferenceMasterCode;
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
	public Optional<FormulaEasyConditionDto> find(String formulaCode, String historyId, String referenceMasterCode) {
		LoginUserContext login = AppContexts.user();

		return this.repository.find(login.companyCode(), new FormulaCode(formulaCode), historyId,
				new ReferenceMasterCode(referenceMasterCode)).map(f -> FormulaEasyConditionDto.fromDomain(f));
	}
}
