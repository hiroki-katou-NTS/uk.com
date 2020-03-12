package nts.uk.ctx.hr.notice.ws.report;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.hr.notice.app.command.report.DeleteReportClassificationHandler;
import nts.uk.ctx.hr.notice.app.command.report.NewLayoutReportCommand;
import nts.uk.ctx.hr.notice.app.command.report.SaveLayoutReportHandler;
import nts.uk.ctx.hr.notice.app.find.report.PersonalReportClassificationDto;
import nts.uk.ctx.hr.notice.app.find.report.PersonalReportClassificationFinder;
import nts.uk.ctx.hr.notice.dom.report.PersonalReportClassificationRepository;
import nts.uk.ctx.hr.notice.dom.report.valueImported.ctg.PerInfoCtgDataEnumImport;
import nts.uk.shr.com.context.AppContexts;

@Path("hr/notice/report")
@Produces("application/json")
public class PersonalReportClassificationWebService  extends WebService {
	@Inject
	private PersonalReportClassificationFinder reportClsFinder;
	
	@Inject
	private DeleteReportClassificationHandler delete;
	
	@Inject
	private SaveLayoutReportHandler saveLayoutReportHandler;
	
	@Inject
	private PersonalReportClassificationRepository repoPsReport;
	
	@POST
	@Path("findAll/{abolition}")
	public List<PersonalReportClassificationDto> findAll(@PathParam("abolition") boolean abolition) {
		return this.reportClsFinder.getAllReportCls(abolition);
	}

	@POST
	@Path("findOne/{reportClsId}")
	public PersonalReportClassificationDto getLayoutById(@PathParam("reportClsId") int reportClsId) {
		PersonalReportClassificationDto dto = this.reportClsFinder.getDetailReportCls(reportClsId);
		return dto;
	}
	
	@POST
	@Path("delete/{reportClsId}")
	public void delete(@PathParam("reportClsId") String reportClsId) {
		this.delete.handle(reportClsId);
	}
	
	@POST
	@Path("save")
	public void save(NewLayoutReportCommand cmd) {
		this.saveLayoutReportHandler.handle(cmd);
	}
	
	@POST
	@Path("find")
	public PerInfoCtgDataEnumImport getAllPerInfoCtgHumanByCompany() {
		return this.reportClsFinder.getCtg();
	}
	
	//利用している届出種類を取得
	@POST
	@Path("findByAbol")
	public List<PsReportEx> findByAbol() {
		return this.repoPsReport.getAllByCid(AppContexts.user().companyId(), false)
				.stream().map(c -> new PsReportEx(c.getPReportClsId(), c.getPReportCode().v(), c.getPReportName().v(),
						c.getPReportNameYomi() == null? null: c.getPReportNameYomi().v(),
						c.getDisplayOrder(), c.isAbolition() ? 1 : 0, c.getRemark() == null? null: c.getRemark().v(),
						c.isNoRankOrder()))
				.collect(Collectors.toList());
	}
}
