/**
 * 
 */
package nts.uk.screen.at.ws.ktgwidget;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.mobi.DisplayNotifiDto;
import nts.uk.screen.at.app.mobi.ToppageOptionalWidgetInfoDto;
import nts.uk.screen.at.app.mobi.ToppageOvertimeData;
import nts.uk.screen.at.app.mobi.ToppageOvertimeHoursDto;
import nts.uk.screen.at.app.mobi.ToppageStartupDto;
import nts.uk.screen.at.app.mobi.ToppageStartupProcessMobFinder;

@Path("screen/at/mobile/ccgs08")
@Produces("application/json")
public class ToppageStartupWebService extends WebService {

	@Inject
	private ToppageStartupProcessMobFinder toppageStartupProcessMobFinder;
	
	@POST
	@Path("/overtime/{yearmonth}")
	public ToppageOvertimeHoursDto getOvertimeData(@PathParam("yearmonth") int yearmonth){
		return toppageStartupProcessMobFinder.getDisplayOvertime(yearmonth);
	}
	
	@POST
	@Path("/overtime/toppage")
	public ToppageOvertimeData getOvertimeToppage() {
		return toppageStartupProcessMobFinder.getOvertimeToppage();
	}
	
	@POST
	@Path("/ktg029")
	public ToppageOptionalWidgetInfoDto getKtg029Data(){
		return toppageStartupProcessMobFinder.getKTG029();
	}
	
	@POST
	@Path("/displaynotif")
	public DisplayNotifiDto getDisplaynotifData(){
		return toppageStartupProcessMobFinder.getDisplayNotif();
	}
	
	@POST
	@Path("/visibleConfig")
	public ToppageStartupDto getVisibleSetting(){
		return toppageStartupProcessMobFinder.startupProcessMob();
	}
}
