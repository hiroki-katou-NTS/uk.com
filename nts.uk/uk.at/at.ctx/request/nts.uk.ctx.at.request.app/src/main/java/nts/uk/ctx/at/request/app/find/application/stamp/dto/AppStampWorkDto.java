package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampWork;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@AllArgsConstructor
public class AppStampWorkDto {
	private Integer stampAtr;
	
	private Integer stampFrameNo;
	
	private Integer stampGoOutReason;
	
	private String supportCard;
		
	private String supportLocationCD;
	
	private Integer startTime;
	
	private String startLocation;
	
	private Integer endTime;
	
	private String endLocation;
	
	public static AppStampWorkDto convertToDto(AppStampWork appStampWork){
		if(appStampWork == null) return null;
		return new AppStampWorkDto(
				appStampWork.getStampAtr().value, 
				appStampWork.getStampFrameNo(), 
				appStampWork.getStampGoOutAtr().value,
				appStampWork.getSupportCard().orElse(null),
				appStampWork.getSupportLocationCD().orElse(null),
				appStampWork.getStartTime().map(x -> x.v()).orElse(null), 
				appStampWork.getStartLocation().orElse(null), 
				appStampWork.getEndTime().map(x -> x.v()).orElse(null), 
				appStampWork.getEndLocation().orElse(null));
	}
}
