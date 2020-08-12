package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import lombok.Value;

@Value
public class WorkTypeSetDto {
	private String workTypeCd;

	private int workAtr;

	private int digestPublicHd;

	private int holidayAtr;

	private int countHodiday;

	private int closeAtr;

	private int sumAbsenseNo;

	private int sumSpHodidayNo;

	private int timeLeaveWork;

	private int attendanceTime;

	private int genSubHodiday;

	private int dayNightTimeAsk;
}
