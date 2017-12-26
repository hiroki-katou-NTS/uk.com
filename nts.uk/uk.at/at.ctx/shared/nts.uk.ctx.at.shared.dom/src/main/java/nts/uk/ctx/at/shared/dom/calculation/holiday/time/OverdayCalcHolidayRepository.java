package nts.uk.ctx.at.shared.dom.calculation.holiday.time;

import java.util.List;
import java.util.Optional;

public interface OverdayCalcHolidayRepository {

	/**
	 * Find by CompanyId
	 */
	List<OverdayCalc> findByCompanyId(String companyId);

	/**
	 * Add Overday Calc
	 * @param overdayCalc
	 */
	void add(OverdayCalc overdayCalc);

	/**
	 * Update Overday Calc
	 * @param overdayCalc
	 */
	void update(OverdayCalc overdayCalc);

	Optional<OverdayCalc> findByCId(String companyId);

}
