/**
 * 4:21:15 PM Jun 12, 2017
 */
package nts.uk.ctx.at.schedule.dom.event;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author hungnm
 *
 */
public interface CompanyEventRepository {
	
	Optional<CompanyEvent> findByPK(String companyId, BigDecimal date);
	
	List<CompanyEvent> getCompanyEventsByListDate(String companyId, List<BigDecimal> lstDate);
	
	void addEvent(CompanyEvent event);
	
	void updateEvent(CompanyEvent event);
	
	void removeEvent(CompanyEvent event);
}
