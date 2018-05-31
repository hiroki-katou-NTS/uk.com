package nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
public interface BusinessTypeOfEmpHisAdaptor {
	/**
	 * find by base date and employeeId
	 * 
	 * @param baseDate
	 * @param sId
	 * @return BusinessTypeOfEmployeeHistory
	 */
	Optional<BusinessTypeOfEmpHis> findByBaseDateAndSid(GeneralDate baseDate, String sId);
}
