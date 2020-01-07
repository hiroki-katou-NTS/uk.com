package nts.uk.ctx.hr.notice.ws.report.registration.person;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.hr.notice.app.command.report.regis.person.DelRegisPersonReportHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.SaveDraftRegisPersonReportHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.SaveReportInputContainer;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.RegistrationPersonReportFinder;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.shr.com.context.AppContexts;

@Path("hr/notice/report/regis/person/save/draft")
@Produces("application/json")
public class PersonalReportSaveDraftWebService {

	@Inject
	private RegistrationPersonReportFinder finder;
	
	@Inject
	private DelRegisPersonReportHandler del;
	
	@Inject
	private SaveDraftRegisPersonReportHandler saveDarft;
	
	@POST
	@Path("getAll")
	public List<RegistrationPersonReport> getListReportSaveDraft() {
		String sid = AppContexts.user().employeeId();
		return finder.getListReportSaveDraft(sid);
	}
	
	
	@POST
	@Path("remove/{reportId}")
	public void remove(@PathParam("reportId") String reportId) {
		del.handle(reportId);
	}
	
	@POST
	@Path("save")
	public void saveDraft(SaveReportInputContainer inputContainer) {
		this.saveDarft.handle(inputContainer);
	}
	
}
