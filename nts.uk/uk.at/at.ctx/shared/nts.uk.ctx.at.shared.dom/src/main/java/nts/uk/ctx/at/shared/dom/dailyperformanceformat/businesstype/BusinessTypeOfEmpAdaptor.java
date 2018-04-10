package nts.uk.ctx.at.shared.dom.dailyperformanceformat.businesstype;

import java.util.Optional;

/**
 * 
 * @author sonnh1
 *
 */
public interface BusinessTypeOfEmpAdaptor {
	/**
	 * 
	 * @param employeeId
	 * @param histId
	 * @return
	 */
	Optional<BusinessTypeOfEmp> getBySidAndHistId(String employeeId, String histId);
}
