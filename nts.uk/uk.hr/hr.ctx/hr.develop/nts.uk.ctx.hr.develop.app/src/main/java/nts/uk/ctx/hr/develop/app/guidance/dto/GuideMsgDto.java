package nts.uk.ctx.hr.develop.app.guidance.dto;

import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.hr.develop.dom.careermgmt.careerpath.Career;
import nts.uk.ctx.hr.develop.dom.guidance.Guidance;
import nts.uk.ctx.hr.develop.dom.guidance.GuideMsg;

@Data
@AllArgsConstructor
public class GuideMsgDto {

	public String categoryCode;
	
	public String categoryName;
	
	public String eventCode;
	
	public String eventName;
	
	public String programId;
	
	public String programName;
	
	public String screenId;
	
	public String screenName;
	
	public Boolean usageFlgByScreen;
	
	public String guideMsg;
	
	public String screenPath;
	
	public GuideMsgDto(GuideMsg guideMsg) {
		super();
		this.categoryCode = guideMsg.getCategoryCode();
		this.categoryName = guideMsg.getCategoryName();
		this.eventCode = guideMsg.getEventCode();
		this.eventName = guideMsg.getEventName();
		this.programId = guideMsg.getProgramId();
		this.programName = guideMsg.getProgramName();
		this.screenId = guideMsg.getScreenId();
		this.screenName = guideMsg.getScreenName();
		this.usageFlgByScreen = guideMsg.isUsageFlgByScreen();
		this.guideMsg = guideMsg.getGuideMsg().v();
		this.screenPath = guideMsg.getScreenPath().orElse("");
	}
	
	public GuideMsg toDomain() {
		return GuideMsg.createFromJavaType(
				this.categoryCode, 
				this.categoryName, 
				this.eventCode, 
				this.eventName,
				this.programId, 
				this.programName, 
				this.screenId, 
				this.screenName,
				this.usageFlgByScreen, 
				this.guideMsg, 
				this.screenPath
				);
	}
}
