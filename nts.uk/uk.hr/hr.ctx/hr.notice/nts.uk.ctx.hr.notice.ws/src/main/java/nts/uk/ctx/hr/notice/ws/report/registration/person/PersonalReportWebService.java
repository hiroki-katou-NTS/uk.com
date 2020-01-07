package nts.uk.ctx.hr.notice.ws.report.registration.person;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.PersonalReportDto;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.RegistrationPersonReportFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("hr/notice/person/report")
@Produces("application/json")
public class PersonalReportWebService  extends WebService {
	
	
	@Inject
	private RegistrationPersonReportFinder reportFinder;
	
	@POST
	@Path("findAll")
	public List<PersonalReportDto> findAll() {
		String sid = AppContexts.user().employeeId();
		List<PersonalReportDto> listReport = this.reportFinder.getListReport(sid);
		return listReport;
	}

	
}
