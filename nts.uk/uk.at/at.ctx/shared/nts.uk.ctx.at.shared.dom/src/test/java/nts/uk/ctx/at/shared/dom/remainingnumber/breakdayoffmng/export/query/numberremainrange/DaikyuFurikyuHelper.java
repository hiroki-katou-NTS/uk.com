package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.OccurrenceDigClass;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.UnbalanceCompensation;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.CompensatoryDayoffDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.ManagementDataRemainUnit;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.DayOffError;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.AccuVacationBuilder;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.AccumulationAbsenceDetail.NumberConsecuVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.UnbalanceVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.VacationDetails;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.StatutoryAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;

public class DaikyuFurikyuHelper {

	public static String CID = "000000000000-0117";

	public static String SID = "292ae91c-508c-4c6e-8fe8-3e72277dec16";

	public static AccumulationAbsenceDetail createDetail(boolean isDaikyu, OccurrenceDigClass occurrentClass,
			GeneralDate deadline, DigestionAtr digestionCate, GeneralDate extinctionDate, boolean unknownDate,
			Optional<GeneralDate> dayoffDate, MngDataStatus dataAtr, String manageId, Integer timeOneDay,
			Integer timeHalfDay, Double occurrentDay, Integer occurrentTime, Double unbalanceDay,
			Integer unbalanceTime) {

		AccumulationAbsenceDetail detail = new AccuVacationBuilder(SID,
				new CompensatoryDayoffDate(unknownDate, dayoffDate), occurrentClass, dataAtr, manageId)
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(occurrentDay),
								Optional.ofNullable(occurrentTime == null ? null : new AttendanceTime(occurrentTime))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(unbalanceDay),
								Optional.ofNullable(unbalanceTime == null ? null : new AttendanceTime(unbalanceTime))))
						.build();
		if (occurrentClass == OccurrenceDigClass.DIGESTION) {
			return detail;
		}

		if (isDaikyu) {
			return new UnbalanceVacation(deadline, digestionCate, Optional.ofNullable(extinctionDate), detail,
					new AttendanceTime(timeOneDay), new AttendanceTime(timeHalfDay));
		} else {
			return new UnbalanceCompensation(detail, deadline, DigestionAtr.UNUSED, Optional.ofNullable(extinctionDate),
					StatutoryAtr.PUBLIC);
		}
	}

	public static AccumulationAbsenceDetail createDetail(boolean isDaikyu, OccurrenceDigClass occurrentClass,
			Optional<GeneralDate> dayoffDate, GeneralDate deadline, String manageId, Double occurrentDay,
			Integer occurrentTime, Double unbalanceDay, Integer unbalanceTime) {
		return createDetail(isDaikyu, occurrentClass, deadline, DigestionAtr.UNUSED, GeneralDate.max(),
				!dayoffDate.isPresent(), dayoffDate, MngDataStatus.RECORD, manageId, 480, 240, occurrentDay,
				occurrentTime, unbalanceDay, unbalanceTime);

	}
	
	public static AccumulationAbsenceDetail createDetailDefault(OccurrenceDigClass occurrentClass,
			Optional<GeneralDate> dayoffDate, String manageId) {
		return createDetailDefault(true, occurrentClass, dayoffDate, manageId, 0.0, 0, 0.0, 0);

	}

	public static AccumulationAbsenceDetail createDetailDefault(boolean isDaikyu, OccurrenceDigClass occurrentClass,
			Optional<GeneralDate> dayoffDate, String manageId, Double occurrentDay, Integer occurrentTime,
			Double unbalanceDay, Integer unbalanceTime) {
		AccumulationAbsenceDetail detail = new AccuVacationBuilder(SID,
				new CompensatoryDayoffDate(!dayoffDate.isPresent(), dayoffDate), occurrentClass, MngDataStatus.RECORD,
				manageId)
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(occurrentDay),
								Optional.ofNullable(occurrentTime == null ? null : new AttendanceTime(occurrentTime))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(unbalanceDay),
								Optional.ofNullable(unbalanceTime == null ? null : new AttendanceTime(unbalanceTime))))
						.build();
		if (occurrentClass == OccurrenceDigClass.DIGESTION) {
			return detail;
		}

		if (isDaikyu) {
			return new UnbalanceVacation(GeneralDate.max(), DigestionAtr.UNUSED, Optional.ofNullable(GeneralDate.max()),
					detail, new AttendanceTime(480), new AttendanceTime(240));
		} else {
			return new UnbalanceCompensation(detail, GeneralDate.max(), DigestionAtr.UNUSED,
					Optional.ofNullable(GeneralDate.max()), StatutoryAtr.PUBLIC);
		}
	}

	public static AccumulationAbsenceDetail createDetailDefaultUnba(boolean isDaikyu, OccurrenceDigClass occurrentClass,
			Optional<GeneralDate> dayoffDate, String manageId, Double unbalanceDay, Integer unbalanceTime) {

		return createDetailDefault(isDaikyu, occurrentClass, dayoffDate, manageId, 0.0, 0, unbalanceDay, unbalanceTime);
	}
	
	public static AccumulationAbsenceDetail createDetailDefault(boolean isDaikyu, OccurrenceDigClass occurrentClass,
			Optional<GeneralDate> dayoffDate, String manageId, GeneralDate deadline,
			Double unbalanceDay, Integer unbalanceTime) {
		return createDetailDefault(isDaikyu, occurrentClass, dayoffDate, manageId, DigestionAtr.UNUSED, deadline, unbalanceDay, unbalanceTime);
	}
	public static AccumulationAbsenceDetail createDetailDefault(boolean isDaikyu, OccurrenceDigClass occurrentClass,
			Optional<GeneralDate> dayoffDate, String manageId, DigestionAtr atr, GeneralDate deadline,
			Double unbalanceDay, Integer unbalanceTime) {
		AccumulationAbsenceDetail detail = new AccuVacationBuilder(SID,
				new CompensatoryDayoffDate(!dayoffDate.isPresent(), dayoffDate), occurrentClass, MngDataStatus.RECORD,
				manageId)
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
								Optional.ofNullable(new AttendanceTime(0))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(unbalanceDay),
								Optional.ofNullable(new AttendanceTime(unbalanceTime))))
						.build();
		if (occurrentClass == OccurrenceDigClass.DIGESTION) {
			return detail;
		}

		if (isDaikyu) {
			return new UnbalanceVacation(deadline, atr, Optional.ofNullable(GeneralDate.max()), detail,
					new AttendanceTime(480), new AttendanceTime(240));
		} else {
			return new UnbalanceCompensation(detail, deadline, atr, Optional.ofNullable(GeneralDate.max()),
					StatutoryAtr.PUBLIC);
		}
	}

	public static AccumulationAbsenceDetail createDetailDefault(boolean isDaikyu, OccurrenceDigClass occurrentClass,
			Optional<GeneralDate> dayoffDate, String manageId, Double occDay, Integer occTime) {
		AccumulationAbsenceDetail detail = new AccuVacationBuilder(SID,
				new CompensatoryDayoffDate(!dayoffDate.isPresent(), dayoffDate), occurrentClass, MngDataStatus.RECORD,
				manageId)
						.numberOccurren(new NumberConsecuVacation(new ManagementDataRemainUnit(occDay),
								Optional.ofNullable(new AttendanceTime(occTime))))
						.unbalanceNumber(new NumberConsecuVacation(new ManagementDataRemainUnit(0.0),
								Optional.ofNullable(new AttendanceTime(0))))
						.build();
		if (occurrentClass == OccurrenceDigClass.DIGESTION) {
			return detail;
		}

		if (isDaikyu) {
			return new UnbalanceVacation(GeneralDate.max(), DigestionAtr.UNUSED, Optional.ofNullable(GeneralDate.max()),
					detail, new AttendanceTime(480), new AttendanceTime(240));
		} else {
			return new UnbalanceCompensation(detail, GeneralDate.max(), DigestionAtr.UNUSED,
					Optional.ofNullable(GeneralDate.max()), StatutoryAtr.PUBLIC);
		}
	}

	public static SubstituteHolidayAggrResult createBeforeResult(List<AccumulationAbsenceDetail> lstAccDetail,
			Double remainDay, Integer remainTime, Double dayUse, Integer timeUse, Double occurrenceDay,
			Integer occurrenceTime, Double carryoverDay, Integer carryoverTime, Double unusedDay, Integer unusedTime,
			List<DayOffError> dayOffErrors, GeneralDate nextDay) {
		return new SubstituteHolidayAggrResult(new VacationDetails(lstAccDetail),
				new ReserveLeaveRemainingDayNumber(remainDay), new RemainingMinutes(remainTime),
				new ReserveLeaveRemainingDayNumber(dayUse), new RemainingMinutes(timeUse),
				new ReserveLeaveRemainingDayNumber(occurrenceDay), new RemainingMinutes(occurrenceTime),
				new ReserveLeaveRemainingDayNumber(carryoverDay), new RemainingMinutes(carryoverTime),
				new ReserveLeaveRemainingDayNumber(unusedDay), new RemainingMinutes(unusedTime), dayOffErrors,
				Finally.of(nextDay), Collections.emptyList());
	}

	public static SubstituteHolidayAggrResult createDefaultResult(List<AccumulationAbsenceDetail> lstAccDetail,
			GeneralDate nextDay) {
		return createDefaultResult(lstAccDetail, 0.0, nextDay);
	}
	
	public static SubstituteHolidayAggrResult createDefaultResult(List<AccumulationAbsenceDetail> lstAccDetail, Double carryDay,
			GeneralDate nextDay) {
		return new SubstituteHolidayAggrResult(new VacationDetails(lstAccDetail),
				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(carryDay), new RemainingMinutes(0),
				new ReserveLeaveRemainingDayNumber(0.0), new RemainingMinutes(0), new ArrayList<>(),
				Finally.of(nextDay), Collections.emptyList());
	}

	public static BreakDayOffRemainMngRefactParam inputParamDaikyu(DatePeriod dateData, boolean mode,
			GeneralDate screenDisplayDate, boolean replaceChk, List<InterimRemain> interimMng, CreateAtr creatorAtr,
			DatePeriod processDate, List<InterimBreakMng> breakMng, List<InterimDayOffMng> dayOffMng,
			Optional<SubstituteHolidayAggrResult> optBeforeResult, FixedManagementDataMonth fixManaDataMonth) {
		return new BreakDayOffRemainMngRefactParam(CID, SID, dateData, mode, screenDisplayDate, replaceChk, interimMng,
				Optional.ofNullable(creatorAtr), Optional.ofNullable(processDate), breakMng, dayOffMng, optBeforeResult,
				fixManaDataMonth);
	}

	public static BreakDayOffRemainMngRefactParam inputParamDaikyu(DatePeriod dateData, boolean mode,
			GeneralDate screenDisplayDate, boolean replaceChk, List<InterimRemain> interimMng,
			List<InterimBreakMng> breakMng, List<InterimDayOffMng> dayOffMng,
			Optional<SubstituteHolidayAggrResult> optBeforeResult, FixedManagementDataMonth fixManaDataMonth) {
		return new BreakDayOffRemainMngRefactParam(CID, SID, dateData, mode, screenDisplayDate, replaceChk, interimMng,
				Optional.ofNullable(CreateAtr.RECORD), Optional.ofNullable(null), breakMng, dayOffMng, optBeforeResult,
				fixManaDataMonth);
	}

	public static InterimDayOffMng createDayOff(String id, int requireTime, double requireDay) {
		return new InterimDayOffMng(id, new RequiredTime(requireTime), new RequiredDay(requireDay),
				new UnOffsetTime(1020), new UnOffsetDay(1.0));
	}

	public static InterimBreakMng createBreak(String id, GeneralDate deadline, int unUseTime, double unUseDay) {

		return new InterimBreakMng(id, new AttendanceTime(480), deadline, new OccurrenceTime(480),
				new OccurrenceDay(1.0), new AttendanceTime(240), new UnUsedTime(unUseTime), new UnUsedDay(unUseDay));
	}

	public static InterimRemain createRemain(String id, GeneralDate date, CreateAtr createBy, RemainType type) {
		return new InterimRemain(id, SID, date, createBy, type, RemainAtr.SINGLE);
	}
	
	public static InterimAbsMng createAbsMng(String id, double requireDay) {
		return new InterimAbsMng(id, new RequiredDay(requireDay), new UnOffsetDay(1.0));
	}
	
	public static InterimRecMng createRecMng(String id, GeneralDate deadline, double occDay) {
		return new InterimRecMng(id, deadline, new OccurrenceDay(occDay), StatutoryAtr.PUBLIC, new UnUsedDay(1.0));
	}
	
	public static InterimRecMng createRecUseMng(String id, GeneralDate deadline, double unuse) {
		return new InterimRecMng(id, deadline, new OccurrenceDay(1.0), StatutoryAtr.PUBLIC, new UnUsedDay(unuse));
	}

	public static AbsRecMngInPeriodRefactParamInput createAbsRecInput(DatePeriod period, GeneralDate dateRefer,
			boolean mode, boolean replaceChk, List<InterimAbsMng> useAbsMng, List<InterimRemain> interimMng,
			List<InterimRecMng> useRecMng) {
		return new AbsRecMngInPeriodRefactParamInput(CID, SID, period, dateRefer, mode, replaceChk, useAbsMng,
				interimMng, useRecMng, Optional.empty(), Optional.empty(), Optional.empty(),
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));
	}
	
	public static AbsRecMngInPeriodRefactParamInput createAbsRecInput(DatePeriod period, GeneralDate dateRefer,
			boolean mode, boolean replaceChk, Optional<CompenLeaveAggrResult> optBeforeResult) {
		return new AbsRecMngInPeriodRefactParamInput(CID, SID, period, dateRefer, mode, replaceChk, new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), optBeforeResult, Optional.empty(), Optional.empty(),
				new FixedManagementDataMonth(new ArrayList<>(), new ArrayList<>()));
	}

	public static LeaveComDayOffManagement createLeavComDayOff(GeneralDate occDate, GeneralDate digestDate,
			double usedDays) {
		return new LeaveComDayOffManagement(SID, occDate, digestDate, usedDays, TargetSelectionAtr.MANUAL.value);
	}
	
	public static PayoutSubofHDManagement createHD(GeneralDate occDate, GeneralDate digestDate,
			double usedDays) {
		return new PayoutSubofHDManagement(SID, occDate, digestDate, usedDays, TargetSelectionAtr.MANUAL.value);
	}
}