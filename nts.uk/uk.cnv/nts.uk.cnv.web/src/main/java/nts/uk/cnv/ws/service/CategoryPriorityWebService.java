package nts.uk.cnv.ws.service;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.cnv.app.command.DeleteCategoryPriorityCommand;
import nts.uk.cnv.app.command.DeleteCategoryPriorityCommandHandler;
import nts.uk.cnv.app.command.RegistCategoryPriorityCommand;
import nts.uk.cnv.app.command.RegistCategoryPriorityCommandHandler;
import nts.uk.cnv.app.command.UpdateCategoryPrioriesSequenceCommand;
import nts.uk.cnv.app.command.UpdateCategoryPrioriesSequenceCommandHandler;
import nts.uk.cnv.app.finder.CategoryPriorityFinder;

@Path("cnv/categorypriority")
@Produces("application/json")
public class CategoryPriorityWebService extends WebService{

	@Inject
	private RegistCategoryPriorityCommandHandler handler;

	@Inject
	private DeleteCategoryPriorityCommandHandler deleteHandler;
	
	@Inject
	private UpdateCategoryPrioriesSequenceCommandHandler updateHandler;
	
	@Inject
	private CategoryPriorityFinder catetoryFinder;

	@POST
	@Path("getcategories")
	public List<String> getCategories() {
		return catetoryFinder.getCategories();
	}

	@POST
	@Path("regist")
	public void register(RegistCategoryPriorityCommand command) {
		handler.handle(command);
	}

	@POST
	@Path("delete")
	public void delete(DeleteCategoryPriorityCommand command) {
		deleteHandler.handle(command);
	}

	@POST
	@Path("updatepriority")
	public void updatePriority(UpdateCategoryPrioriesSequenceCommand command) {
		updateHandler.handle(command);
	}
}
