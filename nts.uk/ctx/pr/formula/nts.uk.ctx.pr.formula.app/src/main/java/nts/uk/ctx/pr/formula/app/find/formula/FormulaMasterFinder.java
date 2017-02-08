/**
 * 
 */
package nts.uk.ctx.pr.formula.app.find.formula;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.formula.dom.repository.FormulaMasterRepository;

/**
 * @author hungnm
 *
 */
@Stateless
public class FormulaMasterFinder {
	/**
	 * FormulaMasterRepository
	 */
	@Inject
	private FormulaMasterRepository repository;
	
	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @return FormulaMaster
	 */
	public Optional<FormulaDto> find(String companyCode, String formulaCode, String historyId) {
		return this.repository.find(companyCode, formulaCode, historyId).map(f -> FormulaDto.fromDomain(f));
	}
}
