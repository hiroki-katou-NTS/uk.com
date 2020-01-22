package nts.uk.ctx.hr.notice.ws.report.registration.person;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.hr.notice.app.command.report.regis.person.DelRegisPersonReportHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.RemoveReportCommand;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.SaveDraftRegisPersonReportHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.SaveReportInputContainer;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.RegistrationPersonReportFinder;
import nts.uk.ctx.hr.notice.dom.report.registration.person.RegistrationPersonReport;
import nts.uk.shr.com.context.AppContexts;

@Path("hr/notice/report/regis/person")
@Produces("application/json")
public class PersonalReportSaveWebService {

	@Inject
	private RegistrationPersonReportFinder finder;
	
	@Inject
	private DelRegisPersonReportHandler del;
	
	@Inject
	private SaveDraftRegisPersonReportHandler save;
	
	@POST
	@Path("getAll")
	public List<RegistrationPersonReport> getListReportSaveDraft() {
		String sid = AppContexts.user().employeeId();
		return finder.getListReportSaveDraft(sid);
	}
	
	@POST
	@Path("remove")
	public void remove(RemoveReportCommand command ) {
		del.handle(command);
	}
	
	@POST
	@Path("saveDraft")
	public void saveDraft(SaveReportInputContainer inputContainer) {
		this.save.handle(inputContainer);
	}
	
	@POST
	@Path("save")
	public void save(SaveReportInputContainer inputContainer) {
		this.save.handle(inputContainer);
	}
	
}
