package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.util.List;

public interface AttachPersonReportFileRepository {
	
	List<AttachmentPersonReportFile> getListDomainByReportId(String cid, String reprtId);
	
	void add(AttachmentPersonReportFile domain);
	
	void addAll(List<AttachmentPersonReportFile> domains);
	
	void delete(int reportID, String cid);
	
	
}
