package nts.uk.ctx.hr.notice.ws.report.registration.person;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.hr.notice.app.command.report.regis.person.DelRegisPersonReportHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.RemoveReportCommand;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.SaveDraftRegisPersonReportHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.SaveRegisPersonReportHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.SaveReportInputContainer;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.approve.ApproveReportCommand;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.approve.RegisterApproveHandler;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.PersonalReportDto;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.RegistrationPersonReportFinder;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.RegistrationPersonReportSaveDraftDto;
import nts.uk.shr.com.context.AppContexts;

@Path("hr/notice/report/regis/person")
@Produces("application/json")
public class PersonalReportSaveWebService {

	@Inject
	private RegistrationPersonReportFinder finder;
	
	@Inject
	private DelRegisPersonReportHandler del;
	
	@Inject
	private SaveDraftRegisPersonReportHandler saveDraft;
	
	@Inject
	private SaveRegisPersonReportHandler save;
	
	@Inject
	private RegisterApproveHandler registerApproveHandler;
	
	@POST
	@Path("getAll")
	public List<PersonalReportDto> findAll() {
		String cid = AppContexts.user().companyId();
		List<PersonalReportDto> listReport = this.finder.getListReport(cid);
		return listReport;
	}
	
	@POST
	@Path("getAll-Jhn002")
	public List<PersonalReportDto> findAllJhn002() {
		String cid = AppContexts.user().companyId();
		List<PersonalReportDto> listReport = this.finder.getListReportJhn002(cid);
		return listReport;
	}
	
	@POST
	@Path("getAll-SaveDraft")
	public List<RegistrationPersonReportSaveDraftDto> getListReportSaveDraft() {
		String cid =  AppContexts.user().companyId();
		return finder.getListReportSaveDraft(cid);
	}
	
	@POST
	@Path("remove")
	public void remove(RemoveReportCommand command ) {
		del.handle(command);
	}
	
	@POST
	@Path("saveDraft")
	public void saveDraft(SaveReportInputContainer inputContainer) {
		this.saveDraft.handle(inputContainer);
	}
	
	@POST
	@Path("save")
	public void save(SaveReportInputContainer inputContainer) {
		this.save.handle(inputContainer);
	}
	
	@POST
	@Path("saveScreenC")
	public void save(ApproveReportCommand approveReportCommand) {
		this.registerApproveHandler.handle(approveReportCommand);
	}
}
