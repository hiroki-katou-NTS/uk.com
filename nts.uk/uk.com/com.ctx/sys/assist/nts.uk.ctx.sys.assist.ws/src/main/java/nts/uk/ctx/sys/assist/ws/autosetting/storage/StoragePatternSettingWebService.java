package nts.uk.ctx.sys.assist.ws.autosetting.storage;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.command.autosetting.storage.AddPatternCommand;
import nts.uk.ctx.sys.assist.app.command.autosetting.storage.AddPatternCommandHandler;
import nts.uk.ctx.sys.assist.app.command.autosetting.storage.DeletePatternCommand;
import nts.uk.ctx.sys.assist.app.command.autosetting.storage.DeletePatternCommandHandler;
import nts.uk.ctx.sys.assist.app.command.autosetting.storage.FindSelectedPatternCommand;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.CategoryDto;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.CategoryInitDisplayFinder;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.DataStoragePatternSettingDto;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.SelectedPatternFinder;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.SelectedPatternParameterDto;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.StartupParameterDto;

@Path("ctx/sys/assist/autosetting")
@Produces("application/json")
public class StoragePatternSettingWebService {
	
	@Inject
	private CategoryInitDisplayFinder categoryInitDisplayFinder;
	
	@Inject 
	private SelectedPatternFinder selectedPatternFinder;
	
	@Inject
	private AddPatternCommandHandler addPatternCommandHandler;
	
	@Inject
	private DeletePatternCommandHandler deletePatternCommandHandler;
	
	@POST
	@Path("/storagePattern/initialDisplay")
	public StartupParameterDto<CategoryDto, DataStoragePatternSettingDto> initDisplay() {
		return categoryInitDisplayFinder.initDisplay();
	}
	
	@POST
	@Path("/storagePattern/select")
	public SelectedPatternParameterDto findSelectedPattern(FindSelectedPatternCommand command) {
		return selectedPatternFinder.findSelectedPattern(command);
	}
	
	@POST
	@Path("/storagePattern/add")
	public void addPattern(AddPatternCommand command) {
		addPatternCommandHandler.handle(command);
	}
	
	@POST
	@Path("/storagePattern/delete")
	public void deletePattern(DeletePatternCommand command) {
		deletePatternCommandHandler.handle(command);
	}
}
