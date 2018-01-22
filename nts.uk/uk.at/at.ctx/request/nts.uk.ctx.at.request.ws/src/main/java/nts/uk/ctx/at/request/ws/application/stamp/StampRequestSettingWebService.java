package nts.uk.ctx.at.request.ws.application.stamp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.setting.company.request.stamp.StampRequestSettingFinder;
import nts.uk.ctx.at.request.app.find.setting.company.request.stamp.dto.StampRequestSettingDto;

/**
 * 
 * @author yennth
 *
 */
@Path("at/request/application/stamprequest")
@Produces("application/json")
public class StampRequestSettingWebService extends WebService{
	@Inject
	private StampRequestSettingFinder finder;
	/**
	 * Doan Duy Hung
	 * @return
	 */
	@POST
	@Path("findByComID")
	public StampRequestSettingDto findByID(){
		return this.finder.findByCompanyID();
	}
}
