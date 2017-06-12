/**
 * 4:21:15 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.event;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * @author hungnm
 *
 */
public interface CompanyEventRepository {
	
	List<CompanyEvent> getListCompanyEvent(String companyId, List<GeneralDate> lstDate);
	
	void addEvent(CompanyEvent event);
	
	void updateEvent(CompanyEvent event);
	
	void removeEvent(CompanyEvent event);
}
