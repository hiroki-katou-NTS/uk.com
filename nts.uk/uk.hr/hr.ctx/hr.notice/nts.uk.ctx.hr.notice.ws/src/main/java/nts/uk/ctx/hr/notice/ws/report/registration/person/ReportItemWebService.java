package nts.uk.ctx.hr.notice.ws.report.registration.person;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.hr.notice.app.find.report.ReportItemFinder;
import nts.uk.ctx.hr.notice.app.find.report.ReportLayoutDto;
import nts.uk.ctx.hr.notice.app.find.report.ReportParams;

@Path("hr/notice/report/item")
@Produces("application/json")
public class ReportItemWebService extends WebService{
	
	@Inject
	private ReportItemFinder reportItemFinder;
	
	@POST
	@Path("findOne")
	public ReportLayoutDto getLayoutById(ReportParams params) {
		ReportLayoutDto dto = this.reportItemFinder.getDetailReportCls(params);
		return dto;
	}
}
