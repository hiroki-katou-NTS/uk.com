package nts.uk.ctx.hr.develop.ws.guidance;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.app.guidance.command.UpdateGuidanceCommandHandler;
import nts.uk.ctx.hr.develop.app.guidance.dto.CategoryGuideDto;
import nts.uk.ctx.hr.develop.app.guidance.dto.GuidanceDto;
import nts.uk.ctx.hr.develop.app.guidance.dto.GuideMsgDto;
import nts.uk.ctx.hr.develop.app.guidance.dto.ParamFindScreen;
import nts.uk.ctx.hr.develop.app.guidance.dto.ScreenGuideParamList;
import nts.uk.ctx.hr.develop.app.guidance.dto.ScreenGuideSettingDto;
import nts.uk.ctx.hr.develop.app.guidance.find.GuidanceFinder;
import nts.uk.shr.com.context.AppContexts;

@Path("guidance")
@Produces(MediaType.APPLICATION_JSON)
public class GuidanceWS {

	@Inject
	private GuidanceFinder finder;
	
	@Inject
	private UpdateGuidanceCommandHandler commandGuidance;
	
	@POST
	@Path("/getGuidance")
	public GuidanceDto getGuideDispSetting(){
		String cId = AppContexts.user().companyId();
		return finder.getGuideDispSetting(cId);
	}
	
	@POST
	@Path("/saveGuidance")
	public void saveGuideDispSetting(GuidanceDto command){
		commandGuidance.updateGuideDispSetting(command);
	}
	
	@POST
	@Path("/getGuideCategory")
	public List<CategoryGuideDto> getGuideCategory(){
		return finder.getGuideCategory();
	}
	
	@POST
	@Path("/getGuideMessageList")
	public List<GuideMsgDto> getGuideMessageList(ParamFindScreen param){
		return finder.getGuideMsg(param);
	}
	
	@POST
	@Path("/updateGuideMsg")
	public void updateGuideMsg(GuideMsgDto command){
		commandGuidance.updateGuideMsg(command);
	}
	
	@POST
	@Path("/guideOperate")
	public List<ScreenGuideSettingDto> guideOperate(ScreenGuideParamList param){
		return finder.guideOperate(param);
	}
}
