/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.ImmutablePair;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanDuplication;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZone;
import nts.uk.ctx.at.shared.dom.worktime.ChangeableWorkingTimeZonePerNo;
import nts.uk.ctx.at.shared.dom.worktime.WorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonRestSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimezoneNo;
import nts.uk.ctx.at.shared.dom.worktime.common.GoLeavingWorkAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.LegalOTSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.OverTimeOfTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.service.WorkTimeAggregateRoot;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.HalfDayWorkSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeDivision;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

/**
 * ??????????????????
 * The Class FixedWorkSetting.
 *
 * UKDesign.?????????????????????.NittsuSystem.UniversalK.??????.shared.????????????.???????????????.??????????????????.??????????????????
 */
@Getter
@NoArgsConstructor
public class FixedWorkSetting extends WorkTimeAggregateRoot implements Cloneable, WorkSetting {

	/** The company id. */
	// ??????ID
	private String companyId;

	/** The work time code. */
	// ????????????????????????
	private WorkTimeCode workTimeCode;

	/** The offday work timezone. */
	// ?????????????????????
	private FixOffdayWorkTimezone offdayWorkTimezone;

	/** The common setting. */
	// ????????????
	@Setter
	private WorkTimezoneCommonSet commonSetting;

	/** The use half day shift. */
	// ?????????????????????????????????
	private HalfDayWorkSet useHalfDayShift;

	/** The common rest set. */
    //?????????????????????
    private CommonRestSetting commonRestSet;

//	/** The fixed work rest setting. *
//	// ???????????????????????????
//	private FixedWorkRestSet fixedWorkRestSetting;

	/** The lst half day work timezone. */
	// ?????????????????????
	private List<FixHalfDayWorkTimezone> lstHalfDayWorkTimezone;

	/** The lst stamp reflect timezone. */
	// ?????????????????????
	private List<StampReflectTimezone> lstStampReflectTimezone;

	/** The legal OT setting. */
	// ?????????????????????
	private LegalOTSetting legalOTSetting;

	/** The calculation setting. */
	// ????????????
	private Optional<FixedWorkCalcSetting> calculationSetting;

