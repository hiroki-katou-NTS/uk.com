package nts.uk.ctx.sys.assist.ws.autosetting.storage;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.command.autosetting.storage.SelectCategoryCommand;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.DataStoragePatternSettingDto;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.ScreenDisplayProcessingDto;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.ScreenDisplayProcessingFinder;
import nts.uk.ctx.sys.assist.app.find.autosetting.storage.SelectCategoryFinder;

@Path("ctx/sys/assist/autosetting")
@Produces("application/json")
public class SelectPatternSettingWebService {
	
	@Inject
	private ScreenDisplayProcessingFinder screenDisplayProcessingFinder;
	
	@Inject
	private SelectCategoryFinder selectCategoryFinder;
	
	@POST
	@Path("screenDisplayProcessing")
	public ScreenDisplayProcessingDto findScreenDisplayProcessing() {
		return screenDisplayProcessingFinder.findScreenDisplayProcessing();
	}
	
	@POST
	@Path("patternSettingSelect")
	public DataStoragePatternSettingDto findSelectionCategoryName(SelectCategoryCommand command) {
		return selectCategoryFinder.findSelectCategoryInfo(command);
	}
}
