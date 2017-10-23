package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.stamp.AppStampOnlineRecord;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Data
@AllArgsConstructor
public class AppStampOnlineRecordDto {
	private Integer stampCombinationAtr;
	
	private Integer appTime;
	
	public static AppStampOnlineRecordDto convertToDto(AppStampOnlineRecord appStampOnlineRecord){
		if(appStampOnlineRecord == null) return null;
		return new AppStampOnlineRecordDto(
				appStampOnlineRecord.getStampCombinationAtr().value, 
				appStampOnlineRecord.getAppTime());
	}
}
