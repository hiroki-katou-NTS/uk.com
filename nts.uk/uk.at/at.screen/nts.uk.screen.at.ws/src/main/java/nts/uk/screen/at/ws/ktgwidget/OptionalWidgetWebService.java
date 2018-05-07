package nts.uk.screen.at.ws.ktgwidget;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.ktgwidget.find.OptionalWidgetKtgFinder;
import nts.uk.screen.at.app.ktgwidget.find.dto.DatePeriodParam;
import nts.uk.screen.at.app.ktgwidget.find.dto.OptionalWidgetDisplay;
import nts.uk.screen.at.app.ktgwidget.find.dto.OptionalWidgetInfoDto;

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
	@Path("getOptionalWidgetDisplay")
	public OptionalWidgetDisplay getOptionalWidgetDisplay(String topPagePartCode){
		return OptionalWidgetFinder.getOptionalWidgetDisplay(topPagePartCode);
	}
	
	@POST
	@Path("getOptionalWidgetInfo")
	public OptionalWidgetInfoDto getOptionalWidgetInfo(DatePeriodParam datePeriodParam){
		return OptionalWidgetFinder.getDataRecord(datePeriodParam.code, datePeriodParam.strMonth, datePeriodParam.endMonth);
	}
}
