package nts.uk.screen.at.ws.ktgwidget;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ktgwidget.KTG001QueryProcessor;
import nts.uk.screen.at.app.ktgwidget.find.KTG001Dto;

@Path("screen/at/ktg001")
@Produces("application/json")
public class KTG001WebService extends WebService {

	@Inject
	private KTG001QueryProcessor queryProcessor; 
	
	@POST
	@Path("checkDisplay")
	public KTG001Dto checkDisplay(KTG001Param param){
		return this.queryProcessor.checkDataDayPerConfirm(param.getYm(), param.getClosureId());
	}
	

}
