package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.applist.service.OverTimeFrame;
@Value
public class AppHolidayWorkFull {

	private String appId;
	/**勤務種類 name*/
	private String workTypeName;
	/**就業時間帯 name*/
	private String workTimeName;
	//勤務開始時刻1
	private String startTime1;
	//勤務終了時刻1
	private String endTime1;
	//勤務開始時刻2
	private String startTime2;
	//勤務終了時刻2
	private String endTime2;
	
	private List<OverTimeFrame> lstFrame;
	//時間外時間の詳細
	private TimeNo417 timeNo417;
}
