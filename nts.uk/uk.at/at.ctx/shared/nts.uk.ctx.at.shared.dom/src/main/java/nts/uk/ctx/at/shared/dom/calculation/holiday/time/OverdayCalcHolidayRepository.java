package nts.uk.ctx.at.shared.dom.calculation.holiday.time;

import java.util.List;

public interface OverdayCalcHolidayRepository {

	List<OverdayCalc> findByCompanyId(String companyId);

	void add(OverdayCalc overdayCalc);

	void update(OverdayCalc overdayCalc);

}
