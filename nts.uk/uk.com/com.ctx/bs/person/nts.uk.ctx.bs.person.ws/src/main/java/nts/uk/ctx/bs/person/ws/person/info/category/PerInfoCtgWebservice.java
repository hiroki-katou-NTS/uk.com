package nts.uk.ctx.bs.person.ws.person.info.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.person.info.category.PerInfoCategoryDto;
import find.person.info.category.PerInfoCategoryFinder;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/person/info/category")
@Produces("application/json")
public class PerInfoCtgWebservice extends WebService {
	@Inject
	private PerInfoCategoryFinder perInfoCtgFinder;

	@POST
	@Path("findAll")
	public List<PerInfoCategoryDto> getAllPerInfoCtg() {
		return perInfoCtgFinder.getAllPerInfoCtg();
	}
	
	@POST
	@Path("findby/{Id}")
	public PerInfoCategoryDto getPerInfoCtg(@PathParam("Id") String Id) {
		return perInfoCtgFinder.getPerInfoCtg(Id);
	}
}
