package nts.uk.ctx.at.schedule.dom.adapter.classification;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
public interface SyClassificationAdapter {
	/**
	 * 
	 * @param companyId
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	Optional<SClsHistImported> findSClsHistBySid(String companyId, String employeeId, GeneralDate baseDate);
}
