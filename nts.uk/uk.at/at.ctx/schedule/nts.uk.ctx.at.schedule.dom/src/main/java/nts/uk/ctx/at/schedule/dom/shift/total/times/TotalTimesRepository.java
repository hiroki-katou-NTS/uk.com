package nts.uk.ctx.at.schedule.dom.shift.total.times;

import java.util.List;

/**
 * The Interface TotalTimesRepository.
 */
public interface TotalTimesRepository {

	List<TotalTimes> getAllTotalTimes(String companyId);
	
}
