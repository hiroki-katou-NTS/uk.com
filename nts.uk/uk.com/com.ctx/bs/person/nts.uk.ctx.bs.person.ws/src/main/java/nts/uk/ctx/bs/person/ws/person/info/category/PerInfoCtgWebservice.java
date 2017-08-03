package nts.uk.ctx.bs.person.ws.person.info.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.person.info.category.PerInfoCategoryFinder;
import find.person.info.category.PerInfoCtgNewLayoutDto;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/person/info/category")
@Produces("application/json")
public class PerInfoCtgWebservice extends WebService {
	@Inject
	private PerInfoCategoryFinder perInfoCtgFinder;

	@POST
	@Path("findAll/newLayout")
	public List<PerInfoCtgNewLayoutDto> getAllPerInfoCtgNewLayout() {
		return perInfoCtgFinder.getAllPerInfoCtgNewLayout();
	}
	
	@POST
	@Path("find/newLayout/{Id}")
	public PerInfoCtgNewLayoutDto getPerInfoCtgNewLayout(@PathParam("Id") String Id) {
		return perInfoCtgFinder.getPerInfoCtgNewLayout(Id);
	}
}
