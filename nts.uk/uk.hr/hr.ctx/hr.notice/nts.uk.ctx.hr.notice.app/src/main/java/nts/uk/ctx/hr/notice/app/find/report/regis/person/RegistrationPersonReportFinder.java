package nts.uk.ctx.hr.notice.app.find.report.regis.person;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReportRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class RegistrationPersonReportFinder {

	@Inject
	private RegistrationPersonReportRepository repo;

	public List<RegistrationPersonReportDto> findPersonReport(PersonReportQuery query) {

		String cId = AppContexts.user().companyId();

		String sId = AppContexts.user().employeeId();
		
		List<RegistrationPersonReport> regisList = this.repo.findByJHN003(cId, sId, query.getAppDate().getStartDate(),
				query.getAppDate().getEndDate(), query.getReportId(), query.getApprovalStatus(), query.getInputName(),
				query.isApprovalReport());

		if ((regisList.size() > 99 && query.isApprovalReport())
				|| (regisList.size() > 999 && !query.isApprovalReport())) {
			throw new BusinessException("Msgj_46");
		}

		return regisList
				.stream().map(x -> RegistrationPersonReportDto.fromDomain(x, query.isApprovalReport()))
				.sorted(Comparator.comparing(RegistrationPersonReportDto::getInputDate)).collect(Collectors.toList());
	}
}
