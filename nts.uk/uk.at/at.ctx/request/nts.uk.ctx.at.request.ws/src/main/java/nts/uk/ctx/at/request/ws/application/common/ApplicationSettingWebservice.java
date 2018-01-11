package nts.uk.ctx.at.request.ws.application.common;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.common.ApplicationSettingFinder;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApplicationSettingDto;

@Path("at/request/application/setting")
@Produces("application/json")
public class ApplicationSettingWebservice extends WebService{
	@Inject
	private ApplicationSettingFinder appSetFind;
	
	/**
	 * get application setting
	 * @param command
	 * @return
	 * @author yennth  
	 */
	@POST
	@Path("appset")
	public ApplicationSettingDto getAppSet(){
		 return appSetFind.finder();
	}
}
