/**
 * 
 */
package nts.uk.ctx.hr.notice.dom.report.registration.person;

import java.util.List;

/**
 * @author laitv
 *
 */
public interface ApprovalPersonReportRepository {

	List<ApprovalPersonReport> getListDomainByReportId(String reprtId);
}
