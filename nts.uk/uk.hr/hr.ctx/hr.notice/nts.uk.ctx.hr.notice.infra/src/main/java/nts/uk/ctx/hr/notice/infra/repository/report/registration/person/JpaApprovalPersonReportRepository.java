package nts.uk.ctx.hr.notice.infra.repository.report.registration.person;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.ApprovalPersonReportRepository;

/**
 * @author laitv
 *
 */
@Stateless
public class JpaApprovalPersonReportRepository implements ApprovalPersonReportRepository{

	@Override
	public List<ApprovalPersonReport> getListDomainByReportId(String reprtId) {
		
		
		return null;
	}

}
