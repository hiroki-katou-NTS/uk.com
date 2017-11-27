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
	 * insert a Form People item
	 * @param formPeople
	 * author: Hoang Yen
	 */
	void insertFormPeople(FormPeople formPeople);
	
	/**
	 * insert list form people func 
	 * @param formPeopleFunc
	 * author: Hoang Yen
	 */
	void insertFormPeopleFunc(List<FormPeopleFunc> formPeopleFunc);
	
	/**
	 * Update Vertical Calculator Setting  
	 * @param verticalCalSet
	 */
	void updateVerticalCalSet(VerticalCalSet verticalCalSet);
	
	/**
	 * update a Form People item
	 * @param formPeople
	 * author: Hoang Yen
	 */
	void updateFormPeople(FormPeople formPeople);
	
	/**
	 * update list Form People Func
	 * @param lstFormPeopleFunc
	 * author: Hoang Yen
	 */
	void updateFormPeopleFunc(List<FormPeopleFunc> lstFormPeopleFunc);
	
	/**
	 * Delete Vertical Calculator Setting  
	 * @param companyId
	 * @param verticalCalCd
	 */
	void deleteVerticalCalSet(String companyId, String verticalCalCd);
	
	/**
	 * delete a Form People item
	 * @param formPeople
	 * author: Hoang Yen
	 */
	void deleteFormPeople(FormPeople formPeople);
	
	/**
	 * delete list Form People Func
	 * @param lstFormPeopleFunc
	 * author: Hoang Yen
	 */
	void deleteFormPeopleFunc(List<FormPeopleFunc> lstFormPeopleFunc);

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

	List<FormulaUnitprice> findAllFormulaPrice(String companyId, String verticalCalCd, String verticalCalItemId);

	void insertFromPrice(FormulaUnitprice price);
}
