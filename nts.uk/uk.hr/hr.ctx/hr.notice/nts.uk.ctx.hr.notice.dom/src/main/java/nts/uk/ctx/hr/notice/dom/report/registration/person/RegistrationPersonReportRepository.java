/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDateTime;

/**
 * @author laitv
 *
 */
public interface RegistrationPersonReportRepository {

	List<RegistrationPersonReport> getListByCid(String sid);
	
	List<RegistrationPersonReport> getListReportSaveDraft(String sid);
	
	Optional<RegistrationPersonReport> getDomain(String cid, Integer reportLayoutID);
	
	Optional<RegistrationPersonReport> getDomainByReportId(String cid, Integer reportID);
	
	void add(RegistrationPersonReport domain);

	void update(RegistrationPersonReport domain);

	void remove(String cid, Integer reportId);
	
	void updateAfterSendBack(String cid, Integer reportId, String sendBackSid, String comment);
	
	void updateMissingDocName(String cid, int reportID, String missingDocName);

	int getMaxReportId(String sid, String cid);

	public List<RegistrationPersonReport> findByJHN003(String cId, String sId, GeneralDateTime startDate,
			GeneralDateTime endDate, Integer reportId, Integer approvalStatus, String inputName,
			boolean approvalReport);

}
