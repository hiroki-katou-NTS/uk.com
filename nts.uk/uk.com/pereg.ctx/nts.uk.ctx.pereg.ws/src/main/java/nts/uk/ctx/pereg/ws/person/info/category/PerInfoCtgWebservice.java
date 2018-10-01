package nts.uk.ctx.pereg.ws.person.info.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.person.info.category.AddPerInfoCtgCommand;
import nts.uk.ctx.pereg.app.command.person.info.category.AddPerInfoCtgCommandHandler;
import nts.uk.ctx.pereg.app.command.person.info.category.UpdatePerInfoCtgCommand;
import nts.uk.ctx.pereg.app.command.person.info.category.UpdatePerInfoCtgCommandHandler;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCategoryFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgDataEnumDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgWithItemsNameDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgWithParentMapDto;

@Path("ctx/pereg/person/info/category")
@Produces("application/json")
public class PerInfoCtgWebservice extends WebService {
	@Inject
	private PerInfoCategoryFinder perInfoCtgFinder;

	@Inject
	private AddPerInfoCtgCommandHandler addPerInfoCtgCommand;

	@Inject
	private UpdatePerInfoCtgCommandHandler updatePerInfoCtgCommand;
	
	@POST
	@Path("findAll")
	public List<PerInfoCtgFullDto> getAllPerInfoCtg() {
		return perInfoCtgFinder.getAllPerInfoCtg();
	}

	@POST
	@Path("find/companyby/{Id}")
	public PerInfoCtgFullDto getPerInfoCtg(@PathParam("Id") String id) {
		return perInfoCtgFinder.getPerInfoCtg(id);
	}

	@POST
	@Path("find/withItemsName/{Id}")
	public PerInfoCtgWithItemsNameDto getPerInfoCtgWithItemsName(@PathParam("Id") String id) {
		PerInfoCtgWithItemsNameDto ctg = perInfoCtgFinder.getPerInfoCtgWithItemsName(id);
		return ctg;
	}
	
	//vinhpx: start
	
	
	@POST
	@Path("find/getPerInfoWithParent")
	public List<PerInfoCtgWithParentMapDto> getPerInfoWithParent(String parentCd){
		return perInfoCtgFinder.getPerInfoCtgWithParent(parentCd);
	}
	
	//vinhpx: end

	@POST
	@Path("findby/company")
	public PerInfoCtgDataEnumDto getAllPerInfoCtgByCompany() {
		return perInfoCtgFinder.getAllPerInfoCtgByCompany();
	}
	
	@POST
	@Path("findby/companyv2/{isCps007}")
	public PerInfoCtgDataEnumDto getAllPerInfoCtgByCompanyv2(@PathParam("isCps007") boolean isCps007) {
		return perInfoCtgFinder.getAllPerInfoCtgByCompanyv3(isCps007);
	}
	
	
	@POST
	@Path("findAll/company/root")
	public PerInfoCtgDataEnumDto getAllPerInfoCtgByCompanyRoot() {
		return perInfoCtgFinder.getAllPerInfoCtgByCompanyRoot();
	}
	
	@POST
	@Path("add")
	public void addPerInfoCtg(AddPerInfoCtgCommand newPerInfoCtg) {
		addPerInfoCtgCommand.handle(newPerInfoCtg);
	}

	@POST
	@Path("update")
	public void updatePerInfoCtg(UpdatePerInfoCtgCommand newPerInfoCtg) {
		updatePerInfoCtgCommand.handle(newPerInfoCtg);
	}
}
