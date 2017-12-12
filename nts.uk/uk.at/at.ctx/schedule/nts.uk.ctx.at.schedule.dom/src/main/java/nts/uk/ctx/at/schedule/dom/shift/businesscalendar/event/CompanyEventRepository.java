/**
 * 4:21:15 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.shift.businesscalendar.event;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
public interface CompanyEventRepository {
	
	Optional<CompanyEvent> findByPK(String companyId, GeneralDate date);
	
	List<CompanyEvent> getCompanyEventsByListDate(String companyId, List<GeneralDate> lstDate);
	
	void addEvent(CompanyEvent event);
	
	void updateEvent(CompanyEvent event);
	
	void removeEvent(CompanyEvent event);
}
