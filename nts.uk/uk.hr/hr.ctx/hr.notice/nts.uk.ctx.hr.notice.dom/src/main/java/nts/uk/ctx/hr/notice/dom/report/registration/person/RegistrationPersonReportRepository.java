/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.util.List;
import java.util.Optional;

/**
 * @author laitv
 *
 */
public interface RegistrationPersonReportRepository {

	List<RegistrationPersonReport> getListBySIds(String sid);
	
	Optional<RegistrationPersonReport> getDomain(String cid, int reportId);
	
	void add(RegistrationPersonReport domain);

	void update(RegistrationPersonReport domain);

	void remove(String cid, int reportId);

	int getMaxReportId(String sid, String cid);

}
