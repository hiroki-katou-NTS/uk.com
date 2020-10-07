package nts.uk.ctx.sys.assist.ws.autosetting.deletion;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.DeleteCategoryInitDisplayFinder;
import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.SelectedDelPatternFinder;
import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.SelectedDelPatternParameterDto;
import nts.uk.ctx.sys.assist.app.command.autosetting.deletion.AddDelPatternCommand;
import nts.uk.ctx.sys.assist.app.command.autosetting.deletion.AddDelPatternCommandHandler;
import nts.uk.ctx.sys.assist.app.command.autosetting.deletion.DeleteDelPatternCommand;
import nts.uk.ctx.sys.assist.app.command.autosetting.deletion.DeleteDelPatternCommandHandler;
import nts.uk.ctx.sys.assist.app.command.autosetting.deletion.FindDelSelectedPatternCommand;
import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.DataDeletionPatternSettingDto;
import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.DeleteCategoryDto;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.StartupParameterDto;

@Path("ctx/sys/assist/autosetting")
@Produces("application/json")
public class DeletionPatternSettingWebService {
	
	@Inject
	private DeleteCategoryInitDisplayFinder categoryInitDisplayFinder;

	@Inject
	private SelectedDelPatternFinder selectedDelPatternFinder;
	
	@Inject
	private AddDelPatternCommandHandler addDelPatternCommandHandler;
	
	@Inject
	private DeleteDelPatternCommandHandler deleteDelPatternCommandHandler;
	
	@POST
	@Path("/deletionPattern/initialDisplay")
	public StartupParameterDto<DeleteCategoryDto, DataDeletionPatternSettingDto> initDisplay() {
		return categoryInitDisplayFinder.initDisplay();
	}
	
	@POST
	@Path("/deletionPattern/select")
	public SelectedDelPatternParameterDto selectPattern(FindDelSelectedPatternCommand command) {
		return selectedDelPatternFinder.findSelectedPattern(command);
	}
	
	@POST
	@Path("/deletionPattern/add")
	public void add(AddDelPatternCommand command) {
		addDelPatternCommandHandler.handle(command);
	}
	
	@POST
	@Path("/deletionPattern/delete")
	public void delete(DeleteDelPatternCommand command) {
		deleteDelPatternCommandHandler.handle(command);
	}
}
