package nts.uk.ctx.pereg.ws.layout;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import find.layout.NewLayoutDto;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.find.layout.GetLayoutByCeateTypeDto;
import nts.uk.ctx.bs.employee.app.find.layout.dto.EmpMaintLayoutDto;
import nts.uk.ctx.bs.person.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.app.find.layout.RegisterLayoutFinder;
import nts.uk.ctx.pereg.app.find.processor.PeregProcessor;
import nts.uk.shr.pereg.app.find.PeregQuery;

/**
 * @author sonnlb
 *
 */
@Path("ctx/pereg/layout")
@Produces(MediaType.APPLICATION_JSON)
public class LayoutWebService extends WebService {

	@Inject
	private RegisterLayoutFinder layoutFinder;
	
	@Inject
	private PeregProcessor layoutProcessor;

	@Path("getByCreateType")
	@POST
	public NewLayoutDto getByCreateType(GetLayoutByCeateTypeDto command) {
		return this.layoutFinder.getByCreateType(command);
	}
	
	/**
	 * @author xuan vinh
	 * @param query
	 * @return
	 */
	
	@Path("find/getTabDetail")
	@POST
	public EmpMaintLayoutDto getTabDetail(PeregQuery query){
		return this.layoutProcessor.getCategoryChild(query);
	}
	
	/**
	 * 
	 */
	
	@Path("find/getCtgTab/{categoryId}")
	@POST
	public List<PersonInfoCategory> getTabDetail(@PathParam("resourceId")String ctgId){
		return this.layoutProcessor.getCtgTab(ctgId);
	}

}
