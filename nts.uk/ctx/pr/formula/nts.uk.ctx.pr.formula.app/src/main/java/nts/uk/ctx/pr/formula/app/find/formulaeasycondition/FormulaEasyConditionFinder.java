package nts.uk.ctx.pr.formula.app.find.formulaeasycondition;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.repository.FormulaEasyConditionRepository;

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
	public Optional<FormulaEasyConditionDto> find(String companyCode, String formulaCode, String historyId,
			String referenceMasterCode) {
		return this.repository.find(companyCode, formulaCode, historyId, referenceMasterCode)
				.map(f -> FormulaEasyConditionDto.fromDomain(f));
	}
}
