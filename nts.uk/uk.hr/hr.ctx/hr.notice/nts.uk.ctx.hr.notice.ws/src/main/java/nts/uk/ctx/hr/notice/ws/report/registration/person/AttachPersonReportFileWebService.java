/**
 * 
 */
package nts.uk.ctx.hr.notice.ws.report.registration.person;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.hr.notice.app.command.report.regis.person.AddAttachPersonReportFileHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.AddDocumentReportCommand;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.DeleteAttachPersonReportFileHandler;
import nts.uk.ctx.hr.notice.app.command.report.regis.person.DeleteDocumentReportCommand;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.AttachPersonReportFileFinder;
import nts.uk.ctx.hr.notice.app.find.report.regis.person.AttachReportFileParamFinder;
import nts.uk.ctx.hr.notice.dom.report.registration.person.DocumentSampleDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author laitv
 */
@Path("hr/notice/report/regis/person/document")
@Produces("application/json")
public class AttachPersonReportFileWebService {
	
	@Inject
	private AttachPersonReportFileFinder finder;
	
	@Inject
	private AddAttachPersonReportFileHandler add;
	
	@Inject
	private DeleteAttachPersonReportFileHandler delete;

	@POST
	@Path("findAll")
	public List<DocumentSampleDto> findAll(AttachReportFileParamFinder param) {
		return finder.findAll(Integer.valueOf(param.layoutReportId), param.reportId == null ? null : Integer.valueOf(param.reportId));
	}
	
	@POST
	@Path("add")
	public String addDoc(AddDocumentReportCommand command) {
		return this.add.handle(command);
	}
	
	@POST
	@Path("delete")
	public void deleteDoc(DeleteDocumentReportCommand command) {
		command.setCid(AppContexts.user().companyId());
		this.delete.handle(command);
	}
}
