package nts.uk.ctx.bs.employee.ws.category;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.find.person.category.EmpPerCtgInfoDto;
import nts.uk.ctx.bs.employee.app.find.person.category.EmpPerInfoCategoryFinder;

@Path("bs/employee/category/tabchildren")
@Produces(MediaType.APPLICATION_JSON)
public class EmpCtgTabChildrenWebService extends WebService{

	@Inject
	private EmpPerInfoCategoryFinder empPerInfoCategoryFinder;
	
	@POST
	@Path("find/getCtgAndItemByCtgId/{categoryId}")
	public EmpPerCtgInfoDto getCtgAndItemByCtgId(@PathParam("categoryId") String ctgId){
		return this.empPerInfoCategoryFinder.getCtgAndItemByCtgId(ctgId);
	}
	
	@POST
	@Path("find/getCtgAndChildrenByParams/{employeeId}/{categoryId}/{parentInfoId}")
	public EmpPerCtgInfoDto getCtgAndChildrenByParams(@PathParam("employeeId") String employeeId, @PathParam("categoryId") String ctgId,
			@PathParam("parentInfoId") String parentInfoId){
		return this.empPerInfoCategoryFinder.getCtgAndItemByParent(employeeId, ctgId, parentInfoId);
	}
}
