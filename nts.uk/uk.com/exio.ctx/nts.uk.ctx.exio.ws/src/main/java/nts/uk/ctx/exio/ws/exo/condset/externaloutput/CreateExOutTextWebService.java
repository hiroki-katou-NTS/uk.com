package nts.uk.ctx.exio.ws.exo.condset.externaloutput;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.app.command.JavaTypeResult;
import nts.arc.layer.ws.WebService;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.exio.app.command.exo.createexouttext.CreateExOutTextCommand;
import nts.uk.ctx.exio.app.command.exo.createexouttext.CreateExOutTextCommandHandler;
import nts.uk.ctx.exio.app.find.exo.exoutsummarysetting.ExOutSummarySettingDto;
import nts.uk.ctx.exio.app.find.exo.exoutsummarysetting.ExOutSummarySettingFinder;

@Path("exio/exo/condset")
@Produces("application/json")
public class CreateExOutTextWebService extends WebService {
	@Inject
	private CreateExOutTextCommandHandler createExOutTextCommandHandler;
	
	@Inject
	private ExOutSummarySettingFinder exOutSummarySettingFinder;

	@POST
	@Path("createExOutText")
	public JavaTypeResult<String> createExOutText(CreateExOutTextCommand command) {
		String processingId = IdentifierUtil.randomUniqueId();
		command.setProcessingId(processingId);
		this.createExOutTextCommandHandler.handle(command);

		return new JavaTypeResult<String>(processingId);
	}
	
	@POST
	@Path("getExOutSummarySetting/{conditionSetCd}")
	public ExOutSummarySettingDto getExOutSummarySetting(@PathParam("conditionSetCd") String conditionSetCd){
		return exOutSummarySettingFinder.getExOutSummarySetting(conditionSetCd);
	}
}
