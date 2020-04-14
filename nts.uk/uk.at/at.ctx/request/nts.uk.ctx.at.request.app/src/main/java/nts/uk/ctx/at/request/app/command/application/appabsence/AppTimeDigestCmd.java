package nts.uk.ctx.at.request.app.command.application.appabsence;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeOffPrimitive;

@Data
public class AppTimeDigestCmd {
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
