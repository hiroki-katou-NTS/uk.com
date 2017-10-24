package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampCancel;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@AllArgsConstructor
public class AppStampCancelDto {
	private Integer stampAtr;
	
	private Integer stampFrameNo;
	
	private Integer cancelAtr;
	
	public static AppStampCancelDto convertToDto(AppStampCancel appStampCancel){
		if(appStampCancel == null) return null;
		return new AppStampCancelDto(
				appStampCancel.getStampAtr().value, 
				appStampCancel.getStampFrameNo(), 
				appStampCancel.getCancelAtr().intValue());
	}
}
