package nts.uk.ctx.sys.assist.ws.autosetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.command.autosetting.SelectCategoryCommand;
import nts.uk.ctx.sys.assist.app.find.autosetting.DataStoragePatternSettingDto;
import nts.uk.ctx.sys.assist.app.find.autosetting.ScreenDisplayProcessingDto;
import nts.uk.ctx.sys.assist.app.find.autosetting.ScreenDisplayProcessingFinder;
import nts.uk.ctx.sys.assist.app.find.autosetting.SelectCategoryFinder;

@Path("ctx/sys/assist/autosetting")
@Produces("application/json")
public class SelectPatternSettingWebService {
	
	@Inject
	private ScreenDisplayProcessingFinder screenDisplayProcessingFinder;
	
	@Inject
	private SelectCategoryFinder selectionCategoryNameFinder;
	
	@POST
	@Path("screenDisplayProcessing")
	public ScreenDisplayProcessingDto findScreenDisplayProcessing() {
		return screenDisplayProcessingFinder.findScreenDisplayProcessing();
	}
	
	@POST
	@Path("selectionCategoryName")
	public DataStoragePatternSettingDto findSelectionCategoryName(SelectCategoryCommand command) {
		return selectionCategoryNameFinder.findSelectCategoryInfo(command);
	}
}
