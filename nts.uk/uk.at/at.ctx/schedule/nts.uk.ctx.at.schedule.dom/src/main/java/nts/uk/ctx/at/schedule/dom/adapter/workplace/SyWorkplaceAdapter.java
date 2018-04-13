package nts.uk.ctx.at.schedule.dom.adapter.workplace;

import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author sonnh1
 *
 */
public interface SyWorkplaceAdapter {
	Optional<SWkpHistImported> findBySid(String employeeId, GeneralDate baseDate);
}
