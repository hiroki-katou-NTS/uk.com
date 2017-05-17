package nts.uk.ctx.pr.core.ws.rule.employment.allot;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification.DeleteClassificationAllotSettingHeaderCommand;
import nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification.DeleteClassificationAllotSettingHeaderHandler;
import nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification.InsertClassificationAllotSettingHeaderCommand;
import nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification.InsertClassificationAllotSettingHeaderHandler;
import nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification.UpdateClassificationAllotSettingHeaderCommand;
import nts.uk.ctx.pr.core.app.command.rule.employment.allot.classification.UpdateClassificationAllotSettingHeaderHandler;
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
	
	@Inject
	private InsertClassificationAllotSettingHeaderHandler insertClassificationAllotSetting;
	
	@Inject
	private DeleteClassificationAllotSettingHeaderHandler deleteClassificationSetting;

	@POST
	@Path("getallclassificationallotsettingheader")
	public List<ClassificationAllotSettingHeaderDto> getAllClassificationAllotSettingHeader(String data) {
		String companyCode = AppContexts.user().companyCode();
		return this.find.getAllClassificationAllotSettingHeader(companyCode);
	}
	
	@POST
	@Path("insertclassificationallotsettingheader")
	public void insert(InsertClassificationAllotSettingHeaderCommand command){
		this.insertClassificationAllotSetting.handle(command);
	}

	@POST
	@Path("updateclassificationallotsettingheader")
	public void update(UpdateClassificationAllotSettingHeaderCommand command) {
		this.updateClassificationAllotSetting.handle(command);
	}
	@POST
	@Path("deleteclassifaicationallotsettingheader")
	public void delete(DeleteClassificationAllotSettingHeaderCommand command){
		this.deleteClassificationSetting.handle(command);
		
	}
	
	
}
