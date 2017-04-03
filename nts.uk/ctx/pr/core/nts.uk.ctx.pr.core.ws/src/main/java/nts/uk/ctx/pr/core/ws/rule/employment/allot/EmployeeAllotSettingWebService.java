package nts.uk.ctx.pr.core.ws.rule.employment.allot;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.core.app.find.rule.employment.allot.EmployeeAllotSettingDto;
import nts.uk.ctx.pr.core.app.find.rule.employment.allot.EmployeeAllotSettingFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("pr/core/allot")
@Produces("application/json")
public class EmployeeAllotSettingWebService {
	
	@Inject
	private EmployeeAllotSettingFinder find;
	@POST
	@Path("findallemployeeallotdetail")
	public List<EmployeeAllotSettingDto> GetAllEmployeeAllotSettingDetailList(String histId){
		return this.find.getAllEmployeeAllotDetailSetting(AppContexts.user().companyCode(),histId);
	}
}
