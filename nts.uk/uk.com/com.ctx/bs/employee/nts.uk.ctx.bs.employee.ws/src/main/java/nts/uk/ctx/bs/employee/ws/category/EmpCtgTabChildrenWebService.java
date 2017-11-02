package nts.uk.ctx.bs.employee.ws.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.find.person.category.EmpPerCtgInfoDto;
import nts.uk.ctx.bs.employee.app.find.person.category.EmpPerInfoCategoryFinder;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;

@Path("bs/employee/category/tabchildren")
@Produces(MediaType.APPLICATION_JSON)
public class EmpCtgTabChildrenWebService extends WebService{

	@Inject
	private EmpPerInfoCategoryFinder empPerInfoCategoryFinder;
	
	/**
	 * lấy category và item list by category id
	 * @param ctgId
	 * @return
	 */
	
	@POST
	@Path("find/getCtgAndItemByCtgId/{categoryId}")
	public EmpPerCtgInfoDto getCtgAndItemByCtgId(@PathParam("categoryId") String ctgId){
		return this.empPerInfoCategoryFinder.getCtgAndItemByCtgId(ctgId);
	}
	
	/**
	 * lấy category và data list
	 * @param employeeId
	 * @param ctgId
	 * @param parentInfoId
	 * @return
	 */
	@POST
	@Path("find/getCtgAndChildrenByParams/{employeeId}/{categoryId}/{parentInfoId}")
	public EmpPerCtgInfoDto getCtgAndChildrenByParams(@PathParam("employeeId") String employeeId, @PathParam("categoryId") String ctgId,
			@PathParam("parentInfoId") String parentInfoId){
		return this.empPerInfoCategoryFinder.getCtgAndItemByParent(employeeId, ctgId, parentInfoId);
	}
	

	/**
	 * lấy category và danh sách category con của nó
	 * @param ctgId
	 * @return
	 */
	
	@POST
	@Path("find/getCtgAndChildren/{categoryId}")
	public List<PersonInfoCategory> getCtgAndChildren(@PathParam("categoryId") String ctgId){
		return this.empPerInfoCategoryFinder.getCtgAndChildren(ctgId);
	}
	
	/**
	 * lấy item và data list
	 * @param employeeId
	 * @param ctgId
	 * @param parentInfoId
	 * @return
	 */
	@POST
	@Path("find/getItemInCtgAndChildrenList/{employeeId}/{categoryId}/{parentInfoId}")
	public EmpPerCtgInfoDto getItemInCtgAndChildrenList(@PathParam("employeeId") String employeeId, @PathParam("categoryId") String ctgId,
			@PathParam("parentInfoId") String parentInfoId){
		return this.empPerInfoCategoryFinder.getItemInCtgAndChildrenList(employeeId, ctgId, parentInfoId);
	}
}
