package nts.uk.ctx.pereg.ws.person.info.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pereg.app.command.person.info.category.UpdateNamePerInfoCtgCommand;
import nts.uk.ctx.pereg.app.command.person.info.category.UpdateNamePerInfoCtgCommandHandler;
import nts.uk.ctx.pereg.app.command.person.info.category.UpdatePerInfoCategoryOrderCommand;
import nts.uk.ctx.pereg.app.command.person.info.category.UpdatePerInfoCategoryOrderCommandHandler;
import nts.uk.ctx.pereg.app.find.person.category.PerCtgInfoDto;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFinder;
import nts.uk.ctx.pereg.app.find.person.category.PerInfoCtgFullDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * The class PerInfoCategoryWebservice
 * 
 * @author lanlt
 *
 */
@Path("ctx/pereg/person/info/ctg")
@Produces("application/json")
public class PerInfoCategoryWebservice extends WebService {
	@Inject
	private PerInfoCtgFinder finder;

	@Inject
	private UpdatePerInfoCategoryOrderCommandHandler updateCtgOrder;

	@Inject
	private UpdateNamePerInfoCtgCommandHandler updateCtgInfo;

	@POST
	@Path("findAll")
	public List<PerInfoCtgFullDto> getAllPerInfoCtg1() {
		String companyId = AppContexts.user().companyId();
		return this.finder.getAllPerInfoCtg(companyId);
	}
	
	@POST
	@Path("find/root/{categoryId}")
	public PerCtgInfoDto getNameCtgRoot(@PathParam("categoryId") String categoryId) {
		return this.finder.getDetailCtgInfo(categoryId);
	}

	@POST
	@Path("updateCtgInfo")
	public void update(UpdateNamePerInfoCtgCommand command) {
		this.updateCtgInfo.handle(command);
	}

	@POST
	@Path("updateCtgOrder")
	public void updateCategoryOrder(List<UpdatePerInfoCategoryOrderCommand> command) {
		this.updateCtgOrder.handle(command);
	}

}
