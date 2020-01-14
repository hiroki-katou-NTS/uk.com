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
	
	List<RegistrationPersonReport> getListReportSaveDraft(String sid);
	
	Optional<RegistrationPersonReport> getDomain(String cid, Integer reportLayoutID);
	
	Optional<RegistrationPersonReport> getDomainByReportId(String cid, Integer reportID);
	
	void add(RegistrationPersonReport domain);

	void update(RegistrationPersonReport domain);

	void remove(String cid, Integer reportId);

	int getMaxReportId(String sid, String cid);

}
