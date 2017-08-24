package nts.uk.ctx.bs.person.ws.person.info.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import find.person.info.category.PerInfoCtgFinder;
import find.person.info.category.PerInfoCtgFullDto;
import nts.arc.layer.ws.WebService;
import nts.uk.shr.com.context.AppContexts;

@Path("ctx/bs/person/info/ctg")
@Produces("application/json")
public class PerInfoCategoryWebservice extends WebService {
    @Inject
	private PerInfoCtgFinder finder;
	@POST
	@Path("findAll")
	public List<PerInfoCtgFullDto> getAllPerInfoCtg() {
		String companyId = AppContexts.user().companyId();
		return this.finder.getAllPerInfoCtg(companyId);
	}
	
	@POST
	@Path("findAllByRoot")
	public List<PerInfoCtgFullDto> getAllPerInfoCtgRoot() {
		return this.finder.getAllPerInfoCtg("000000000000-0000");
	}
}
