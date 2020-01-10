/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.util.List;

import nts.arc.time.GeneralDateTime;

/**
 * @author laitv
 *
 */
public interface ApprovalPersonReportRepository {

	List<ApprovalPersonReport> getListDomainByReportId(String cid, String reprtId);
	
	boolean checkExit(int reportID, int phaseNum, int aprNum, String cid);
	
	void add(ApprovalPersonReport domain);
	
	void addAll(List<ApprovalPersonReport> domains);
	
	void update(ApprovalPersonReport domain);
	
	void delete(int reportID, int phaseNum, int aprNum, String cid);
	
	void deleteByReportId(int reportID, String cid);

	List<ApprovalPersonReport> getByJHN003(String cId, String sId, GeneralDateTime startDate, GeneralDateTime endDate,
			Integer reportId, Integer approvalStatus, String inputName);
}
