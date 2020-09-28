package nts.uk.ctx.sys.assist.ws.autosetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.command.autosetting.AddCategoryCommand;
import nts.uk.ctx.sys.assist.app.command.autosetting.AddCategoryCommandHandler;
import nts.uk.ctx.sys.assist.app.find.autosetting.CategoryInitDisplayFinder;
import nts.uk.ctx.sys.assist.app.find.autosetting.StartupParameterDto;

@Path("ctx/sys/assist/autosetting")
@Produces("application/json")
public class CategorySettingWebService {
	
	@Inject
	private CategoryInitDisplayFinder categoryInitDisplayFinder;
	
	@Inject
	private AddCategoryCommandHandler addCategoryCommandHandler;
	
	@POST
	@Path("/category/initialDisplay")
	public StartupParameterDto initDisplay() {
		return categoryInitDisplayFinder.initDisplay();
	}
	
	@POST
	@Path("/category/add")
	public void addCategory(AddCategoryCommand command) {
		addCategoryCommandHandler.handle(command);
	}
}
