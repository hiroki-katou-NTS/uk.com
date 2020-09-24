package nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * Implement RangeOfDayTimeZoneService
 * 
 * @author trungtran
 *
 */
@Stateless
public class RangeOfDayTimeZoneServiceImpl implements RangeOfDayTimeZoneService {

	@Inject
	public PredetemineTimeSettingRepository preTimeSetRepo;

	@Override
	public TimeSpanForCalc getRangeofOneDay(String siftCode) {
		String companyId = AppContexts.user().companyId();
		Optional<PredetemineTimeSetting> optional = preTimeSetRepo.findByWorkTimeCode(companyId, siftCode);
		if (!optional.isPresent()) {
			return null;
		}
		PredetemineTimeSetting prSetting = optional.get();
		TimeWithDayAttr startTime = prSetting.getStartDateClock();
		AttendanceTime rangeTimeDay = prSetting.getRangeTimeDay();
		TimeWithDayAttr endTime = startTime.forwardByMinutes(rangeTimeDay.valueAsMinutes());
		return new TimeSpanForCalc(startTime, endTime);
	}

	@Override
	public DuplicateStateAtr checkPeriodDuplication(TimeSpanForCalc destination, TimeSpanForCalc source) {
		/** 基準となる時間帯の開始時刻 */
		int s1 = destination.getStart().valueAsMinutes();
		/** e1=基準となる時間帯の終了時刻 */
		int e1 = destination.getEnd().valueAsMinutes();
		/** s2=比較したい時間帯の開始時刻 */
		int s2 = source.getStart().valueAsMinutes();
		/** e2=比較したい時間帯の終了時刻 */
		int e2 = source.getEnd().valueAsMinutes();
		if (s1 == s2 && e1 == e2) {
			return DuplicateStateAtr.valueOf(0);
		}
		if (s1 < s2 && e1 > e2) {
			return DuplicateStateAtr.valueOf(1);
		}
		if (s1 == s2 && e1 > e2) {
			return DuplicateStateAtr.valueOf(2);
		}
		if (s1 < s2 && e1 == e2) {
			return DuplicateStateAtr.valueOf(3);
		}
		if (s1 > s2 && e1 < e2) {
			return DuplicateStateAtr.valueOf(4);
		}
		if (s1 == s2 && e1 < e2) {
			return DuplicateStateAtr.valueOf(5);
		}
		if (s1 > s2 && e1 == e2) {
			return DuplicateStateAtr.valueOf(6);
		}
		if (s2 == e1) {
			return DuplicateStateAtr.valueOf(7);
		}
		if (e2 == s1) {
			return DuplicateStateAtr.valueOf(8);
		}
		if (s2 > e1) {
			return DuplicateStateAtr.valueOf(9);
		}
		if (e2 < s1) {
			return DuplicateStateAtr.valueOf(10);
		}
		if (s1 < s2 && e1 < e2 && s2 < e1) {
			return DuplicateStateAtr.valueOf(11);
		}
		if (s1 > s2 && e1 > e2 && e2 > s1) {
			return DuplicateStateAtr.valueOf(12);
		}
		return null;

	}

	@Override
	public DuplicationStatusOfTimeZone checkStateAtr(DuplicateStateAtr duplicateStateAtr) {
		switch (duplicateStateAtr.value) {
		case 0:
			return DuplicationStatusOfTimeZone.valueOf(0);
		case 11:
			return DuplicationStatusOfTimeZone.valueOf(1);
		case 12:
			return DuplicationStatusOfTimeZone.valueOf(2);
		case 4:
		case 5:
		case 6:
			return DuplicationStatusOfTimeZone.valueOf(3);
		case 1:
		case 2:
		case 3:
			return DuplicationStatusOfTimeZone.valueOf(4);
		case 7:
		case 8:
		case 9:
		case 10:
			return DuplicationStatusOfTimeZone.valueOf(5);
		default:
			return null;
		}
	}

	@Override
	public void checkWithinRangeOfOneDay(DuplicationStatusOfTimeZone dStatusOfTimeZone, TimeSpanForCalc destination) {
		if (dStatusOfTimeZone.value == 1) {
			throw new BusinessException("Msg_440", "KSU001_73", destination.getStart().getInDayTimeWithFormat(),
					destination.getEnd().getInDayTimeWithFormat());
		}
		if (dStatusOfTimeZone.value == 2) {
			throw new BusinessException("Msg_440", "KSU001_74", destination.getStart().getInDayTimeWithFormat(),
					destination.getEnd().getInDayTimeWithFormat());
		}
		if (dStatusOfTimeZone.value == 3 || dStatusOfTimeZone.value == 4) {
			throw new BusinessException("Msg_440", "KSU001_73", "KSU001_74", destination.getStart().getInDayTimeWithFormat(),
					destination.getEnd().getInDayTimeWithFormat());
		}

	}

}
