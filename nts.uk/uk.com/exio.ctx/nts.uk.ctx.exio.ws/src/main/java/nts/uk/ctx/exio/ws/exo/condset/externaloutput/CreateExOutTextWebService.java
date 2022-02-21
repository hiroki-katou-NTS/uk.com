package nts.uk.ctx.exio.ws.exo.condset.externaloutput;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistRepository;
import nts.uk.ctx.exio.app.command.exo.createexouttext.CreateExOutTextCommand;
import nts.uk.ctx.exio.app.command.exo.createexouttext.CreateExOutTextCommandHandler;
import nts.uk.ctx.exio.app.command.exo.createexouttext.SmileCreateExOutTextCommand;
import nts.uk.ctx.exio.app.find.exo.exoutsummarysetting.ExOutSummarySettingDto;
import nts.uk.ctx.exio.app.find.exo.exoutsummarysetting.ExOutSummarySettingFinder;
import nts.uk.ctx.exio.app.find.exo.exoutsummarysetting.SmileGetSettingDto;
import nts.uk.shr.com.context.AppContexts;

@Path("exio/exo/condset")
@Produces("application/json")
public class CreateExOutTextWebService extends WebService {
	@Inject
	private CreateExOutTextCommandHandler createExOutTextCommandHandler;
	
	@Inject
	private ExOutSummarySettingFinder exOutSummarySettingFinder;

	@Inject
	private AffCompanyHistRepository affCompanyHistRepo;
	
	@POST
	@Path("createExOutText")
	public JavaTypeResult<String> createExOutText(CreateExOutTextCommand command) {
		String companyId = AppContexts.user().companyId();
		String processingId = IdentifierUtil.randomUniqueId();
		command.setProcessingId(processingId);
		command.setCompanyId(companyId);
		this.createExOutTextCommandHandler.handle(command);

		return new JavaTypeResult<String>(processingId);
	}
	
	@POST
	@Path("smileCreateExOutText")
	public JavaTypeResult<String> smileCreateExOutText(SmileCreateExOutTextCommand command) {
		String processingId = IdentifierUtil.randomUniqueId();
		GeneralDate startDate = GeneralDate.legacyDate(command.getStartDate());
		GeneralDate endDate = GeneralDate.legacyDate(command.getStartDate());
		String Cid = AppContexts.user().companyId();
		
		List<String> listSid = affCompanyHistRepo.getSidItemByCom(Cid, GeneralDate.today());
		
		CreateExOutTextCommand outTextCmd = new CreateExOutTextCommand(Cid, command.getConditionSetCd(), "", null, 
																	startDate, endDate, GeneralDate.today(), processingId, true, listSid);
		this.createExOutTextCommandHandler.handle(outTextCmd);

		return new JavaTypeResult<String>(processingId);
	}
	
	
	@POST
	@Path("getExOutSummarySetting/{conditionSetCd}")
	public ExOutSummarySettingDto getExOutSummarySetting(@PathParam("conditionSetCd") String conditionSetCd){
		return exOutSummarySettingFinder.getExOutSummarySetting(conditionSetCd);
	}
	
	
	@POST
	@Path("getExOutSummarySetting")
	public SmileGetSettingDto getExOutSetting(String conditionSetCd){
		String cid = AppContexts.user().companyId();
		return exOutSummarySettingFinder.getExOutSetting(cid, conditionSetCd);
	}
}
