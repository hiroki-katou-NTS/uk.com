package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampGoOutPermit;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@AllArgsConstructor
public class AppStampGoOutPermitDto {
	private Integer stampAtr;
	
	private Integer stampFrameNo;
	
	private Integer stampGoOutReason;
	
	private Integer startTime;
	
	private String startLocation;
	
	private Integer endTime;
	
	private String endLocation;
	
	public static AppStampGoOutPermitDto convertToDto(AppStampGoOutPermit appStampGoOutPermit){
		if(appStampGoOutPermit == null) return null;
		return new AppStampGoOutPermitDto(
				appStampGoOutPermit.getStampAtr().value, 
				appStampGoOutPermit.getStampFrameNo(), 
				appStampGoOutPermit.getStampGoOutAtr().value, 
				appStampGoOutPermit.getStartTime().get().v(), 
				appStampGoOutPermit.getStartLocation().get(), 
				appStampGoOutPermit.getEndTime().get().v(), 
				appStampGoOutPermit.getEndLocation().get());
	}
}
