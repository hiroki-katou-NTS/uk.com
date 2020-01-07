/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * @author laitv
 *
 */
public interface RegistrationPersonReportRepository {

	List<RegistrationPersonReport> getListBySIds(String sid);

	Optional<RegistrationPersonReport> getDomain(String cid, Integer reportId);

	void add(RegistrationPersonReport domain);

	void update(RegistrationPersonReport domain);

	void remove(String cid, int reportId);

	int getMaxReportId(String sid, String cid);

	public List<RegistrationPersonReport> findByJHN003(String cId, String sId, GeneralDate startDate,
			GeneralDate endDate, Integer reportId, Integer approvalStatus, String inputName, boolean approvalReport);

}
