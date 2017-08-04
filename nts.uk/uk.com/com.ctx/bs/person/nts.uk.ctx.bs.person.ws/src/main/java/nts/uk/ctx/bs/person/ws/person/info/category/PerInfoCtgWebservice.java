package nts.uk.ctx.bs.person.ws.person.info.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import find.person.info.category.PerInfoCategoryFinder;
import find.person.info.category.PerInfoCtgDataEnumDto;
import find.person.info.category.PerInfoCtgFullDto;
import find.person.info.category.PerInfoCtgWithItemsNameDto;
import nts.arc.layer.ws.WebService;

@Path("ctx/bs/person/person/info/category")
@Produces("application/json")
public class PerInfoCtgWebservice extends WebService {
	@Inject
	private PerInfoCategoryFinder perInfoCtgFinder;

	@POST
	@Path("findAll")
	public List<PerInfoCtgFullDto> getAllPerInfoCtg() {
		return perInfoCtgFinder.getAllPerInfoCtg();
	}
	
	@POST
	@Path("findby/{Id}")
	public PerInfoCtgFullDto getPerInfoCtg(@PathParam("Id") String id) {
		return perInfoCtgFinder.getPerInfoCtg(id);
	}
	
	@POST
	@Path("find/withItemsName/{Id}")
	public PerInfoCtgWithItemsNameDto getPerInfoCtgWithItemsName(@PathParam("Id") String id) {
		return perInfoCtgFinder.getPerInfoCtgWithItemsName(id);
	}
	
	@POST
	@Path("findby/company")
	public PerInfoCtgDataEnumDto getAllPerInfoCtgByCompany() {
		return perInfoCtgFinder.getAllPerInfoCtgByCompany();
	}
}
