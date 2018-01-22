package nts.uk.ctx.at.request.ws.application.stamp;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.setting.stamp.StampRequestSettingFinder;
import nts.uk.ctx.at.request.app.find.setting.stamp.dto.StampRequestSettingDto;
import nts.uk.shr.com.context.AppContexts;

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
	
	@POST
	@Path("findByComID")
	public StampRequestSettingDto findByID(){
		String companyId = AppContexts.user().companyId();
		return this.finder.findByCompanyID(companyId);
	}
}
