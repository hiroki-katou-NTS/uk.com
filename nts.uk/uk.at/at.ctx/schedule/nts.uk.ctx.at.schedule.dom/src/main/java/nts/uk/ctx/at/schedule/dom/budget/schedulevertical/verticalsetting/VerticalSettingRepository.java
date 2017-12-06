package nts.uk.ctx.at.schedule.dom.budget.schedulevertical.verticalsetting;

import java.util.List;
import java.util.Optional;

/**
 * TanLV
 *
 */
public interface VerticalSettingRepository {
	/**
	 * Find all Vertical Calculator Setting
	 * @param companyId
	 * @return
	 */
	List<VerticalCalSet> findAllVerticalCalSet(String companyId);
	
	/**
	 * get all form people
	 * @param companyId
	 * @return
	 * author: Hoang Yen
	 */
	List<FormPeople> findAllFormPeople(String companyId);
	
	/**
	 * get all Form People Func
	 * @param companyId
	 * @return
	 * author: Hoang Yen
	 */
	List<FormPeopleFunc> findAllFormPeopleFunc(String companyId);
	
	/**
	 * Get Vertical Calculator Setting by Code
	 * @param companyId
	 * @param verticalCalCd
	 * @return
	 */
	Optional<VerticalCalSet> getVerticalCalSetByCode(String companyId, String verticalCalCd);
	
	/**
	 * Add Vertical Calculator Setting  
	 * @param verticalCalSet
	 */
	void addVerticalCalSet(VerticalCalSet verticalCalSet);
	
	/**
	 * Update Vertical Calculator Setting  
	 * @param verticalCalSet
	 */
	void updateVerticalCalSet(VerticalCalSet verticalCalSet);
	
	/**
	 * Delete Vertical Calculator Setting  
	 * @param companyId
	 * @param verticalCalCd
	 */
	void deleteVerticalCalSet(String companyId, String verticalCalCd);

	/**
	 * Find Money Func
	 * @param companyId
	 * @author phongtq
	 * @return
	 */
	List<MoneyFunc> findAllMoneyFunc(String companyId);

	/**
	 * Find Formula Time Unit
	 * @param companyId
	 * @author phongtq
	 * @return
	 */
	List<FormulaTimeUnit> findAllFormulaTimeUnit(String companyId);

	/**
	 * Find Formula Nummer
	 * @param companyId
	 * @author phongtq
	 * @return
	 */
	List<FormulaNumerical> findAllFormNumber(String companyId);

	/**
	 * Add Formula Amount
	 * @param formulaAmount
	 * @author phongtq
	 */
	void insertFromAmount(FormulaAmount formulaAmount);

	/**
	 * Find Formula Money
	 * @param companyId
	 * @param verticalCalCd
	 * @param verticalCalItemId
	 * @author phongtq
	 * @return
	 */
	List<FormulaMoney> findAllFormulaMoney(String companyId, String verticalCalCd, String verticalCalItemId);

	/**
	 * Find Time Unit Func 
	 * @param companyId
	 * @param verticalCalCd
	 * @param verticalCalItemId
	 * @author phongtq 
	 * @return
	 */
	List<TimeUnitFunc> findAllTimeUnit(String companyId, String verticalCalCd, String verticalCalItemId);

	/**
	 * Find Formula Amount
	 * @param companyId
	 * @author phongtq
	 * @return
	 */
	List<FormulaAmount> findAllFormulaAmount(String companyId, String verticalCalCd, String verticalCalItemId);

	/**
	 * findAllFormulaPrice
	 * @param companyId
	 * @param verticalCalCd
	 * @param verticalCalItemId
	 * @return
	 */
	List<FormulaUnitprice> findAllFormulaPrice(String companyId, String verticalCalCd, String verticalCalItemId);

	/**
	 * insertFromPrice
	 * @param price
	 */
	void insertFromPrice(FormulaUnitprice price);
}
