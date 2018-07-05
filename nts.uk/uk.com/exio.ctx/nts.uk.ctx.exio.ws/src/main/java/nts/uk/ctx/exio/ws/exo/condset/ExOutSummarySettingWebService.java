package nts.uk.ctx.exio.ws.exo.condset;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.find.exo.exoutsummarysetting.ExOutSummarySettingDto;
import nts.uk.ctx.exio.app.find.exo.exoutsummarysetting.ExOutSummarySettingFinder;

@Path("exio/exo/condset")
@Produces("application/json")
public class ExOutSummarySettingWebService extends WebService {
	@Inject
	private ExOutSummarySettingFinder exOutSummarySettingFinder;
	
	@POST
	@Path("getExOutSummarySetting/{conditionSetCd}")
	public ExOutSummarySettingDto getExOutSummarySetting(@PathParam("conditionSetCd") String conditionSetCd){
		return exOutSummarySettingFinder.getExOutSummarySetting(conditionSetCd);
	}
}
