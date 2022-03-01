package nts.uk.ctx.at.record.dom.remainingnumber.childcare;

import java.util.ArrayList;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeVacationDigestUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.shr.com.time.calendar.MonthDay;

public class NursingLeaveSettingHelper {
	public static NursingLeaveSetting createNursingLeaveSetting(ManageDistinct manageDistinct,
			NursingCategory nursingCategory) {
		return new NursingLeaveSetting("000000000008-0006", manageDistinct, nursingCategory, new MonthDay(1, 1),
				new ArrayList<>(), Optional.empty(), Optional.empty(), null);
	}

	public static NursingLeaveSetting createNursingLeaveSetting(NursingCategory nursingCategory) {
		return new NursingLeaveSetting("000000000008-0006", ManageDistinct.NO, nursingCategory, new MonthDay(1, 1),
				new ArrayList<>(), Optional.empty(), Optional.empty(), null);
	}

	public static NursingLeaveSetting createNursingLeaveSetting(ManageDistinct manageDistinct,
			NursingCategory nursingCategory, TimeVacationDigestUnit timeCareNursingSetting) {
		return new NursingLeaveSetting("000000000008-0006", manageDistinct, nursingCategory, new MonthDay(1, 1),
				new ArrayList<>(), Optional.empty(), Optional.empty(), timeCareNursingSetting);
	}

	public static TimeVacationDigestUnit createTimeCareNursingSet(ManageDistinct manageDistinct) {
		return new TimeVacationDigestUnit(manageDistinct, TimeDigestiveUnit.FifteenMinute);
	}
}