	/**
	 * Instantiates a new fixed work setting.
	 *
	 * @param memento
	 *            the memento
	 */
	public FixedWorkSetting(FixedWorkSettingGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.workTimeCode = memento.getWorkTimeCode();
		this.offdayWorkTimezone = memento.getOffdayWorkTimezone();
		this.commonSetting = memento.getCommonSetting();
		this.useHalfDayShift = memento.getUseHalfDayShift();
		this.commonRestSet = memento.getCommonRestSet();
//		this.fixedWorkRestSetting = memento.getFixedWorkRestSetting();
		this.lstStampReflectTimezone = memento.getLstStampReflectTimezone();
		this.legalOTSetting = memento.getLegalOTSetting();
		this.calculationSetting = memento.getCalculationSetting();

		if (!this.validateHalfDayWorkTime(memento.getLstHalfDayWorkTimezone())) {
            throw new BusinessException("Msg_2143");
        }
		this.lstHalfDayWorkTimezone = memento.getLstHalfDayWorkTimezone();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento
	 *            the memento
	 */
	public void saveToMemento(FixedWorkSettingSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setWorkTimeCode(this.workTimeCode);
		memento.setOffdayWorkTimezone(this.offdayWorkTimezone);
		memento.setCommonSetting(this.commonSetting);
		memento.setUseHalfDayShift(this.useHalfDayShift);
		memento.setCommonRestSet(this.commonRestSet);
//		memento.setFixedWorkRestSetting(this.fixedWorkRestSetting);
		memento.setLstStampReflectTimezone(this.lstStampReflectTimezone);
		memento.setLegalOTSetting(this.legalOTSetting);
		memento.setCalculationSetting(this.calculationSetting);

		if (!this.validateHalfDayWorkTime(lstHalfDayWorkTimezone)) {
		    throw new BusinessException("Msg_2143");
		}
		memento.setLstHalfDayWorkTimezone(this.lstHalfDayWorkTimezone);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((workTimeCode == null) ? 0 : workTimeCode.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FixedWorkSetting other = (FixedWorkSetting) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (workTimeCode == null) {
			if (other.workTimeCode != null)
				return false;
		} else if (!workTimeCode.equals(other.workTimeCode))
			return false;
		return true;
	}

	public static interface Require {
		Optional<FixedWorkSetting> fixedWorkSetting(String companyId, WorkTimeCode workTimeCode);
	}
	
	/**
	 * Correct data.
	 *
	 * @param screenMode
	 *            the screen mode
	 * @param workTimeType
	 *            the work time type
	 * @param oldDomain
	 *            the old domain
	 */
	public void correctData(ScreenMode screenMode, WorkTimeDivision workTimeType, FixedWorkSetting oldDomain) {
		// Dialog J: list stamp timezone
		Map<Entry<WorkNo, GoLeavingWorkAtr>, StampReflectTimezone> mapStampReflectTimezone = oldDomain.getLstStampReflectTimezone().stream()
				.collect(Collectors.toMap(
						item -> new ImmutablePair<WorkNo, GoLeavingWorkAtr>(item.getWorkNo(), item.getClassification()),
						Function.identity()));
		this.lstStampReflectTimezone.forEach(item -> item.correctData(screenMode, mapStampReflectTimezone.get(
				new ImmutablePair<WorkNo, GoLeavingWorkAtr>(item.getWorkNo(), item.getClassification()))));

		// Tab 8 -> 16
		this.commonSetting.correctData(screenMode, oldDomain.getCommonSetting());

		// Tab 17
		if (this.calculationSetting.isPresent()) {
			this.calculationSetting.get().correctData(screenMode, oldDomain.getCalculationSetting());
		}
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode
	 *            the screen mode
	 */
	public void correctDefaultData(ScreenMode screenMode) {
		// Tab 2 + 3 + 5: restore ?????????????????????
		this.lstHalfDayWorkTimezone.forEach(item -> item.correctDefaultData(screenMode));

		// Dialog J: list stamp timezone
		this.lstStampReflectTimezone.forEach(item -> item.correctDefaultData(screenMode));

		// Tab 8 -> 16
		this.commonSetting.correctDefaultData(screenMode);

		// Tab 17
		if (this.calculationSetting.isPresent()) {
			this.calculationSetting.get().correctDefaultData(screenMode);
		}
	}

	/**
	 * Correct default data.
	 *
	 * @param screenMode the screen mode
	 * @param overtimeSetting the overtime setting
	 */
	public void setDefaultData(ScreenMode screenMode) {
		if (screenMode == ScreenMode.SIMPLE || this.legalOTSetting == LegalOTSetting.OUTSIDE_LEGAL_TIME) {
			this.lstHalfDayWorkTimezone.forEach(item -> item.correctDefaultData());
		}
	}

	/**
 	 * create this Instance
 	 * TODO ??????????????????clone???????????????????????????
	 * @return new Instance
	 */
	@Override
	public FixedWorkSetting clone() {
		FixedWorkSetting cloned = new FixedWorkSetting();
		try {
			cloned.companyId = this.companyId;
			cloned.workTimeCode = new WorkTimeCode(this.workTimeCode.v());
			cloned.offdayWorkTimezone = this.offdayWorkTimezone.clone();
			cloned.commonSetting = this.commonSetting.clone();
			cloned.useHalfDayShift = this.useHalfDayShift;
			cloned.commonRestSet = this.commonRestSet;
//			cloned.fixedWorkRestSetting = this.fixedWorkRestSetting.clone();
			cloned.lstHalfDayWorkTimezone = this.lstHalfDayWorkTimezone.stream().map(c -> c.clone()).collect(Collectors.toList());
			cloned.lstStampReflectTimezone = this.lstStampReflectTimezone.stream().map(c -> c.clone()).collect(Collectors.toList());
			cloned.legalOTSetting = LegalOTSetting.valueOf(this.legalOTSetting.value);
			if(this.calculationSetting.isPresent()) {
				cloned.calculationSetting = this.calculationSetting.map(c -> c.clone());
			}
			else {
				cloned.calculationSetting = Optional.empty();
			}
		}
		catch (Exception e){
			throw new RuntimeException("FixedWorkSetting clone error.");
		}
		return cloned;
	}


	/**
	 * ?????????????????????????????????No?????????????????????No???????????????
	 * @param workType ????????????
	 * @return Map<???????????????No, ?????????????????????No>
	 */
	public Map<EmTimezoneNo, OverTimeFrameNo> getLegalOverTimeFrameNoMap(WorkType workType) {
		return this.getOverTimeOfTimeZoneSet(workType).stream()
				//??????????????????????????????OTFrameNo????????????????????????OverTimeFrameNo?????????????????????????????????
				.collect(Collectors.toMap(k->k.getWorkTimezoneNo(), v->new OverTimeFrameNo(v.getLegalOTframeNo().v())));
	}

	/**
	 * ??????????????????NO???????????????
	 * @param workType ????????????
	 * @return ?????????????????????No(List)
	 */
	public List<OverTimeFrameNo> getInLegalOverTimes(WorkType workType) {
		if(this.legalOTSetting.isOutsideLegal()) {
			return new ArrayList<>();
		}
		return this.getOverTimeOfTimeZoneSet(workType).stream()
				.map(o -> new OverTimeFrameNo(o.getLegalOTframeNo().v()))
				.distinct()
				.sorted((f,s) -> f.compareTo(s))
				.collect(Collectors.toList());
	}

	/**
	 * ???????????????????????????????????????????????????????????????
	 * @param workType ????????????
	 * @return ??????????????????????????????(List)
	 */
	public List<EmTimeZoneSet> getEmTimeZoneSet(WorkType workType) {
		return this.getFixHalfDayWorkTimezone(workType.getAttendanceHolidayAttr()).isPresent()
					?this.getFixHalfDayWorkTimezone(workType.getAttendanceHolidayAttr()).get().getWorkTimezone().getLstWorkingTimezone()
					:Collections.emptyList();
	}

	/**
	 * ???????????????????????????????????????????????????????????????
	 * @param workType ????????????
	 * @return ??????????????????????????????(List)
	 */
	public List<OverTimeOfTimeZoneSet> getOverTimeOfTimeZoneSet(WorkType workType) {
		return this.getFixHalfDayWorkTimezone(workType.getAttendanceHolidayAttr()).isPresent()
					? this.getFixHalfDayWorkTimezone(workType.getAttendanceHolidayAttr()).get().getWorkTimezone().getLstOTTimezone()
					: Collections.emptyList();
	}

	/**
	 * ????????????????????????????????????????????????????????????????????????????????????
	 *
	 * @param attendanceHolidayAttr ??????????????????
	 * @return ?????????????????????????????????????????????(List)
	 */
	public Optional<FixHalfDayWorkTimezone> getFixHalfDayWorkTimezone(AttendanceHolidayAttr attendanceHolidayAttr) {
		switch(attendanceHolidayAttr) {
			case FULL_TIME:	return this.getFixHalfDayWorkTimezone(AmPmAtr.ONE_DAY);
			case MORNING:	return this.getFixHalfDayWorkTimezone(AmPmAtr.AM);
			case AFTERNOON:	return this.getFixHalfDayWorkTimezone(AmPmAtr.PM);
			case HOLIDAY:	return Optional.empty();
			default:		throw new RuntimeException("unknown AttendanceHolidayAttr");
		}
	}

	/**
	 * ????????????????????????????????????????????????????????????????????????????????????
	 *
	 * @param amPmAtr ??????????????????
	 * @return ?????????????????????????????????????????????(List)
	 */
	private Optional<FixHalfDayWorkTimezone> getFixHalfDayWorkTimezone(AmPmAtr amPmAtr){
		return this.lstHalfDayWorkTimezone.stream().filter(timeZone -> timeZone.getDayAtr().equals(amPmAtr)).findFirst();
	}


	/**
	 * ?????????????????????????????????????????????
	 * @param require Require
	 * @return ????????????????????????
	 */
	@Override
	public ChangeableWorkingTimeZone getChangeableWorkingTimeZone(WorkSetting.Require require) {

		// ??????
		val forWholeDay = this.getListOfChangeableWorkingTimeZonePerNoByAmPmAtr(require, AmPmAtr.ONE_DAY);	// 1???
		val forAm = this.getListOfChangeableWorkingTimeZonePerNoByAmPmAtr(require, AmPmAtr.AM);				// ??????
		val forPm = this.getListOfChangeableWorkingTimeZonePerNoByAmPmAtr(require, AmPmAtr.PM);				// ??????
		// ??????
		val forWorkOnDayOff = this.getListOfChangeableWorkingTimeZoneOfWorkOnDayOff(require);

		return ChangeableWorkingTimeZone.create(forWholeDay, forAm, forPm, forWorkOnDayOff);

	}

	/**
	 * ???????????????????????????????????????????????????????????????????????????????????????
	 * @param require Require
	 * @param atr ??????????????????
	 * @return ??????NO??????????????????????????????????????????(?????????????????????)
	 */
	private List<ChangeableWorkingTimeZonePerNo> getListOfChangeableWorkingTimeZonePerNoByAmPmAtr(WorkSetting.Require require, AmPmAtr atr) {

		// ???????????????
		val workings = this.getPredetermineTimeSetting(require).get().getTimezoneByAmPmAtrForCalc(atr);
		// ???????????????
		val overtimes = this.getTimeZoneOfOvertimeWorkByAmPmAtr(atr);

		return IntStream.rangeClosed( 0, workings.size() - 1 ).boxed()
			.map( index -> {
				val forStart = this.getStartOfChangeableWorkingTimeZone(index, workings, overtimes);
				val forEnd = this.getEndOfChangeableWorkingTimeZone(index, workings, overtimes);
				return ChangeableWorkingTimeZonePerNo.create(new WorkNo(index + 1), forStart, forEnd);
			}).collect(Collectors.toList());

	}

	/**
	 * ???????????????????????????????????????????????????????????????
	 * @param atr ??????????????????
	 * @return ????????????????????????(????????????????????????)
	 */
	public List<TimeSpanForCalc> getTimeZoneOfOvertimeWorkByAmPmAtr(AmPmAtr atr) {

		// ????????????????????????????????????
		val timezoneSetting = this.lstHalfDayWorkTimezone.stream()
				.filter( e -> e.getDayAtr() == atr )
				.findFirst().get();

		return timezoneSetting.getWorkTimezone().getOvertimeWorkingTimezonesForCalc();

	}

	/**
	 * ????????????????????????????????????????????????????????????
	 * @param index ???????????????????????????index
	 * @param workings ????????????????????????
	 * @param overtimes ????????????????????????
	 * @return ?????????????????????????????????????????????
	 */
	private TimeSpanForCalc getStartOfChangeableWorkingTimeZone(int index, List<TimeSpanForCalc> workings, List<TimeSpanForCalc> overtimes) {

		// ???????????????????????????????????????
		val timespan = new TimeSpanForCalc( workings.get(index).getStart(), workings.get(index).getStart() );

		if (index == 0) {
			/* ???????????? */
			// ????????????????????????????????????????????????????????????
			val ovtWrkBeforeStart = overtimes.stream()
					.filter( e -> e.getStart().lessThan( timespan.getStart() ) )
					.sorted(Comparator.comparing( e -> ((TimeSpanForCalc)e).getStart() ))
					.findFirst();
			// ??????????????????????????????????????????????????????????????????????????????????????????????????????
			if (ovtWrkBeforeStart.isPresent()) {
				return timespan.shiftOnlyStart( ovtWrkBeforeStart.get().getStart() );
			}
		}

		return timespan;

	}

	/**
	 * ????????????????????????????????????????????????????????????
	 * @param index ???????????????????????????index
	 * @param workings ????????????????????????
	 * @param overtimes ????????????????????????
	 * @return ?????????????????????????????????????????????
	 */
	private TimeSpanForCalc getEndOfChangeableWorkingTimeZone(int index, List<TimeSpanForCalc> workings, List<TimeSpanForCalc> overtimes) {

		// ???????????????????????????????????????
		val timespan = new TimeSpanForCalc( workings.get(index).getEnd(), workings.get(index).getEnd() );

		if ( index == (workings.size() - 1) ) {
			/* ???????????? */
			// ????????????????????????????????????????????????????????????
			val ovtWrkAfterEnd = overtimes.stream()
					.filter( e -> e.getEnd().greaterThan( timespan.getEnd() ) )
					.sorted(Comparator.comparing( e -> ((TimeSpanForCalc)e).getEnd() ).reversed())
					.findFirst();
			if (ovtWrkAfterEnd.isPresent()) {
				// ?????????????????????????????????????????????
				return timespan.shiftOnlyEnd( ovtWrkAfterEnd.get().getEnd() );
			}
		} else {
			/* ?????????????????? */
			// ??????????????????????????????????????????
			return timespan.shiftOnlyEnd( workings.get(index + 1).getStart() );
		}

		return timespan;

	}

	/**
	 * ??????????????????????????????????????????????????????????????????
	 * @param require Require
	 * @return ???????????????????????????????????????(?????????)
	 */
	private List<ChangeableWorkingTimeZonePerNo> getListOfChangeableWorkingTimeZoneOfWorkOnDayOff(WorkSetting.Require require) {

		// ?????????????????????????????????
		val predTimeStg = this.getPredetermineTimeSetting(require).get();

		// ???????????????????????????????????????
		val workOnDayOff = this.offdayWorkTimezone.getFirstAndLastTimeOfOffdayWorkTimezone();

		// ??????????????????????????????????????????
		if (predTimeStg.getTimezoneByAmPmAtrForCalc(AmPmAtr.ONE_DAY).stream()
				.allMatch( e -> workOnDayOff.checkDuplication( e ) == TimeSpanDuplication.NOT_DUPLICATE )) {
			// ???????????????????????????????????????
			return Collections.emptyList();
		}

		// ??????????????????????????????????????????
		val timezones = new ArrayList<>(Arrays.asList(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd((new WorkNo(1)), workOnDayOff)));
		if (predTimeStg.isUseShiftTwo()) {
			timezones.add(ChangeableWorkingTimeZonePerNo.createAsStartEqualsEnd((new WorkNo(2)), workOnDayOff));
		}

		return timezones;

	}


	/**
	 * ??????????????????????????????
	 * @param isWorkingOnDayOff ?????????
	 * @param amPmAtr ??????????????????
	 * @return ????????????
	 */
	@Override
	public BreakTimeZone getBreakTimeZone(boolean isWorkingOnDayOff, AmPmAtr amPmAtr) {

		// ?????????????????????????????????????????????
	    TimezoneOfFixedRestTimeSet fixedRestTimezone;
		if (isWorkingOnDayOff) {
			// ??????
			fixedRestTimezone = this.offdayWorkTimezone.getRestTimezone();
		} else {
			// ????????????
			fixedRestTimezone = this.getFixHalfDayWorkTimezone(amPmAtr)
									.map( e -> e.getRestTimezone() ).get();
		}

		return BreakTimeZone.createAsFixed(fixedRestTimezone.getRestTimezonesForCalc());

	}

	/**
	 * inv-1
	 */
	private boolean validateHalfDayWorkTime(List<FixHalfDayWorkTimezone> lstHalfDayWorkTimezone){
		val onlyOneAllDay = lstHalfDayWorkTimezone
				.stream()
				.filter(tz -> tz.getDayAtr() == AmPmAtr.ONE_DAY)
				.count() == 1;
		val onlyOneAM = lstHalfDayWorkTimezone
				.stream()
				.filter(tz -> tz.getDayAtr() == AmPmAtr.AM)
				.count() == 1;
		val onlyOnePM = lstHalfDayWorkTimezone
				.stream()
				.filter(tz -> tz.getDayAtr() == AmPmAtr.PM)
				.count() == 1;
		return onlyOneAllDay && onlyOneAM && onlyOnePM;
	}
}
