package nts.uk.ctx.pereg.ws.employee.category;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;

@Path("bs/employee/category/tabchildren")
@Produces(MediaType.APPLICATION_JSON)
public class EmpCtgTabChildrenWebService extends WebService{

	/**
	 * lấy category và item list by category id
	 * @param ctgId
	 * @return
	 */
	
	@POST
	@Path("find/getCtgTab/{categoryId}")
	public List<PersonInfoCategory> getCtgTab(@PathParam("categoryId") String ctgId){
		return null;
	}
	
	/**
	 * lấy category và data list
	 * @param employeeId
	 * @param ctgId
	 * @param parentInfoId
	 * @return
	 */
	@POST
	@Path("find/getTabDetail/{employeeId}/{categoryId}/{infoId}")
	public List<Object> getTabDetail(@PathParam("employeeId") String employeeId, @PathParam("categoryId") String ctgId,
			@PathParam("infoId") String parentInfoId){
		return null;
	}
		
	/**
	 * lấy item và data list
	 * @param employeeId
	 * @param ctgId
	 * @param parentInfoId
	 * @return
	 */
	@POST
	@Path("find/getTabSubDetail/{employeeId}/{categoryId}/{subDetailId}")
	public List<Object> getTabSubDetail(@PathParam("employeeId") String employeeId, @PathParam("categoryId") String ctgId,
			@PathParam("subDetailId") String parentInfoId){
		return null;
	}
}
