package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.grantremainingdata;

import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialLeaveUseNumber;

public class SpecialLeaveUsedWork {
	private SpecialLeaveUseNumber useNumber;

	public SpecialLeaveUsedWork() {
		this.useNumber = new SpecialLeaveUseNumber();
	}


	/*
	 * 時間休暇消化数を求める
	 */
	public int calcDigestTime(int unDigestTime) {
		return this.useNumber.getUseTimeOfZero().getUseTimes().v() - unDigestTime;
	}

	public static SpecialLeaveUsedWork of(InterimSpecialHolidayMng interim) {
		SpecialLeaveUsedWork domain = new SpecialLeaveUsedWork();

		double days = interim.getUseDays().isPresent()?interim.getUseDays().get().v():0.0;
		int time = interim.getUseTimes().isPresent()?interim.getUseTimes().get().v():0;

		domain.useNumber = SpecialLeaveUseNumber.of(days, time);
		return domain;
	}
}
