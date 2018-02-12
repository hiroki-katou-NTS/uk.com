package nts.uk.ctx.at.request.dom.application.holidayworktime;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * @author loivt
 * 休日出勤申請
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppHolidayWork extends AggregateRoot{
	
	/**
	 * application
	 */
	private Application_New application;
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 残業申請時間設定
	 */
	private List<HolidayWorkInput> holidayWorkInputs;
	/**
	 * 勤務種類コード
	 */
	private WorkTypeCode workTypeCode;
	/**
	 * 就業時間帯
	 */
	private WorkTimeCode workTimeCode;
	
	/**
	 * 勤務時間
	 */
	private HolidayWorkClock workClock1;
	
	/**
	 * 勤務時間2
	 */
	private HolidayWorkClock workClock2;
	
	/**
	 * 乖離定型理由
	 */
	private String divergenceReason;
	/**
	 * 就業時間外深夜時間
	 */
	private int holidayShiftNight;
	
	public AppHolidayWork(String companyID,
						String appID,
						String workTypeCode, 
						String workTimeCode, 
						int workClockStart1,
						int workClockEnd1,
						int workClockStart2,
						int workClockEnd2,
						int goAtr1,
						int backAtr1,
						int goAtr2,
						int backAtr2,
						String divergenceReason,
						int holidayShiftNight){
		this.companyID = companyID;
		this.appID = appID;
		this.workTypeCode = new WorkTypeCode(workTypeCode);
		this.workTimeCode = new WorkTimeCode(workTimeCode);
		this.workClock1 = HolidayWorkClock.validateTime(workClockStart1, workClockEnd1,goAtr1,backAtr1);
		this.workClock2 = HolidayWorkClock.validateTime2(workClockStart2, workClockEnd2,goAtr2,backAtr2);
		this.divergenceReason = divergenceReason;
		this.holidayShiftNight = holidayShiftNight;
	}
	
	public static AppHolidayWork createSimpleFromJavaType(String companyID,
														String appID, 
														String workTypeCode, 
														String workTimeCode, 
														int workClockStart1,
														int workClockEnd1,
														int workClockStart2,
														int workClockEnd2,
														int goAtr1,
														int backAtr1,
														int goAtr2,
														int backAtr2,
														String divergenceReason,
														int overTimeShiftNight ){
		return new AppHolidayWork(companyID,
				appID,
				workTypeCode,
				workTimeCode,
				workClockStart1,
				workClockEnd1,
				workClockStart2,
				workClockEnd2,
				goAtr1,
				backAtr1,
				goAtr2,
				backAtr2,
				divergenceReason,
				overTimeShiftNight);
		
	}

}
