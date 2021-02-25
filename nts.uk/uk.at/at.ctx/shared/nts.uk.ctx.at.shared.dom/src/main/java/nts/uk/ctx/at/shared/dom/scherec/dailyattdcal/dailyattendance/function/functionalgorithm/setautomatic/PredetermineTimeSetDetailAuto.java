package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.functionalgorithm.setautomatic;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.DetermineClassifiByWorkInfoCond.AutoStampSetClassifi;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;

/**
 * @author thanh_nx
 *
 *         所定時間設定を自動打刻セット詳細に入れる
 */
@Stateless
public class PredetermineTimeSetDetailAuto {

	@Inject
	private AddTimeZoneToAttendance addTimeZoneToAttendance;

	public List<TimeLeavingWork> process(String companyId, String workTimeCode, AutoStampSetClassifi autoStampClasssifi,
			List<TimezoneUse> timezones) {

		List<TimeLeavingWork> result = new ArrayList<>();
		// 所定時間帯．時間帯を順次確認する
		timezones.stream().forEach(timeZone -> {
			// 時間帯．使用区分
			if (timeZone.getUseAtr() == UseSetting.USE) {
				result.add(addTimeZoneToAttendance.addTimeZone(companyId, workTimeCode, timeZone, autoStampClasssifi));
			}
		});
		return result;
	}
}
