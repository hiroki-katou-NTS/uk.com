package nts.uk.ctx.sys.assist.ws.autosetting.deletion;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.command.autosetting.deletion.SelectDelCategoryCommand;
import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.DataDeletionPatternSettingDto;
import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.ScreenDelDisplayProcessingDto;
import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.ScreenDelDisplayProcessingFinder;
import nts.uk.ctx.sys.assist.app.find.autosetting.deletion.SelectDelCategoryFinder;

@Path("ctx/sys/assist/autosetting")
@Produces("application/json")
public class SelectDelPatternSettingWebService {

	@Inject
	private ScreenDelDisplayProcessingFinder screenDisplayProcessingFinder;
	
	@Inject
	private SelectDelCategoryFinder selectCategoryFinder;
	
	@POST
	@Path("screenDelDisplayProcessing")
	public ScreenDelDisplayProcessingDto findScreenDisplayProcessing() {
		return screenDisplayProcessingFinder.findScreenDisplayProcessing();
	}
	
	@POST
	@Path("delPatternSettingSelect")
	public DataDeletionPatternSettingDto findSelectionCategoryName(SelectDelCategoryCommand command) {
		return selectCategoryFinder.findSelectCategoryInfo(command);
	}
}
