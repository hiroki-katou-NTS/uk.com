package nts.uk.screen.at.ws.ktgwidget;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetImport;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.screen.at.app.ktgwidget.find.DatePeriodDto;
import nts.uk.screen.at.app.ktgwidget.find.OptionalWidgetKtgFinder;

@Path("screen/at/OptionalWidget")
@Produces("application/json")
public class OptionalWidgetWebService extends WebService {

	@Inject
	private OptionalWidgetKtgFinder OptionalWidgetFinder; 
	
	@POST
	@Path("getEmploymentCode")
	public String getEmploymentCode(){
		return null;
	}
	
	@POST
	@Path("getCurrentMonth")
	public DatePeriodDto getCurrentMonth(){
		return OptionalWidgetFinder.getCurrentMonth();
	}
	
	@POST
	@Path("getOptionalWidget")
	public OptionalWidgetImport findOptionalWidgetByCode(String topPagePartCode){
		return OptionalWidgetFinder.findOptionalWidgetByCode(topPagePartCode);
	}
}
