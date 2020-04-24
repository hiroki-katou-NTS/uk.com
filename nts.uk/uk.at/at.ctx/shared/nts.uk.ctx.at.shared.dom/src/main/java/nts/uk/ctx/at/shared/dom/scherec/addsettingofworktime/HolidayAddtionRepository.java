/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.util.Map;
import java.util.Optional;

import nts.arc.layer.dom.AggregateRoot;

/**
 * The Interface HolidayAddtionRepository.
 */
public interface HolidayAddtionRepository {
	
	/**
	 * Find by company id.
	 *
	 * @param companyId the company id
	 * @return the map
	 */
	Map<String, AggregateRoot> findByCompanyId(String companyId);
	
	/**
	 * Find by C id.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<HolidayAddtionSet >findByCId(String companyId);
	
	/**
	 * Adds the.
	 *
	 * @param holidayAddtime the holiday addtime
	 * @param regularAdditionSet the regular addition set
	 * @param flexAdditionSet the flex addition set
	 * @param deformedLaborAdditionSet the deformed labor addition set
	 * @param addSetManageWorkHour the add set manage work hour
	 * @param hourlyPaymentAdditionSet the hourly payment addition set
	 */
	void add(HolidayAddtionSet holidayAddtime, WorkRegularAdditionSet regularAdditionSet, 
			WorkFlexAdditionSet flexAdditionSet , WorkDeformedLaborAdditionSet deformedLaborAdditionSet, 
			AddSetManageWorkHour addSetManageWorkHour, HourlyPaymentAdditionSet hourlyPaymentAdditionSet);
	
	/**
	 * Update.
	 *
	 * @param holidayAddtime the holiday addtime
	 * @param regularAdditionSet the regular addition set
	 * @param flexAdditionSet the flex addition set
	 * @param deformedLaborAdditionSet the deformed labor addition set
	 * @param addSetManageWorkHour the add set manage work hour
	 * @param hourlyPaymentAdditionSet the hourly payment addition set
	 */
	void update(HolidayAddtionSet holidayAddtime, WorkRegularAdditionSet regularAdditionSet, 
			WorkFlexAdditionSet flexAdditionSet , WorkDeformedLaborAdditionSet deformedLaborAdditionSet, 
			AddSetManageWorkHour addSetManageWorkHour, HourlyPaymentAdditionSet hourlyPaymentAdditionSet);

}
