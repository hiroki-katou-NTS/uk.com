package nts.uk.ctx.bs.employee.ws.regpersoninfo.init.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.init.category.InitCtgDto;
import nts.uk.ctx.bs.employee.dom.regpersoninfo.init.category.PerInfoInitValueSettingCtgFinder;

@Path("regpersoninfo/init/category")
@Produces(MediaType.APPLICATION_JSON)
public class PerInfoInitValueSettingCtgWebService extends WebService {

	@Inject
	private PerInfoInitValueSettingCtgFinder cgtFinder;

	@POST
	@Path("findAllBySetId/{settingId}")
	public List<InitCtgDto> getAllCategoryBySetId(@PathParam("settingId") String settingId) {
		return this.cgtFinder.getAllCategoryBySetId(settingId);
	}
}
