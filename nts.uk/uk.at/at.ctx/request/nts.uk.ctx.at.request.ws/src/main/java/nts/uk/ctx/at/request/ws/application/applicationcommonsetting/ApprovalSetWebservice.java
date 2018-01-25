package nts.uk.ctx.at.request.ws.application.applicationcommonsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.setting.company.applicationcommonsetting.ApprovalSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationcommonsetting.ApprovalSetFinder;
/**
 * 
 * @author yennth
 *
 */
@Path("at/request/application/common/setting")
@Produces("application/json")
public class ApprovalSetWebservice extends WebService{
	@Inject 
	private ApprovalSetFinder appFinder;
	/**
	 * get approval set by companyid
	 * @return
	 * @author yennth
	 */
	@POST
	@Path("appcommon")
	public ApprovalSetDto getAppSet(){
		 return appFinder.findByComId();
	}
}
