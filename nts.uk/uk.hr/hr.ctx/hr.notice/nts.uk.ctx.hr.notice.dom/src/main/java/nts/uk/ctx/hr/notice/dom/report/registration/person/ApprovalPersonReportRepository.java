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

	List<ApprovalPersonReport> getListDomainByReportId(String cid, int reprtId);
	
	List<ApprovalPersonReport> getListDomainByReportId(int reprtId);
	
	List<ApprovalPersonReport> getListDomainByReportIdAndSid(String cid, int reprtId, String approverId);
	
	boolean checkExit(int reportID, int phaseNum, int aprNum, String cid, String aprSid);
	
	void add(ApprovalPersonReport domain);
	
	void addAll(List<ApprovalPersonReport> domains);
	
	void updateAll(List<ApprovalPersonReport> domains);
	
	void update(ApprovalPersonReport domain);
	
	void delete(int reportID, int phaseNum, int aprNum, String cid, String aprSid);
	
	void deleteByReportId(int reportID, String cid);
	
	void updateSendBack(List<ApprovalPersonReport> domains, int reprtId, String sid);

	List<ApprovalPersonReport> getByJHN003(String cId, String sId, GeneralDateTime startDate, GeneralDateTime endDate,
			Integer reportId, Integer approvalStatus, String inputName);

	//社員IDから承認が必要な届出の有無を取得する
	boolean checkExit(String cid, String aprSid);
}
