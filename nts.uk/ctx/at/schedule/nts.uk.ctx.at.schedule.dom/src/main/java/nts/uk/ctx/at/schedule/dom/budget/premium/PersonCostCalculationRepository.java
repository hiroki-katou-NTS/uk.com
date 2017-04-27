package nts.uk.ctx.at.schedule.dom.budget.premium;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */

public interface PersonCostCalculationRepository {
	
	/**
	 * insert PersonCostCalculation
	 * @param personCostCalculation PersonCostCalculation
	 */
	public void add(PersonCostCalculation personCostCalculation);
	
	/**
	 * get list PersonCostCalculation by company ID
	 * @param CID company ID
	 * @return list PersonCostCalculation by company ID
	 */
	public List<PersonCostCalculation> findByCompanyID(String CID);
	
	/**
	 * get single PersonCostCalculation by company ID and history ID
	 * @param CID company ID
	 * @param HID history ID
	 * @return single PersonCostCalculation by company ID and history ID
	 */
	public Optional<PersonCostCalculation> find(String CID, String HID);
	
	/**
	 * get single PersonCostCalculation after input date by company ID
	 * @param CID company ID
	 * @param date input date
	 * @return single PersonCostCalculation after input date by company ID
	 */
	public Optional<PersonCostCalculation> findAfterDay(String CID, GeneralDate date);
	
	/**
	 * update PersonCostCalculation 
	 * @param personCostCalculation PersonCostCalculation
	 */
	public void update(PersonCostCalculation personCostCalculation);
	
	/**
	 * delete PersonCostCalculation
	 * @param personCostCalculation PersonCostCalculation
	 */
	public void delete(PersonCostCalculation personCostCalculation);
}
