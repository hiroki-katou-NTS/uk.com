package nts.uk.ctx.at.request.ws.application.applicationlist;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppListExtractConditionDto;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ApplicationListDto;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ApplicationListFinder;
import nts.uk.ctx.at.request.app.find.application.applicationlist.BfReqSetDto;
import nts.uk.ctx.at.request.app.find.application.applicationlist.BfReqSetFinder;
import nts.uk.ctx.at.request.dom.application.applicationlist.extractcondition.ApplicationDisplayAtr;

/**
 * 
 * @author hoatt
 *
 */
@Path("at/request/application/applist")
@Produces("application/json")
public class ApplicationListWebservice extends WebService{

	@Inject
	private ApplicationListFinder appListFinder;
	
	@Inject
	private BfReqSetFinder bfreqFinder;
	
	@POST
	/**
	 * get all list application
	 * @param param
	 * @return
	 */
	@Path("getapplist")
	public ApplicationListDto getApplicationList(AppListExtractConditionDto param) {
		return this.appListFinder.getAppList(param);
	}
	
	/**
	 * get before After Restriction
	 * @return
	 * @author yennth
	 */
	@POST
	@Path("getappDisp")
	public List<BfReqSetDto> getBeforAfer() {
		return this.bfreqFinder.findByCom();
	}
	
	/**
	 * Enum 申請表示区分.
	 *
	 * @return the list
	 */
	@POST
	@Path("get/appdisplayatr")
	public List<EnumConstant> getListAppDisplayAtr() {
		return EnumAdaptor.convertToValueNameList(ApplicationDisplayAtr.class);
	}
}
