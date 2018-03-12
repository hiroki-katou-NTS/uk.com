package nts.uk.ctx.at.request.dom.application.appforleave.apptimedigest;

import lombok.Data;

/**
 * @author loivt
 * 時間消化申請
 */
@Data
public class AppTimeDigest {
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 60H超過時間
	 */
	private TimeOffPrimitive sixtyHOvertime;
	/**
	 * 時間代休時間
	 */
	private TimeOffPrimitive hoursOfSubHoliday;
	/**
	 * 時間年休時間
	 */
	private TimeOffPrimitive hoursOfHoliday;
}
