package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RegistrationPersonReportFinder {

	@Inject
	private RegistrationPersonReportRepository repo;

	public List<RegistrationPersonReportDto> findPersonReport(PersonReportQuery query) {

		String cId = AppContexts.user().companyId();

		String sId = AppContexts.user().employeeId();

		return this.repo
				.findByJHN003(cId, sId, query.getStartDate(), query.getEndDate(), query.getReportId(),
						query.getApprovalStatus(), query.getInputName(), query.isApprovalReport())
				.stream().map(x -> RegistrationPersonReportDto.fromDomain(x)).collect(Collectors.toList());
	}
}
