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

}
