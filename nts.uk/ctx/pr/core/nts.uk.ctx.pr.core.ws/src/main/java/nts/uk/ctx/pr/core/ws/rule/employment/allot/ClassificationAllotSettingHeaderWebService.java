package nts.uk.ctx.pr.core.ws.rule.employment.allot;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.pr.core.app.command.rule.employment.allot.UpdateClassificationAllotSettingHeaderCommand;
import nts.uk.ctx.pr.core.app.command.rule.employment.allot.UpdateClassificationAllotSettingHeaderHandler;
import nts.uk.ctx.pr.core.app.find.rule.employment.allot.ClassificationAllotSettingHeaderDto;
import nts.uk.ctx.pr.core.app.find.rule.employment.allot.ClassificationAllotSettingHeaderFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("pr/core/allot")
@Produces(MediaType.APPLICATION_JSON)
public class ClassificationAllotSettingHeaderWebService {

	@Inject
	private ClassificationAllotSettingHeaderFinder find;

	@Inject
	private UpdateClassificationAllotSettingHeaderHandler updateClassificationAllotSetting;

	@POST
	@Path("getallclassificationallotsettingheader")
	public List<ClassificationAllotSettingHeaderDto> getAllClassificationAllotSettingHeader(String data) {
		String companyCode = AppContexts.user().companyCode();
		return this.find.getAllClassificationAllotSettingHeader(companyCode);
	}

	@POST
	@Path("updateclassificationallotsettingheader")
	public void update(UpdateClassificationAllotSettingHeaderCommand command) {
		this.updateClassificationAllotSetting.handle(command);
	}
}
