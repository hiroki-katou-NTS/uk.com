package nts.uk.ctx.pr.proto.ws.personalwage;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.proto.app.find.layout.LayoutDto;
import nts.uk.ctx.pr.proto.app.find.layout.category.LayoutMasterCategoryDto;
import nts.uk.ctx.pr.proto.app.find.personalinfo.wage.PersonalWageNameDto;
import nts.uk.ctx.pr.proto.app.find.personalinfo.wage.PersonalWageNameFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("pr/proto/personalwage")
@Produces("application/json")
public class PersonalWageWebService extends WebService {

	@Inject
	private PersonalWageNameFinder find;

//	@POST
//	@Path("findPersonalWageName")
//	public List<PersonalWageNameDto> getPersonalWageNames(){
//		return this.find.getPersonalWageNames(AppContexts.user().companyCode());		
//	}
//	

	@POST
	@Path("findPersonalWageName/full/{companyCode}/{categoryAtr}")
	public List<PersonalWageNameDto> getPersonalWageName(@PathParam("companyCode") String companyCode, @PathParam("categoryAtr") int categoryAtr){
		return this.find.getPersonalWageName(companyCode, categoryAtr);
	}
}
