package nts.uk.ctx.hr.develop.ws.test;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import test.mandatoryretirement.RetirePlanCourceFinderTest;
import test.mandatoryretirement.dto.RetirePlanCourceDto;
import test.mandatoryretirement.param.RetirePlanCourceParamDto;

@Path("retirePlanCource")
@Produces(MediaType.APPLICATION_JSON)
public class RetirePlanCourceWS {
	
	@Inject
	private RetirePlanCourceFinderTest finder;
	
	@POST
	@Path("/getList")
	public List<RetirePlanCourceDto> getRetirePlanCourse(RetirePlanCourceParamDto param){
		return finder.getRetirePlanCourse(param.cId);
	}
	
	@POST
	@Path("/add")
	public void addRetirePlanCourse(RetirePlanCourceParamDto param){
		finder.addRetirePlanCourse(param.cId, param.retirePlanCourseList);
	}
	
	@POST
	@Path("/update")
	public void updateRetirePlanCourse(RetirePlanCourceParamDto param){
		finder.updateRetirePlanCourse(param.cId, param.retirePlanCourseList);
	}
	
	@POST
	@Path("/getAllList")
	public List<RetirePlanCourceDto> getAllRetirePlanCource(RetirePlanCourceParamDto param){
		return finder.getAllRetirePlanCource(param.cId);
	}
	
	@POST
	@Path("/getByListId")
	public List<RetirePlanCourceDto> getRetireTermByRetirePlanCourceIdList(RetirePlanCourceParamDto param){
		return finder.getRetireTermByRetirePlanCourceIdList(param.cId, param.retirePlanCourseId);
	}
	
	@POST
	@Path("/getByDate")
	public List<RetirePlanCourceDto> getEnableRetirePlanCourceByBaseDate(RetirePlanCourceParamDto param){
		return finder.getEnableRetirePlanCourceByBaseDate(param.cId, param.baseDate);
	}
	
}
