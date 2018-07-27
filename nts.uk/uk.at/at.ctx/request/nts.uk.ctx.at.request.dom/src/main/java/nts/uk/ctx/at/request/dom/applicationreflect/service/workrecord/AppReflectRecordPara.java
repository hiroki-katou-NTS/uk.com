package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class AppReflectRecordPara {
	/**
	 * 
	 */
	private ReflectRecordInfor recordInfor;
	/**
	 * 直行直帰申請
	 */
	private GobackReflectPara gobackInfor;
	/**
	 * 残業申請
	 */
	private OvertimeReflectPara overtimeInfor;
	/**
	 * 勤務変更
	 */
	private CommonReflectPara workchangeInfor;
	/**
	 * 休日出勤申請
	 */
	private HolidayWorkReflectPara holidayworkInfor;
	/**
	 * 休暇申請
	 */
	private CommonReflectPara absenceInfor;
	/**
	 * 振休申請
	 */
	private CommonReflectPara absenceLeaveAppInfor;
	/**
	 * 振出申請
	 */
	private CommonReflectPara recruitmentInfor;

}
