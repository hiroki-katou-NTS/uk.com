package nts.uk.ctx.pr.formula.dom.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.pr.formula.dom.formula.FormulaMaster;

/**
 * @author hungnm
 *
 */
public interface FormulaMasterRepository {
	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param historyId
	 * @return a formula master
	 */
	Optional<FormulaMaster> find(String companyCode, String formulaCode, String historyId);
	
	/**
	 * @param companyCode
	 * @param formulaCode
	 * @param baseYearMonth
	 * @return a formula master
	 */
	Optional<FormulaMaster> findByBaseYearMonth(String companyCode, String formulaCode, int baseYM);
	
	/**
	 * @param companyCode
	 * @return list of formulas in a company
	 */
	List<FormulaMaster> findByCompanyCode(String companyCode);
}
