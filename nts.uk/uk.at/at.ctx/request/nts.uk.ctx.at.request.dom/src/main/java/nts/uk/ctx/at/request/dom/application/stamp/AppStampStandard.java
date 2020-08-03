package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppStampStandard {
	
	private Integer startTime;
	
	private Integer endTime;
	
	private Integer framNo;
	
	private StampAtrOther stampAtrOther;
	
	public static List<AppStampStandard> toListStandard(AppStamp appStamp) {
		
		List<AppStampStandard> listAppStampStandard = new ArrayList<AppStampStandard>();
//		時刻
		List<TimeStampApp> listTimeStampApp = appStamp.getListTimeStampApp();	
//		時刻の取消
		List<DestinationTimeApp> listDestinationTimeApp = appStamp.getListDestinationTimeApp();
//		時間帯
		List<TimeStampAppOther> listTimeStampAppOther = appStamp.getListTimeStampAppOther();
//		時間帯の取消
		List<DestinationTimeZoneApp> listDestinationTimeZoneApp = appStamp.getListDestinationTimeZoneApp();
		
		
		return null;
	}

}
