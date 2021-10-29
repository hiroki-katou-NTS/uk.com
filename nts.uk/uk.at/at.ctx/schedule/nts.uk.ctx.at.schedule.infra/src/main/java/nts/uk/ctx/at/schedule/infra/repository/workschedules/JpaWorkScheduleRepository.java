package nts.uk.ctx.at.schedule.infra.repository.workschedules;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.requestperiodchange.AffInfoForWorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule.TaskSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchAtdLvwTime;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchAtdLvwTimePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBasicInfo;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBasicInfoPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBonusPay;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBonusPayPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBreakTs;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchBreakTsPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchComeLate;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchComeLatePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchEditState;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchEditStatePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchGoingOut;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchGoingOutPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchGoingOutTs;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchGoingOutTsPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchHolidayWork;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchHolidayWorkPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchLeaveEarly;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchLeaveEarlyPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchOvertimeWork;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchOvertimeWorkPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchPremium;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchPremiumPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchShortTime;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchShortTimePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchShortTimeTs;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedule.KscdtSchShortTimeTsPK;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.DayOfWeek;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.UsedDays;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.ExcessOfStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.WithinStatutoryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.AffiliationInforOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.TimeLeavingWork;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.attendancetime.WorkTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeGoOutTimes;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.BreakTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakgoout.OutingTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.OutingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.breaking.BreakTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.DeductionTotalTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeActualStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeDivergenceWithCalculationMinusExist;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.TimeWithCalculation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.ReasonTimeChange;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.TimeChangeMeans;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkStamp;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkTimeInformation;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DivergenceTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.earlyleavetime.LeaveEarlyTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.editstate.EditStateSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.interval.IntervalTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.latetime.LateTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.ExcessOverTimeWorkMidNightTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.FlexTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.overtimehours.clearovertime.OverTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.paytime.RaiseSalaryTimeOfDailyPerfor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.premiumtime.PremiumTimeOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.secondorder.medical.MedicalCareTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortTimeOfDailyAttd;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.shortworktime.ShortWorkingTimeSheet;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.temporarytime.TemporaryTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.AbsenceOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.AnnualOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.HolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.OverSalaryOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.SpecialHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.SubstituteHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.TimeDigestOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.TransferHolidayOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.vacationusetime.YearlyReservedOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.FuriClassifi;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NotUseAttribute;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.NumberOfDaySuspension;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workschedule.WorkScheduleTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ActualWorkingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.AttendanceTimeOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.ConstraintTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.StayingTimeOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worktime.TotalWorkingTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.IntervalExemptionTime;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.WithinOutingTotalTime;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkNo;
import nts.uk.ctx.at.shared.dom.worktime.predset.WorkTimeNightShift;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.AttendanceClock;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class JpaWorkScheduleRepository extends JpaRepository implements WorkScheduleRepository {

	private static final String SELECT_BY_KEY = "SELECT c FROM KscdtSchBasicInfo c WHERE c.pk.sid = :employeeID AND c.pk.ymd = :ymd";

	private static final String SELECT_BY_LIST = "SELECT c FROM KscdtSchBasicInfo c WHERE c.pk.sid IN :sids AND (c.pk.ymd >= :startDate AND c.pk.ymd <= :endDate) ";

	private static final String SELECT_CHECK_UPDATE = "SELECT count (c) FROM KscdtSchBasicInfo c WHERE c.pk.sid = :employeeID AND c.pk.ymd = :ymd";

	private static final String WHERE_PK = "WHERE a.pk.sid = :sid AND a.pk.ymd >= :ymdStart AND a.pk.ymd <= :ymdEnd";

	private static final String DELETE_BY_LIST_DATE = "WHERE a.pk.sid = :sid AND a.pk.ymd IN :ymds";

//	private static final String SELECT_MAX = "SELECT MAX(c.startDate) FROM KscdtSchBasicInfo c WHERE c.pk.sid IN :employeeIDs";

//	private static final String GET_MAX_DATE_WORK_SCHE_BY_LIST_EMP = "SELECT c.pk.ymd FROM KscdtSchBasicInfo c "
//			+ " WHERE c.pk.sid IN :listEmp"
//			+ " ORDER BY c.pk.ymd desc ";

	private static final List<String> DELETE_TABLES = Arrays.asList("DELETE FROM KscdtSchTime a ",
			"DELETE FROM KscdtSchOvertimeWork a ", "DELETE FROM KscdtSchHolidayWork a ",
			"DELETE FROM KscdtSchBonusPay a ", "DELETE FROM KscdtSchPremium a ", "DELETE FROM KscdtSchShortTime a ",
			"DELETE FROM KscdtSchBasicInfo a ", "DELETE FROM KscdtSchEditState a ", "DELETE FROM KscdtSchAtdLvwTime a ",
			"DELETE FROM KscdtSchShortTimeTs a ", "DELETE FROM KscdtSchBreakTs a ", "DELETE FROM KscdtSchComeLate a ",
			"DELETE FROM KscdtSchGoingOut a ", "DELETE FROM KscdtSchLeaveEarly a ");

	@Override
	public Optional<WorkSchedule> get(String employeeID, GeneralDate ymd) {
		Optional<WorkSchedule> workSchedule = this.queryProxy().query(SELECT_BY_KEY, KscdtSchBasicInfo.class)
				.setParameter("employeeID", employeeID).setParameter("ymd", ymd)
				.getSingle(c -> c.toDomain(employeeID, ymd));
		return workSchedule;
	}

//	@Override
//	public Optional<GeneralDate> getMaxDate(List<String> employeeIDs, GeneralDate ymd) {
//		GeneralDate date = this.queryProxy().query(SELECT_MAX, GeneralDate.class)
//				.setParameter("employeeIDs", employeeIDs)
//				.getSingleOrNull();
//		return Optional.ofNullable(date);
//	}

	@Override
	public List<WorkSchedule> getList(List<String> sids, DatePeriod period) {
		if (sids.isEmpty())
			return new ArrayList<>();

		List<WorkSchedule> result = this.queryProxy().query(SELECT_BY_LIST, KscdtSchBasicInfo.class)
				.setParameter("sids", sids).setParameter("startDate", period.start())
				.setParameter("endDate", period.end()).getList(c -> c.toDomain(c.pk.sid, c.pk.ymd));
		return result;
	}

	@Override
	public boolean checkExits(String employeeID, GeneralDate ymd) {
		return this.queryProxy().query(SELECT_CHECK_UPDATE, Long.class).setParameter("employeeID", employeeID)
				.setParameter("ymd", ymd).getSingle().get() > 0;
	}

	@Override
	public void insert(WorkSchedule workSchedule) {
		String cID = AppContexts.user().companyId();
		this.commandProxy().insert(this.toEntity(workSchedule, cID));
	}

	@Override
	public void insertAll(String cID, List<WorkSchedule> workSchedules) {
		this.commandProxy()
				.insertAll(workSchedules.stream().map(s -> this.toEntity(s, cID)).collect(Collectors.toList()));
	}

	public KscdtSchBasicInfo toEntity(WorkSchedule workSchedule, String cID) {
		return KscdtSchBasicInfo.toEntity(workSchedule, cID);
	}

	@Override
	public void update(WorkSchedule workSchedule) {
		String cID = AppContexts.user().companyId();
		/*
		 * Optional<KscdtSchBasicInfo> oldData = this.queryProxy().query(SELECT_BY_KEY,
		 * KscdtSchBasicInfo.class) .setParameter("employeeID",
		 * workSchedule.getEmployeeID()).setParameter("ymd", workSchedule.getYmd())
		 * .getSingle(c -> c);
		 */
		KscdtSchBasicInfo entity = KscdtSchBasicInfo.toEntity(workSchedule, cID);
		Optional<KscdtSchBasicInfo> oldData = this.queryProxy().find(entity.getPk(), KscdtSchBasicInfo.class);
		if (oldData.isPresent()) {
			KscdtSchBasicInfo newData = KscdtSchBasicInfo.toEntity(workSchedule, cID);
			oldData.get().confirmedATR = newData.confirmedATR;
			oldData.get().empCd = newData.empCd;
			oldData.get().jobId = newData.jobId;
			oldData.get().wkpId = newData.wkpId;
			oldData.get().clsCd = newData.clsCd;
			oldData.get().busTypeCd = newData.busTypeCd;
			oldData.get().nurseLicense = newData.nurseLicense;
			oldData.get().wktpCd = newData.wktpCd;
			oldData.get().wktmCd = newData.wktmCd;
			oldData.get().goStraightAtr = newData.goStraightAtr;
			oldData.get().backStraightAtr = newData.backStraightAtr;
			oldData.get().treatAsSubstituteAtr = newData.treatAsSubstituteAtr;
			oldData.get().treatAsSubstituteDays = newData.treatAsSubstituteDays;

			// kscdtSchTime
			if (oldData.get().kscdtSchTime != null) {
				oldData.get().kscdtSchTime.cid = newData.kscdtSchTime.cid;
				oldData.get().kscdtSchTime.count = newData.kscdtSchTime.count;
				oldData.get().kscdtSchTime.totalTime = newData.kscdtSchTime.totalTime;
				oldData.get().kscdtSchTime.totalTimeAct = newData.kscdtSchTime.totalTimeAct;
				oldData.get().kscdtSchTime.prsWorkTime = newData.kscdtSchTime.prsWorkTime;
				oldData.get().kscdtSchTime.prsWorkTimeAct = newData.kscdtSchTime.prsWorkTimeAct;
				oldData.get().kscdtSchTime.prsPrimeTime = newData.kscdtSchTime.prsPrimeTime;
				oldData.get().kscdtSchTime.prsMidniteTime = newData.kscdtSchTime.prsMidniteTime;
				oldData.get().kscdtSchTime.extBindTimeOtw = newData.kscdtSchTime.extBindTimeOtw;
				oldData.get().kscdtSchTime.extBindTimeHw = newData.kscdtSchTime.extBindTimeHw;
				oldData.get().kscdtSchTime.extVarwkOtwTimeLegal = newData.kscdtSchTime.extVarwkOtwTimeLegal;
				oldData.get().kscdtSchTime.extFlexTime = newData.kscdtSchTime.extFlexTime;
				oldData.get().kscdtSchTime.extFlexTimePreApp = newData.kscdtSchTime.extFlexTimePreApp;
				oldData.get().kscdtSchTime.extMidNiteOtwTime = newData.kscdtSchTime.extMidNiteOtwTime;
				oldData.get().kscdtSchTime.extMidNiteHdwTimeLghd = newData.kscdtSchTime.extMidNiteHdwTimeLghd;
				oldData.get().kscdtSchTime.extMidNiteHdwTimeIlghd = newData.kscdtSchTime.extMidNiteHdwTimeIlghd;
				oldData.get().kscdtSchTime.extMidNiteHdwTimePubhd = newData.kscdtSchTime.extMidNiteHdwTimePubhd;
				oldData.get().kscdtSchTime.extMidNiteTotal = newData.kscdtSchTime.extMidNiteTotal;
				oldData.get().kscdtSchTime.extMidNiteTotalPreApp = newData.kscdtSchTime.extMidNiteTotalPreApp;
				oldData.get().kscdtSchTime.intervalAtdClock = newData.kscdtSchTime.intervalAtdClock;
				oldData.get().kscdtSchTime.intervalTime = newData.kscdtSchTime.intervalTime;
				oldData.get().kscdtSchTime.brkTotalTime = newData.kscdtSchTime.brkTotalTime;
				oldData.get().kscdtSchTime.hdPaidTime = newData.kscdtSchTime.hdPaidTime;
				oldData.get().kscdtSchTime.hdPaidHourlyTime = newData.kscdtSchTime.hdPaidHourlyTime;
				oldData.get().kscdtSchTime.hdComTime = newData.kscdtSchTime.hdComTime;
				oldData.get().kscdtSchTime.hdComHourlyTime = newData.kscdtSchTime.hdComHourlyTime;
				oldData.get().kscdtSchTime.hd60hTime = newData.kscdtSchTime.hd60hTime;
				oldData.get().kscdtSchTime.hd60hHourlyTime = newData.kscdtSchTime.hd60hHourlyTime;
				oldData.get().kscdtSchTime.hdspTime = newData.kscdtSchTime.hdspTime;
				oldData.get().kscdtSchTime.hdspHourlyTime = newData.kscdtSchTime.hdspHourlyTime;
				oldData.get().kscdtSchTime.hdstkTime = newData.kscdtSchTime.hdstkTime;
				oldData.get().kscdtSchTime.hdHourlyTime = newData.kscdtSchTime.hdHourlyTime;
				oldData.get().kscdtSchTime.hdHourlyShortageTime = newData.kscdtSchTime.hdHourlyShortageTime;
				oldData.get().kscdtSchTime.absenceTime = newData.kscdtSchTime.absenceTime;
				oldData.get().kscdtSchTime.vacationAddTime = newData.kscdtSchTime.vacationAddTime;
				oldData.get().kscdtSchTime.staggeredWhTime = newData.kscdtSchTime.staggeredWhTime;
			}

			if (oldData.get().kscdtSchTime != null) {
				// List<KscdtSchOvertimeWork> overtimeWorks
				if (!oldData.get().kscdtSchTime.overtimeWorks.isEmpty()) {
					for (KscdtSchOvertimeWork y : newData.kscdtSchTime.overtimeWorks) {
						oldData.get().kscdtSchTime.overtimeWorks.forEach(x -> {
							if (y.pk.frameNo == x.pk.frameNo) {
								x.cid = y.cid;
								x.overtimeWorkTime = y.overtimeWorkTime;
								x.overtimeWorkTimeTrans = y.overtimeWorkTimeTrans;
								x.overtimeWorkTimePreApp = y.getOvertimeWorkTimePreApp();
							}
						});
					}
				}

				// List<KscdtSchHolidayWork> holidayWorks
				if (!oldData.get().kscdtSchTime.holidayWorks.isEmpty()) {
					for (KscdtSchHolidayWork y : newData.kscdtSchTime.holidayWorks) {
						oldData.get().kscdtSchTime.holidayWorks.forEach(x -> {
							if (y.pk.frameNo == x.pk.frameNo) {
								x.cid = y.cid;
								x.holidayWorkTsStart = y.holidayWorkTsStart;
								x.holidayWorkTsEnd = y.holidayWorkTsEnd;
								x.holidayWorkTime = y.holidayWorkTime;
								x.holidayWorkTimeTrans = y.holidayWorkTimeTrans;
								x.holidayWorkTimePreApp = y.holidayWorkTimePreApp;
							}
						});
					}
				}

				// List<KscdtSchBonusPay> bonusPays
				if (!oldData.get().kscdtSchTime.bonusPays.isEmpty()) {
					for (KscdtSchBonusPay y : newData.kscdtSchTime.bonusPays) {
						oldData.get().kscdtSchTime.bonusPays.forEach(x -> {
							if (y.pk.frameNo == x.pk.frameNo && y.pk.bonuspayType == x.pk.bonuspayType) {
								x.cid = y.cid;
								x.premiumTime = y.premiumTime;
								x.premiumTimeWithIn = y.premiumTimeWithIn;
								x.premiumTimeWithOut = y.getPremiumTimeWithOut();
							}
						});
					}
				}

				// List<KscdtSchPremium> premiums
				if (!oldData.get().kscdtSchTime.premiums.isEmpty()) {
					for (KscdtSchPremium y : newData.kscdtSchTime.premiums) {
						oldData.get().kscdtSchTime.premiums.forEach(x -> {
							if (y.pk.frameNo == x.pk.frameNo) {
								x.cid = y.cid;
								x.premiumTime = y.premiumTime;
							}
						});
					}
				}

				// List<KscdtSchShortTime> shortTimes
				if (!oldData.get().kscdtSchTime.shortTimes.isEmpty()) {
					for (KscdtSchShortTime y : newData.kscdtSchTime.shortTimes) {
						oldData.get().kscdtSchTime.shortTimes.forEach(x -> {
							if (y.pk.childCareAtr == x.pk.childCareAtr) {
								x.cid = y.cid;
								x.count = y.count;
								x.totalTime = y.totalTime;
								x.totalTimeWithIn = y.totalTimeWithIn;
								x.totalTimeWithOut = y.getTotalTimeWithOut();
							}
						});
					}
				}
				///

				// #114431
				// KSCDT_SCH_COME_LATE
				if (!oldData.get().kscdtSchTime.kscdtSchComeLate.isEmpty()) {
					// get list insert and update data exist
					List<KscdtSchComeLate> listInsert = new ArrayList<>();
					for (KscdtSchComeLate schComeLate : newData.kscdtSchTime.kscdtSchComeLate) {
						boolean checkExist = false;
						for (KscdtSchComeLate schComeLateOld : oldData.get().kscdtSchTime.kscdtSchComeLate) {
							if (schComeLate.pk.workNo == schComeLateOld.pk.workNo
									&& schComeLate.pk.sid.equals(schComeLateOld.pk.sid)
									&& schComeLate.pk.ymd.equals(schComeLateOld.pk.ymd)) {
								schComeLateOld.useHourlyHdPaid = schComeLate.useHourlyHdPaid;
								schComeLateOld.useHourlyHdCom = schComeLate.useHourlyHdCom;
								schComeLateOld.useHourlyHd60h = schComeLate.useHourlyHd60h;
								schComeLateOld.useHourlyHdSpNO = schComeLate.useHourlyHdSpNO;
								schComeLateOld.useHourlyHdSpTime = schComeLate.useHourlyHdSpTime;
								schComeLateOld.useHourlyHdChildCare = schComeLate.useHourlyHdChildCare;
								schComeLateOld.useHourlyHdNurseCare = schComeLate.useHourlyHdNurseCare;
								checkExist = true;
							}
						}

						if (!checkExist) {
							listInsert.add(schComeLate);
						}
					}
					// get list remove
					List<KscdtSchComeLate> listRemove = new ArrayList<>();
					for (KscdtSchComeLate schComeLateOld : oldData.get().kscdtSchTime.kscdtSchComeLate) {
						boolean checkExist = false;
						for (KscdtSchComeLate schComeLate : newData.kscdtSchTime.kscdtSchComeLate) {
							if (schComeLate.pk.workNo == schComeLateOld.pk.workNo
									&& schComeLate.pk.sid.equals(schComeLateOld.pk.sid)
									&& schComeLate.pk.ymd.equals(schComeLateOld.pk.ymd)) {
								checkExist = true;
								break;
							}
						}
						if (!checkExist) {
							listRemove.add(schComeLateOld);
						}
					}

					// remove
					String delete = "delete from KscdtSchComeLate o " + " where o.pk.sid = :sid "
							+ " and o.pk.ymd = :ymd " + " and o.pk.workNo = :workNo";
					for (KscdtSchComeLate sle : listRemove) {
						this.getEntityManager().createQuery(delete).setParameter("sid", sle.pk.sid)
								.setParameter("ymd", sle.pk.ymd).setParameter("workNo", sle.pk.workNo).executeUpdate();
					}
					// add
					for (KscdtSchComeLate sle : listInsert) {
						this.commandProxy().insert(sle);
					}

				} else {
					oldData.get().kscdtSchTime.kscdtSchComeLate = newData.kscdtSchTime.kscdtSchComeLate;
				}

				// KSCDT_SCH_GOING_OUT
				if (!oldData.get().kscdtSchTime.kscdtSchGoingOut.isEmpty()) {
					// get list insert and update data exist
					List<KscdtSchGoingOut> listInsert = new ArrayList<>();
					for (KscdtSchGoingOut schGoingOut : newData.kscdtSchTime.kscdtSchGoingOut) {
						boolean checkExist = false;
						for (KscdtSchGoingOut schGoingOutOld : oldData.get().kscdtSchTime.kscdtSchGoingOut) {
							if (schGoingOut.pk.reasonAtr == schGoingOutOld.pk.reasonAtr
									&& schGoingOut.pk.sid.equals(schGoingOutOld.pk.sid)
									&& schGoingOut.pk.ymd.equals(schGoingOutOld.pk.ymd)) {
								schGoingOutOld.useHourlyHdPaid = schGoingOut.useHourlyHdPaid;
								schGoingOutOld.useHourlyHdCom = schGoingOut.useHourlyHdCom;
								schGoingOutOld.useHourlyHd60h = schGoingOut.useHourlyHd60h;
								schGoingOutOld.useHourlyHdSpNO = schGoingOut.useHourlyHdSpNO;
								schGoingOutOld.useHourlyHdSpTime = schGoingOut.useHourlyHdSpTime;
								schGoingOutOld.useHourlyHdChildCare = schGoingOut.useHourlyHdChildCare;
								schGoingOutOld.useHourlyHdNurseCare = schGoingOut.useHourlyHdNurseCare;
								checkExist = true;
							}
						}

						if (!checkExist) {
							listInsert.add(schGoingOut);
						}
					}
					// get list remove
					List<KscdtSchGoingOut> listRemove = new ArrayList<>();
					for (KscdtSchGoingOut schGoingOutOld : oldData.get().kscdtSchTime.kscdtSchGoingOut) {
						boolean checkExist = false;
						for (KscdtSchGoingOut schGoingOut : newData.kscdtSchTime.kscdtSchGoingOut) {
							if (schGoingOut.pk.reasonAtr == schGoingOutOld.pk.reasonAtr
									&& schGoingOut.pk.sid.equals(schGoingOutOld.pk.sid)
									&& schGoingOut.pk.ymd.equals(schGoingOutOld.pk.ymd)) {
								checkExist = true;
								break;
							}
						}
						if (!checkExist) {
							listRemove.add(schGoingOutOld);
						}
					}

					// remove
					String delete = "delete from KscdtSchGoingOut o " + " where o.pk.sid = :sid "
							+ " and o.pk.ymd = :ymd " + " and o.pk.reasonAtr = :reasonAtr";
					for (KscdtSchGoingOut sgo : listRemove) {
						this.getEntityManager().createQuery(delete).setParameter("sid", sgo.pk.sid)
								.setParameter("ymd", sgo.pk.ymd).setParameter("reasonAtr", sgo.pk.reasonAtr)
								.executeUpdate();
					}
					// add
					for (KscdtSchGoingOut sgo : listInsert) {
						this.commandProxy().insert(sgo);
					}

				} else {
					oldData.get().kscdtSchTime.kscdtSchGoingOut = newData.kscdtSchTime.kscdtSchGoingOut;
				}

				// kscdtSchLeaveEarly
				if (!oldData.get().kscdtSchTime.kscdtSchLeaveEarly.isEmpty()) {
					// get list insert and update data exist
					List<KscdtSchLeaveEarly> listInsert = new ArrayList<>();
					for (KscdtSchLeaveEarly schLeaveEarly : newData.kscdtSchTime.kscdtSchLeaveEarly) {
						boolean checkExist = false;
						for (KscdtSchLeaveEarly schLeaveEarlyOld : oldData.get().kscdtSchTime.kscdtSchLeaveEarly) {
							if (schLeaveEarly.pk.workNo == schLeaveEarlyOld.pk.workNo
									&& schLeaveEarly.pk.sid.equals(schLeaveEarlyOld.pk.sid)
									&& schLeaveEarly.pk.ymd.equals(schLeaveEarlyOld.pk.ymd)) {
								schLeaveEarlyOld.useHourlyHdPaid = schLeaveEarly.useHourlyHdPaid;
								schLeaveEarlyOld.useHourlyHdCom = schLeaveEarly.useHourlyHdCom;
								schLeaveEarlyOld.useHourlyHd60h = schLeaveEarly.useHourlyHd60h;
								schLeaveEarlyOld.useHourlyHdSpNO = schLeaveEarly.useHourlyHdSpNO;
								schLeaveEarlyOld.useHourlyHdSpTime = schLeaveEarly.useHourlyHdSpTime;
								schLeaveEarlyOld.useHourlyHdChildCare = schLeaveEarly.useHourlyHdChildCare;
								schLeaveEarlyOld.useHourlyHdNurseCare = schLeaveEarly.useHourlyHdNurseCare;
								checkExist = true;
							}
						}

						if (!checkExist) {
							listInsert.add(schLeaveEarly);
						}
					}
					// get list remove
					List<KscdtSchLeaveEarly> listRemove = new ArrayList<>();
					for (KscdtSchLeaveEarly schLeaveEarlyOld : oldData.get().kscdtSchTime.kscdtSchLeaveEarly) {
						boolean checkExist = false;
						for (KscdtSchLeaveEarly schLeaveEarly : newData.kscdtSchTime.kscdtSchLeaveEarly) {
							if (schLeaveEarly.pk.workNo == schLeaveEarlyOld.pk.workNo
									&& schLeaveEarly.pk.sid.equals(schLeaveEarlyOld.pk.sid)
									&& schLeaveEarly.pk.ymd.equals(schLeaveEarlyOld.pk.ymd)) {
								checkExist = true;
								break;
							}
						}
						if (!checkExist) {
							listRemove.add(schLeaveEarlyOld);
						}
					}

					// remove
					String delete = "delete from KscdtSchLeaveEarly o " + " where o.pk.sid = :sid "
							+ " and o.pk.ymd = :ymd " + " and o.pk.workNo = :workNo";
					for (KscdtSchLeaveEarly sle : listRemove) {
						this.getEntityManager().createQuery(delete).setParameter("sid", sle.pk.sid)
								.setParameter("ymd", sle.pk.ymd).setParameter("workNo", sle.pk.workNo).executeUpdate();
					}
					// add
					for (KscdtSchLeaveEarly sle : listInsert) {
						this.commandProxy().insert(sle);
					}

				} else {
					oldData.get().kscdtSchTime.kscdtSchLeaveEarly = newData.kscdtSchTime.kscdtSchLeaveEarly;
				}
			}

//			if (!oldData.get().kscdtSchTime.kscdtSchTask.isEmpty()) {
//				// get list insert and update data exist
//				List<KscdtSchTask> listInsert = new ArrayList<>();
//				for (KscdtSchTask task : newData.kscdtSchTime.kscdtSchTask) {
//					List<KscdtSchTask> checkLst = new ArrayList<>();
//					oldData.get().kscdtSchTime.kscdtSchTask.forEach(x -> {
//						if(task.pk.sid.equals(x.pk.sid)
//								&& task.pk.ymd.equals(x.pk.ymd)
//								&& task.pk.serialNo == x.pk.serialNo) {
//							x.taskCode = task.taskCode;
//							x.startClock = task.startClock;
//							x.endClock = task.endClock;
//							checkLst.add(x);
//						} 
//					});
//					if(checkLst.isEmpty()) {
//						listInsert.add(task);
//					}
//				}

//				List<KscdtSchTask> listRemove = new ArrayList<>();
//				for (KscdtSchTask taskOld : oldData.get().kscdtSchTime.kscdtSchTask) {
//					boolean checkExist = false;
//					for (KscdtSchTask task : newData.kscdtSchTime.kscdtSchTask) {
//						if(task.pk.serialNo == taskOld.pk.serialNo
//								&& task.pk.sid.equals(taskOld.pk.sid)
//								&& task.pk.ymd.equals(taskOld.pk.ymd)
//								) {
//							checkExist = true;
//							break;
//						}
//					}
//					if(!checkExist) {
//						listRemove.add(taskOld);
//					}
//				}
//				
//				//remove
//				String delete = "delete from KscdtSchTask o " + " where o.pk.sid = :sid "
//						+ " and o.pk.ymd = :ymd " + " and o.pk.serialNo = :serialNo";
//				for(KscdtSchTask sle : listRemove) {
//					this.getEntityManager().createQuery(delete).setParameter("sid", sle.pk.sid)
//					.setParameter("ymd", sle.pk.ymd)
//					.setParameter("serialNo", sle.pk.serialNo).executeUpdate();
//				}
//				//add
//				for(KscdtSchTask sle : listInsert) {
//					this.commandProxy().insert(sle);
//				}
//				
//			} else {
//				oldData.get().kscdtSchTime.kscdtSchTask = newData.kscdtSchTime.kscdtSchTask;
//			}
//			

			// List<KscdtSchEditState> editStates;
			if (!oldData.get().editStates.isEmpty()) {
				// get list insert and update data exist
				List<KscdtSchEditState> listInsert = new ArrayList<>();
				for (KscdtSchEditState schState : newData.editStates) {
					List<KscdtSchEditState> checkLst = new ArrayList<>();
					oldData.get().editStates.forEach(x -> {
						if (schState.pk.sid.equals(x.pk.sid) && schState.pk.ymd.equals(x.pk.ymd)
								&& schState.pk.atdItemId == x.pk.atdItemId) {
							x.sditState = schState.sditState;
							x.cid = schState.cid;
							checkLst.add(x);
						}
					});
					if (checkLst.isEmpty()) {
						listInsert.add(schState);
					}
				}
				
				List<KscdtSchEditState> listRemove = new ArrayList<>();
				for (KscdtSchEditState editOld : oldData.get().editStates) {
					boolean checkExist = false;
					for (KscdtSchEditState edit : newData.editStates) {
						if (edit.pk.atdItemId == editOld.pk.atdItemId && edit.pk.sid.equals(editOld.pk.sid)
								&& edit.pk.ymd.equals(editOld.pk.ymd)) {
							checkExist = true;
							break;
						}
					}
					if (!checkExist) {
						listRemove.add(editOld);
					}
				}

				// remove
				String delete = "delete from KscdtSchEditState o " + " where o.pk.sid = :sid "
						+ " and o.pk.ymd = :ymd " + " and o.pk.atdItemId = :atdItemId";
				for (KscdtSchEditState sle : listRemove) {
					this.getEntityManager().createQuery(delete).setParameter("sid", sle.pk.sid)
							.setParameter("ymd", sle.pk.ymd).setParameter("atdItemId", sle.pk.atdItemId).executeUpdate();
				}
				
				// add
				for (KscdtSchEditState sle : listInsert) {
					this.commandProxy().insert(sle);
				}

			} else {
				oldData.get().editStates = newData.editStates;
			}

			// List<KscdtSchAtdLvwTime> atdLvwTimes;
			if (!oldData.get().atdLvwTimes.isEmpty()) {
				// get list insert and update data exist
				List<KscdtSchAtdLvwTime> listInsert = new ArrayList<>();
				for (KscdtSchAtdLvwTime atdLvw : newData.atdLvwTimes) {
					List<KscdtSchAtdLvwTime> checkLst = new ArrayList<>();
					oldData.get().atdLvwTimes.forEach(x -> {
						// Update data
						if (atdLvw.pk.workNo == x.pk.workNo) {
							x.cid = atdLvw.cid;
							x.atdClock = atdLvw.atdClock;
							x.lwkClock = atdLvw.lwkClock;
							x.atdHourlyHDTSStart = atdLvw.atdHourlyHDTSStart;
							x.atdHourlyHDTSEnd = atdLvw.atdHourlyHDTSEnd;
							x.lvwHourlyHDTSStart = atdLvw.lvwHourlyHDTSStart;
							x.lvwHourlyHDTSEnd = atdLvw.lvwHourlyHDTSEnd;
							checkLst.add(x);
						}
					});
					if (checkLst.isEmpty()) {
						listInsert.add(atdLvw);
					}
				}

				List<KscdtSchAtdLvwTime> listRemove = new ArrayList<>();
				for (KscdtSchAtdLvwTime atdLvwOld : oldData.get().atdLvwTimes) {
					boolean checkExist = false;
					for (KscdtSchAtdLvwTime atdLvw : newData.atdLvwTimes) {
						if (atdLvw.pk.workNo == atdLvwOld.pk.workNo && atdLvw.pk.sid.equals(atdLvwOld.pk.sid)
								&& atdLvw.pk.ymd.equals(atdLvwOld.pk.ymd)) {
							checkExist = true;
							break;
						}
					}
					if (!checkExist) {
						listRemove.add(atdLvwOld);
					}
				}

				// remove
				String delete = "delete from KscdtSchAtdLvwTime o " + " where o.pk.sid = :sid "
						+ " and o.pk.ymd = :ymd " + " and o.pk.workNo = :workNo";
				for (KscdtSchAtdLvwTime sle : listRemove) {
					this.getEntityManager().createQuery(delete).setParameter("sid", sle.pk.sid)
							.setParameter("ymd", sle.pk.ymd).setParameter("workNo", sle.pk.workNo).executeUpdate();
				}
				// add
				for (KscdtSchAtdLvwTime sle : listInsert) {
					this.commandProxy().insert(sle);
				}

			} else {
				oldData.get().atdLvwTimes = newData.atdLvwTimes;
			}

			// List<KscdtSchShortTimeTs> schShortTimeTs
			if (!oldData.get().schShortTimeTs.isEmpty()) {
				// get list insert and update data exist
				List<KscdtSchShortTimeTs> listInsert = new ArrayList<>();
				for (KscdtSchShortTimeTs shortTs : newData.schShortTimeTs) {
					List<KscdtSchShortTimeTs> checkLst = new ArrayList<>();
					oldData.get().schShortTimeTs.forEach(x -> {
						// Update data
						if (x.pk.frameNo == shortTs.pk.frameNo && x.pk.childCareAtr == shortTs.pk.childCareAtr) {
							x.cid = shortTs.cid;
							x.shortTimeTsStart = shortTs.shortTimeTsStart;
							x.shortTimeTsEnd = shortTs.shortTimeTsEnd;
							checkLst.add(x);
						}
					});
					if (checkLst.isEmpty()) {
						listInsert.add(shortTs);
					}
				}

				List<KscdtSchShortTimeTs> listRemove = new ArrayList<>();
				for (KscdtSchShortTimeTs shortTsOld : oldData.get().schShortTimeTs) {
					boolean checkExist = false;
					for (KscdtSchShortTimeTs shortTs : newData.schShortTimeTs) {
						if (shortTs.pk.frameNo == shortTsOld.pk.frameNo && shortTs.pk.sid.equals(shortTsOld.pk.sid)
								&& shortTs.pk.ymd.equals(shortTsOld.pk.ymd)) {
							checkExist = true;
							break;
						}
					}
					if (!checkExist) {
						listRemove.add(shortTsOld);
					}
				}

				// remove
				String delete = "delete from KscdtSchShortTimeTs o " + " where o.pk.sid = :sid "
						+ " and o.pk.ymd = :ymd " + " and o.pk.frameNo = :frameNo";
				for (KscdtSchShortTimeTs sle : listRemove) {
					this.getEntityManager().createQuery(delete).setParameter("sid", sle.pk.sid)
							.setParameter("ymd", sle.pk.ymd).setParameter("frameNo", sle.pk.frameNo).executeUpdate();
				}
				// add
				for (KscdtSchShortTimeTs sle : listInsert) {
					this.commandProxy().insert(sle);
				}

			} else {
				oldData.get().schShortTimeTs = newData.schShortTimeTs;
			}

			// KscdtSchBreakTs
			if (!oldData.get().breakTs.isEmpty()) {
				// get list insert and update data exist
				List<KscdtSchBreakTs> listInsert = new ArrayList<>();
				for (KscdtSchBreakTs schBrk : newData.breakTs) {
					List<KscdtSchBreakTs> checkLst = new ArrayList<>();
					oldData.get().breakTs.forEach(x -> {
						if (schBrk.pk.sid.equals(x.pk.sid) && schBrk.pk.ymd.equals(x.pk.ymd)
								&& schBrk.pk.frameNo == x.pk.frameNo) {
							x.cid = schBrk.cid;
							x.breakTsStart = schBrk.breakTsStart;
							x.breakTsEnd = schBrk.breakTsEnd;
							checkLst.add(x);
						}
					});
					if (checkLst.isEmpty()) {
						listInsert.add(schBrk);
					}
				}

				// get list remove
				List<KscdtSchBreakTs> listRemove = new ArrayList<>();
				for (KscdtSchBreakTs schBrkTsOld : oldData.get().breakTs) {
					boolean checkLst = false;
					for (KscdtSchBreakTs schBrk : newData.breakTs) {
						if (schBrk.pk.frameNo == schBrkTsOld.pk.frameNo && schBrk.pk.sid.equals(schBrkTsOld.pk.sid)
								&& schBrk.pk.ymd.equals(schBrkTsOld.pk.ymd)) {
							checkLst = true;
							break;
						}
					}
					if (!checkLst) {
						listRemove.add(schBrkTsOld);
					}
				}

				// remove
				String delete = "delete from KscdtSchBreakTs o " + " where o.pk.sid = :sid " + " and o.pk.ymd = :ymd "
						+ " and o.pk.frameNo = :frameNo";
				for (KscdtSchBreakTs sle : listRemove) {
					this.getEntityManager().createQuery(delete).setParameter("sid", sle.pk.sid)
							.setParameter("ymd", sle.pk.ymd).setParameter("frameNo", sle.pk.frameNo).executeUpdate();
				}

				// add
				for (KscdtSchBreakTs sle : listInsert) {
					this.commandProxy().insert(sle);
				}

			} else {
				oldData.get().breakTs = newData.breakTs;
			}

			// #114431
			if (!oldData.get().kscdtSchGoingOutTs.isEmpty()) {
				oldData.get().kscdtSchGoingOutTs.sort(Comparator.comparing(KscdtSchGoingOutTs::getGoingOutClock));
				newData.kscdtSchGoingOutTs.sort(Comparator.comparing(KscdtSchGoingOutTs::getGoingOutClock));
				int sizeOld = oldData.get().kscdtSchGoingOutTs.size();
				int sizeNew = newData.kscdtSchGoingOutTs.size();

				if (sizeOld > sizeNew) {
					// remove
					for (int i = 0; i < sizeNew; i++) {
						oldData.get().kscdtSchGoingOutTs.get(i).cid = newData.kscdtSchGoingOutTs.get(i).cid;
						oldData.get().kscdtSchGoingOutTs.get(i).reasonAtr = newData.kscdtSchGoingOutTs.get(i).reasonAtr;
						oldData.get().kscdtSchGoingOutTs.get(i).goingOutClock = newData.kscdtSchGoingOutTs
								.get(i).goingOutClock;
						oldData.get().kscdtSchGoingOutTs.get(i).goingBackClock = newData.kscdtSchGoingOutTs
								.get(i).goingBackClock;
					}
					for (int i = sizeOld - 1; i >= sizeNew; i--) {
						oldData.get().kscdtSchGoingOutTs.remove(i);
					}

				} else {
					for (int i = 0; i < sizeOld; i++) {
						oldData.get().kscdtSchGoingOutTs.get(i).cid = newData.kscdtSchGoingOutTs.get(i).cid;
						oldData.get().kscdtSchGoingOutTs.get(i).reasonAtr = newData.kscdtSchGoingOutTs.get(i).reasonAtr;
						oldData.get().kscdtSchGoingOutTs.get(i).goingOutClock = newData.kscdtSchGoingOutTs
								.get(i).goingOutClock;
						oldData.get().kscdtSchGoingOutTs.get(i).goingBackClock = newData.kscdtSchGoingOutTs
								.get(i).goingBackClock;
					}
					for (int i = sizeOld; i < sizeNew; i++) {
						oldData.get().kscdtSchGoingOutTs.add(newData.kscdtSchGoingOutTs.get(i));
					}
				}
			} else {
				oldData.get().kscdtSchGoingOutTs = newData.kscdtSchGoingOutTs;
			}
			this.commandProxy().update(oldData.get());
		}
	}

	@Override
	public void delete(String sid, GeneralDate ymd) {
		Optional<WorkSchedule> optWorkSchedule = this.get(sid, ymd);
		if (optWorkSchedule.isPresent()) {
			KscdtSchBasicInfoPK pk = new KscdtSchBasicInfoPK(optWorkSchedule.get().getEmployeeID(),
					optWorkSchedule.get().getYmd());
			this.commandProxy().remove(KscdtSchBasicInfo.class, pk);
			this.getEntityManager().flush();
		}
	}

	@Override
	public void deleteListDate(String sid, List<GeneralDate> ymds) {
		if (ymds.isEmpty())
			return;
		for (val deleteTable : DELETE_TABLES) {
			this.getEntityManager().createQuery(deleteTable + DELETE_BY_LIST_DATE).setParameter("sid", sid)
					.setParameter("ymds", ymds).executeUpdate();
		}
	}

	@Override
	public void delete(String sid, DatePeriod datePeriod) {
		for (val deleteTable : DELETE_TABLES) {
			this.getEntityManager().createQuery(deleteTable + WHERE_PK).setParameter("sid", sid)
					.setParameter("ymdStart", datePeriod.start()).setParameter("ymdEnd", datePeriod.end())
					.executeUpdate();
		}
	}

	@Override
	public void deleteAllShortTime(String sid, GeneralDate ymd) {
		Boolean optWorkShortTime = this.checkExitsShortTime(sid, ymd);
		if (optWorkShortTime) {
			KscdtSchShortTimeTsPK pk = new KscdtSchShortTimeTsPK(sid, ymd);
			this.commandProxy().remove(KscdtSchShortTimeTs.class, pk);
		}
	}

	@Override
	public void deleteSchAtdLvwTime(String sid, GeneralDate ymd, int workNo) {
		KscdtSchAtdLvwTimePK pk = new KscdtSchAtdLvwTimePK(sid, ymd, workNo);
		this.commandProxy().remove(KscdtSchAtdLvwTime.class, pk);
	}

	@Override
	public void insert(ShortWorkingTimeSheet shortWorkingTimeSheets, String sID, GeneralDate yMD, String cID) {
		this.commandProxy().insert(KscdtSchShortTimeTs.toEntity(shortWorkingTimeSheets, sID, yMD, cID));
	}

	@Override
	public void insertAtdLvwTimes(TimeLeavingWork leavingWork, String sID, GeneralDate yMD, String cID) {
		this.commandProxy().insert(KscdtSchAtdLvwTime.toEntity(leavingWork, sID, yMD, cID));
	}

	private static final String SELECT_BY_SHORTTIME_TS = "SELECT c FROM KscdtSchShortTimeTs c WHERE c.pk.sid = :employeeID AND c.pk.ymd = :ymd AND c.pk.childCareAtr = :childCareAtr AND c.pk.frameNo = :frameNo";

	@Override
	public Optional<ShortTimeOfDailyAttd> getShortTime(String sid, GeneralDate ymd, int childCareAtr, int frameNo) {
		Optional<ShortTimeOfDailyAttd> workSchedule = this.queryProxy()
				.query(SELECT_BY_SHORTTIME_TS, KscdtSchShortTimeTs.class).setParameter("employeeID", sid)
				.setParameter("ymd", ymd).setParameter("childCareAtr", childCareAtr).setParameter("frameNo", frameNo)
				.getSingle(c -> c.toDomain(sid, ymd, childCareAtr, frameNo));
		return workSchedule;
	}

	private static final String SELECT_ALL_SHORTTIME_TS = "SELECT count (c) FROM KscdtSchShortTimeTs c WHERE c.pk.sid = :employeeID AND c.pk.ymd = :ymd";

	@Override
	public boolean checkExitsShortTime(String employeeID, GeneralDate ymd) {
		return this.queryProxy().query(SELECT_ALL_SHORTTIME_TS, Long.class).setParameter("employeeID", employeeID)
				.setParameter("ymd", ymd).getSingle().get() > 0;
	}

	@Override
	public List<WorkSchedule> getListBySid(String sid, DatePeriod period) {

		List<WorkSchedule> result = this.queryProxy()
				.query("SELECT a FROM KscdtSchBasicInfo a " + WHERE_PK, KscdtSchBasicInfo.class)
				.setParameter("sid", sid).setParameter("ymdStart", period.start()).setParameter("ymdEnd", period.end())
				.getList(c -> c.toDomain(c.pk.sid, c.pk.ymd));

		return result;
	}

	private static final String GET_MAX_DATE_WORK_SCHE_BY_LIST_EMP = "SELECT c.pk.ymd FROM KscdtSchBasicInfo c "
			+ " WHERE c.pk.sid IN :listEmp" + " ORDER BY c.pk.ymd desc ";

	@Override
	public Optional<GeneralDate> getMaxDateWorkSche(List<String> listEmp) {
		List<GeneralDate> data = this.getEntityManager()
				.createQuery(GET_MAX_DATE_WORK_SCHE_BY_LIST_EMP, GeneralDate.class).setParameter("listEmp", listEmp)
				.setMaxResults(1).getResultList();
		if (data.isEmpty())
			return Optional.empty();
		return Optional.of(data.get(0));
	}

	@Override
	public List<AffInfoForWorkSchedule> getAffiliationInfor(String sid, DatePeriod period) {
		List<WorkSchedule>  data = this.getListBySid(sid, period);
		List<AffInfoForWorkSchedule> result = data.stream().map(c->new AffInfoForWorkSchedule(c.getEmployeeID(), c.getYmd(), c.getAffInfo()) ).collect(Collectors.toList());
		return result;
	}
	
	@Override
	public List<WorkSchedule> getListJDBC(List<String> sids, DatePeriod period) {
		if (sids.isEmpty() || period == null)
			return new ArrayList<>();
		
		List<WorkSchedule> result = new ArrayList<>();
		
		CollectionUtil.split(sids, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subList -> {
			String listEmp = "(";
			for (int i = 0; i < subList.size(); i++) {
				listEmp += "'" + subList.get(i) + "',";
			}
			// remove last , in string and add )
			listEmp = listEmp.substring(0, listEmp.length() - 1) + ")";
			
			Map<Pair<String, GeneralDate>, ActualWorkingTimeOfDaily> mapActualWorkingTimeOfDaily = this.getListActualWorkingTimeOfDaily(listEmp, period);
			
			result.addAll(getListWorkSchedule(listEmp, period, mapActualWorkingTimeOfDaily));
			
		});
		
		return result;

	}
	
	private String CREATEQUERY_KSCDT_SCH_BASIC_INFO(String listEmp ,DatePeriod period){
		
		String SELECT_KSCDT_SCH_BASIC_INFO = "KSCDT_SCH_BASIC_INFO.SID, KSCDT_SCH_BASIC_INFO.YMD, KSCDT_SCH_BASIC_INFO.CID, KSCDT_SCH_BASIC_INFO.DECISION_STATUS, KSCDT_SCH_BASIC_INFO.EMP_CD, "
				+ " KSCDT_SCH_BASIC_INFO.JOB_ID, KSCDT_SCH_BASIC_INFO.WKP_ID, KSCDT_SCH_BASIC_INFO.CLS_CD, KSCDT_SCH_BASIC_INFO.BUSTYPE_CD, KSCDT_SCH_BASIC_INFO.NURSE_LICENSE, "
				+ " KSCDT_SCH_BASIC_INFO.WKTP_CD, KSCDT_SCH_BASIC_INFO.WKTM_CD, KSCDT_SCH_BASIC_INFO.GO_STRAIGHT_ATR, KSCDT_SCH_BASIC_INFO.BACK_STRAIGHT_ATR, "
				+ " KSCDT_SCH_BASIC_INFO.TREAT_AS_SUBSTITUTE_ATR, KSCDT_SCH_BASIC_INFO.TREAT_AS_SUBSTITUTE_DAYS ";		
		
		String SELECT_KSCDT_SCH_EDIT_STATE = "KSCDT_SCH_EDIT_STATE.ATD_ITEM_ID, KSCDT_SCH_EDIT_STATE.EDIT_STATE ";
		
		String SELECT_KSCDT_SCH_ATD_LVW_TIME = "KSCDT_SCH_ATD_LVW_TIME.WORK_NO, "
				+ " KSCDT_SCH_ATD_LVW_TIME.ATD_CLOCK, KSCDT_SCH_ATD_LVW_TIME.ATD_HOURLY_HD_TS_START, KSCDT_SCH_ATD_LVW_TIME.ATD_HOURLY_HD_TS_END, "
				+ " KSCDT_SCH_ATD_LVW_TIME.LVW_CLOCK, KSCDT_SCH_ATD_LVW_TIME.LVW_HOURLY_HD_TS_START, KSCDT_SCH_ATD_LVW_TIME.LVW_HOURLY_HD_TS_END ";
		
		String SELECT_KSCDT_SCH_SHORTTIME_TS = "KSCDT_SCH_SHORTTIME_TS.CHILD_CARE_ATR,"
				+ " KSCDT_SCH_SHORTTIME_TS.FRAME_NO as FRAME_NO_SHORTTIME, KSCDT_SCH_SHORTTIME_TS.SHORTTIME_TS_START, KSCDT_SCH_SHORTTIME_TS.SHORTTIME_TS_END";
		
		String SELECT_KSCDT_SCH_BREAK_TS = "KSCDT_SCH_BREAK_TS.FRAME_NO as FRAME_NO_BREAK, "
				+ " KSCDT_SCH_BREAK_TS.BREAK_TS_START, KSCDT_SCH_BREAK_TS.BREAK_TS_END ";
		
		String SELECT_KSCDT_SCH_GOING_OUT_TS = "KSCDT_SCH_GOING_OUT_TS.FRAME_NO as FRAME_NO_GOING_OUT,"
				+ " KSCDT_SCH_GOING_OUT_TS.REASON_ATR, KSCDT_SCH_GOING_OUT_TS.GOING_OUT_CLOCK, KSCDT_SCH_GOING_OUT_TS.GOING_BACK_CLOCK ";
		
		String COMMA = " , ";
		
		String sqlQueryWhere = " WHERE KSCDT_SCH_BASIC_INFO.SID IN " + listEmp + " AND KSCDT_SCH_BASIC_INFO.YMD BETWEEN " + "'" + period.start() + "' AND '" + period.end() + "'";
		
		String sqlQuery = "SELECT " + SELECT_KSCDT_SCH_BASIC_INFO + COMMA + SELECT_KSCDT_SCH_EDIT_STATE + COMMA +
				SELECT_KSCDT_SCH_ATD_LVW_TIME + COMMA + SELECT_KSCDT_SCH_SHORTTIME_TS + COMMA + SELECT_KSCDT_SCH_BREAK_TS + COMMA + SELECT_KSCDT_SCH_GOING_OUT_TS + 
				" FROM KSCDT_SCH_BASIC_INFO "
				+ " LEFT JOIN KSCDT_SCH_EDIT_STATE ON KSCDT_SCH_EDIT_STATE.SID = KSCDT_SCH_BASIC_INFO.SID AND KSCDT_SCH_EDIT_STATE.YMD = KSCDT_SCH_BASIC_INFO.YMD"
				+ " LEFT JOIN KSCDT_SCH_ATD_LVW_TIME ON KSCDT_SCH_ATD_LVW_TIME.SID = KSCDT_SCH_BASIC_INFO.SID AND KSCDT_SCH_ATD_LVW_TIME.YMD = KSCDT_SCH_BASIC_INFO.YMD"
				+ " LEFT JOIN KSCDT_SCH_SHORTTIME_TS ON KSCDT_SCH_SHORTTIME_TS.SID = KSCDT_SCH_BASIC_INFO.SID AND KSCDT_SCH_SHORTTIME_TS.YMD = KSCDT_SCH_BASIC_INFO.YMD"
				+ " LEFT JOIN KSCDT_SCH_BREAK_TS ON KSCDT_SCH_BREAK_TS.SID = KSCDT_SCH_BASIC_INFO.SID AND KSCDT_SCH_BREAK_TS.YMD = KSCDT_SCH_BASIC_INFO.YMD"
				+ " LEFT JOIN KSCDT_SCH_GOING_OUT_TS ON KSCDT_SCH_GOING_OUT_TS.SID = KSCDT_SCH_BASIC_INFO.SID AND KSCDT_SCH_GOING_OUT_TS.YMD = KSCDT_SCH_BASIC_INFO.YMD"
				+ sqlQueryWhere;
		
		return sqlQuery;
	}
	
	private List<WorkSchedule> getListWorkSchedule(String listEmp, DatePeriod period, Map<Pair<String, GeneralDate>, ActualWorkingTimeOfDaily> mapActualWorkingTimeOfDaily) {
		
		List<WorkScheduleFromSql> listScheduleFromSql = new ArrayList<>();
		Connection con = this.getEntityManager().unwrap(Connection.class);

		// xử lý KSCDT_SCH_TIME và các bảng con
		String CREATEQUERY_KSCDT_SCH_BASIC_INFO = this.CREATEQUERY_KSCDT_SCH_BASIC_INFO(listEmp, period);
		
		try {
			ResultSet rs = con.createStatement().executeQuery(CREATEQUERY_KSCDT_SCH_BASIC_INFO);
			while (rs.next()) {
				
				// KSCDT_SCH_BASIC_INFO
				String sid = rs.getString("SID");
				GeneralDate ymd = GeneralDate.fromString(rs.getString("YMD"), "yyyy-MM-dd"); 
				String cid = rs.getString("CID"); 
				Integer confirmedATR = rs.getInt("DECISION_STATUS"); 
				String empCd = rs.getString("EMP_CD"); 
				String jobId = rs.getString("JOB_ID"); 
				String wkpId = rs.getString("WKP_ID"); 
				String clsCd = rs.getString("CLS_CD"); 
				String busTypeCd = rs.getString("BUSTYPE_CD"); 
				String nurseLicense = rs.getString("NURSE_LICENSE"); 
				String wktpCd = rs.getString("WKTP_CD"); 
				String wktmCd = rs.getString("WKTM_CD");
				Integer goStraightAtr = rs.getInt("GO_STRAIGHT_ATR");
				Integer backStraightAtr = rs.getInt("BACK_STRAIGHT_ATR");
				Integer treatAsSubstituteAtr = rs.getInt("TREAT_AS_SUBSTITUTE_ATR");
				Double treatAsSubstituteDays = rs.getDouble("TREAT_AS_SUBSTITUTE_DAYS");
				
				// KSCDT_SCH_EDIT_STATE
				Integer atdItemId = rs.getInt("ATD_ITEM_ID");
				Integer editState = rs.getInt("EDIT_STATE");
				
				// KSCDT_SCH_ATD_LVW_TIME
				Integer workNo = rs.getInt("WORK_NO");
				Integer atdClock = rs.getInt("ATD_CLOCK");
				Integer atdHourlyHDTSStart = rs.getInt("ATD_HOURLY_HD_TS_START");
				Integer atdHourlyHDTSEnd = rs.getInt("ATD_HOURLY_HD_TS_END");
				Integer lwkClock = rs.getInt("LVW_CLOCK");
				Integer lvwHourlyHDTSStart = rs.getInt("LVW_HOURLY_HD_TS_START");
				Integer lvwHourlyHDTSEnd = rs.getInt("LVW_HOURLY_HD_TS_END");
				
				// KSCDT_SCH_SHORTTIME_TS
				Integer childCareAtr = rs.getInt("CHILD_CARE_ATR");
				Integer frameNoShorttime = rs.getInt("FRAME_NO_SHORTTIME");
				Integer shortTimeTsStart = rs.getInt("SHORTTIME_TS_START");
				Integer shortTimeTsEnd = rs.getInt("SHORTTIME_TS_END");
				
				// KSCDT_SCH_BREAK_TS
				Integer frameNoBreak = rs.getInt("FRAME_NO_BREAK");
				Integer breakTsStart = rs.getInt("BREAK_TS_START");
				Integer breakTsEnd = rs.getInt("BREAK_TS_END");
				
				// KSCDT_SCH_GOING_OUT_TS
				Integer frameNoGoingOut = rs.getInt("FRAME_NO_GOING_OUT");
				Integer reasonAtr = rs.getInt("REASON_ATR");
				Integer goingOutClock = rs.getInt("GOING_OUT_CLOCK");
				Integer goingBackClock = rs.getInt("GOING_BACK_CLOCK");
				
				listScheduleFromSql.add(new WorkScheduleFromSql(sid, ymd, cid, confirmedATR, empCd, jobId, wkpId, clsCd,
						busTypeCd, nurseLicense, wktpCd, wktmCd, goStraightAtr, backStraightAtr, treatAsSubstituteAtr,
						treatAsSubstituteDays, atdItemId, editState, workNo, atdClock, atdHourlyHDTSStart,
						atdHourlyHDTSEnd, lwkClock, lvwHourlyHDTSStart, lvwHourlyHDTSEnd, childCareAtr,
						frameNoShorttime, shortTimeTsStart, shortTimeTsEnd, frameNoBreak, breakTsStart, breakTsEnd,
						frameNoGoingOut, reasonAtr, goingOutClock, goingBackClock));
			}
			
			Map<Pair<String, GeneralDate>, List<WorkScheduleFromSql>> mapPairSchedule = listScheduleFromSql
					.stream().collect(Collectors.groupingBy(x -> Pair.of(x.sid, x.ymd)));
			
			return this.mapDataSchedule(mapPairSchedule, mapActualWorkingTimeOfDaily);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private List<WorkSchedule> mapDataSchedule(
			Map<Pair<String, GeneralDate>, List<WorkScheduleFromSql>> mapPairSchedule,
			Map<Pair<String, GeneralDate>, ActualWorkingTimeOfDaily> mapActualWorkingTimeOfDaily) {
		
		List<WorkSchedule> result = new ArrayList<>();
		
		mapPairSchedule.forEach((key, value) -> {
			String sid = key.getLeft();
			GeneralDate ymd = key.getRight();
			String cid = value.get(0).cid;
			
			// create WorkInfoOfDailyAttendance
			WorkInformation recordInfo = new WorkInformation(value.get(0).wktpCd, value.get(0).wktmCd);
			WorkInfoOfDailyAttendance workInfo = new WorkInfoOfDailyAttendance(recordInfo,
					CalculationState.No_Calculated, EnumAdaptor.valueOf(value.get(0).goStraightAtr, NotUseAttribute.class),
					EnumAdaptor.valueOf(value.get(0).backStraightAtr, NotUseAttribute.class),
					EnumAdaptor.valueOf(GeneralDate.today().dayOfWeek() - 1, DayOfWeek.class), new ArrayList<>(),
					Optional.empty());
			if (value.get(0).treatAsSubstituteAtr != null && value.get(0).treatAsSubstituteDays != null) {
				workInfo.setNumberDaySuspension(
						Optional.of(new NumberOfDaySuspension(new UsedDays(value.get(0).treatAsSubstituteDays),
								EnumAdaptor.valueOf(value.get(0).treatAsSubstituteAtr, FuriClassifi.class))));
			}
			
			// create AffiliationInforOfDailyAttd
			AffiliationInforOfDailyAttd affInfo = new AffiliationInforOfDailyAttd(new EmploymentCode(value.get(0).empCd), value.get(0).jobId, value.get(0).wkpId, new ClassificationCode(value.get(0).clsCd),
					Optional.ofNullable(new BusinessTypeCode(value.get(0).busTypeCd)),
					Optional.empty());
			
			// create List<BreakTimeOfDailyAttd>
			List<KscdtSchBreakTs> breakTs = value.stream().filter(p -> p.frameNoBreak != null)
					.map(x -> new KscdtSchBreakTs(new KscdtSchBreakTsPK(sid, ymd, x.frameNoBreak), cid, x.breakTsStart, x.breakTsEnd))
					.collect(Collectors.toList());
			
			List<BreakTimeSheet> breakTimeSheets = new ArrayList<>();
			breakTs.stream().forEach(x->{
				BreakTimeSheet timeSheet = new BreakTimeSheet(new BreakFrameNo(x.getPk().getFrameNo()), new TimeWithDayAttr(x.getBreakTsStart()), new TimeWithDayAttr(x.getBreakTsEnd()));
				breakTimeSheets.add(timeSheet);

			});
			BreakTimeOfDailyAttd dailyAttd = new BreakTimeOfDailyAttd(breakTimeSheets);
			
			// create List<EditStateOfDailyAttd>
			List<KscdtSchEditState> editStates = value.stream().filter(p -> p.atdItemId != null)
					.map(x -> new KscdtSchEditState(new KscdtSchEditStatePK(sid, ymd, x.atdItemId), cid, x.editState))
					.collect(Collectors.toList());
			List<EditStateOfDailyAttd> lstEditState = editStates.stream().map(mapper-> new EditStateOfDailyAttd(mapper.getPk().getAtdItemId(),EnumAdaptor.valueOf(mapper.getSditState(), EditStateSetting.class))).collect(Collectors.toList());

			// create Optional<TimeLeavingOfDailyAttd>
			List<KscdtSchAtdLvwTime> atdLvwTimes = value.stream().filter(p -> p.workNo != null)
					.map(x -> new KscdtSchAtdLvwTime(new KscdtSchAtdLvwTimePK(sid, ymd, x.workNo), cid, 
							x.atdClock, x.atdHourlyHDTSStart, x.atdHourlyHDTSEnd, 
							x.lwkClock, x.lvwHourlyHDTSStart, x.lvwHourlyHDTSEnd))
					.collect(Collectors.toList());
			
			TimeLeavingOfDailyAttd optTimeLeaving = null;
			List<TimeLeavingWork> timeLeavingWorks = new ArrayList<>();
			atdLvwTimes.stream().forEach(mapper-> {
				WorkStamp workStamp = new WorkStamp(new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET,Optional.empty()), new TimeWithDayAttr(mapper.getAtdClock())), Optional.empty());
				WorkStamp workStamp2 = new WorkStamp(new WorkTimeInformation(new ReasonTimeChange(TimeChangeMeans.AUTOMATIC_SET,Optional.empty()), new TimeWithDayAttr(mapper.getLwkClock())), Optional.empty());
				//#114431
				TimeSpanForCalc timeVacation = null;
				if(mapper.getAtdHourlyHDTSStart() !=null && mapper.getAtdHourlyHDTSEnd() != null) {
					timeVacation = new TimeSpanForCalc(new TimeWithDayAttr(mapper.getAtdHourlyHDTSStart()) , new TimeWithDayAttr(mapper.getAtdHourlyHDTSEnd()));
				}
				TimeSpanForCalc timeVacation2 = null;
				if(mapper.getLvwHourlyHDTSStart() !=null && mapper.getLvwHourlyHDTSEnd() != null) {
					timeVacation2 = new TimeSpanForCalc(new TimeWithDayAttr(mapper.getLvwHourlyHDTSStart()) , new TimeWithDayAttr(mapper.getLvwHourlyHDTSEnd()));
				}
				TimeActualStamp timeActualStamp = new TimeActualStamp(null, workStamp, 0 , null, timeVacation);
				TimeActualStamp timeActualStamp2 = new TimeActualStamp(null, workStamp2, 0, null, timeVacation2);
				TimeLeavingWork timeLeavingWork = new TimeLeavingWork(new WorkNo(mapper.getPk().getWorkNo()), timeActualStamp, timeActualStamp2);
				timeLeavingWorks.add(timeLeavingWork);
			});
			optTimeLeaving = new TimeLeavingOfDailyAttd(timeLeavingWorks, new WorkTimes(0));
			
			// create Optional<ShortTimeOfDailyAttd> optSortTimeWork
			List<KscdtSchShortTimeTs> schShortTimeTs = value.stream().filter(p -> p.childCareAtr != null && p.frameNoShorttime != null)
					.map(x -> new KscdtSchShortTimeTs(new KscdtSchShortTimeTsPK(sid, ymd, x.childCareAtr, x.frameNoShorttime), 
							cid, x.shortTimeTsStart, x.shortTimeTsEnd))
					.collect(Collectors.toList());
					
			ShortTimeOfDailyAttd optSortTimeWork = null;
			List<ShortWorkingTimeSheet> shortWorkingTimeSheets = new ArrayList<>();
			schShortTimeTs.stream().forEach(x->{
				ShortWorkingTimeSheet shortWorkingTimeSheet = new ShortWorkingTimeSheet(new ShortWorkTimFrameNo(x.getPk().getFrameNo()), EnumAdaptor.valueOf(x.getPk().getChildCareAtr(), ChildCareAtr.class),
						new TimeWithDayAttr(x.getShortTimeTsStart()), new TimeWithDayAttr(x.getShortTimeTsEnd()));
				shortWorkingTimeSheets.add(shortWorkingTimeSheet);
			});

			ActualWorkingTimeOfDaily actualWorkingTimeOfDaily = mapActualWorkingTimeOfDaily.get(key);
			WorkScheduleTimeOfDaily scheduleTimeOfDaily = new WorkScheduleTimeOfDaily(new WorkScheduleTime(new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0)), new AttendanceTime(0));
			AttendanceTimeOfDailyAttendance attendance = null;
			StayingTimeOfDaily stayingTime = new StayingTimeOfDaily(new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0), new AttendanceTime(0), new AttendanceTimeOfExistMinus(0));
			MedicalCareTimeOfDaily medicalCareTime = new MedicalCareTimeOfDaily(WorkTimeNightShift.DAY_SHIFT, new AttendanceTime(0), new AttendanceTime(0), new AttendanceTime(0));
			if(actualWorkingTimeOfDaily != null) {
			attendance = new AttendanceTimeOfDailyAttendance(
					scheduleTimeOfDaily, actualWorkingTimeOfDaily,
					stayingTime, new AttendanceTimeOfExistMinus(0), new AttendanceTimeOfExistMinus(0), medicalCareTime);
			}

			optSortTimeWork = new ShortTimeOfDailyAttd(shortWorkingTimeSheets);
			
			//#114431
			List<KscdtSchGoingOutTs> kscdtSchGoingOutTs = value.stream().filter(p -> p.frameNoGoingOut != null)
					.map(x -> new KscdtSchGoingOutTs(new KscdtSchGoingOutTsPK(sid, ymd, x.frameNoGoingOut), 
							cid, x.reasonAtr, x.goingOutClock, x.goingBackClock))
					.collect(Collectors.toList());
			
			OutingTimeOfDailyAttd outingTime =null;
			if(!kscdtSchGoingOutTs.isEmpty()) {
				List<OutingTimeSheet> outingTimeSheets = kscdtSchGoingOutTs.stream().map(c-> c.toDomain()).collect(Collectors.toList());
				outingTime = new OutingTimeOfDailyAttd(outingTimeSheets);
			}
			result.add(new WorkSchedule(
					sid,
					ymd,
					EnumAdaptor.valueOf(value.get(0).confirmedATR, ConfirmedATR.class),
					workInfo,
					affInfo,
					dailyAttd,
					lstEditState,
					TaskSchedule.createWithEmptyList(), //TODO Cチームに修正してもらいます。
					Optional.ofNullable(optTimeLeaving),
					Optional.ofNullable(attendance),
					Optional.ofNullable(optSortTimeWork),
					Optional.ofNullable(outingTime)));
			
		});
		return result;
	}

	private String CREATEQUERY_KSCDT_SCH_TIME(String listEmp ,DatePeriod period){
		
		String SELECT_KSCDT_SCH_TIME = "KSCDT_SCH_TIME.SID, KSCDT_SCH_TIME.YMD, KSCDT_SCH_TIME.CID, KSCDT_SCH_TIME.COUNT as TBL1_COUNT, KSCDT_SCH_TIME.TOTAL_TIME as TBL1_TOTAL_TIME, KSCDT_SCH_TIME.TOTAL_TIME_ACT, "
				+ " KSCDT_SCH_TIME.PRS_WORK_TIME, KSCDT_SCH_TIME.PRS_WORK_TIME_ACT, KSCDT_SCH_TIME.PRS_PRIME_TIME, KSCDT_SCH_TIME.PRS_MIDNITE_TIME, KSCDT_SCH_TIME.EXT_BIND_TIME_OTW, "
				+ " KSCDT_SCH_TIME.EXT_BIND_TIME_HDW, KSCDT_SCH_TIME.EXT_VARWK_OTW_TIME_LEGAL, KSCDT_SCH_TIME.EXT_FLEX_TIME, KSCDT_SCH_TIME.EXT_FLEX_TIME_PREAPP, "
				+ " KSCDT_SCH_TIME.EXT_MIDNITE_OTW_TIME, KSCDT_SCH_TIME.EXT_MIDNITE_HDW_TIME_LGHD, KSCDT_SCH_TIME.EXT_MIDNITE_HDW_TIME_ILGHD, KSCDT_SCH_TIME.EXT_MIDNITE_HDW_TIME_PUBHD, "
				+ " KSCDT_SCH_TIME.EXT_MIDNITE_TOTAL, KSCDT_SCH_TIME.EXT_MIDNITE_TOTAL_PREAPP, KSCDT_SCH_TIME.INTERVAL_ATD_CLOCK, KSCDT_SCH_TIME.INTERVAL_TIME, "
				+ " KSCDT_SCH_TIME.BRK_TOTAL_TIME, KSCDT_SCH_TIME.HDPAID_TIME, KSCDT_SCH_TIME.HDPAID_HOURLY_TIME, KSCDT_SCH_TIME.HDCOM_TIME, KSCDT_SCH_TIME.HDCOM_HOURLY_TIME, "
				+ " KSCDT_SCH_TIME.HD60H_TIME, KSCDT_SCH_TIME.HD60H_HOURLY_TIME, KSCDT_SCH_TIME.HDSP_TIME, KSCDT_SCH_TIME.HDSP_HOURLY_TIME, KSCDT_SCH_TIME.HDSTK_TIME, "
				+ " KSCDT_SCH_TIME.HD_HOURLY_TIME, KSCDT_SCH_TIME.HD_HOURLY_SHORTAGE_TIME, KSCDT_SCH_TIME.ABSENCE_TIME, KSCDT_SCH_TIME.VACATION_ADD_TIME, KSCDT_SCH_TIME.STAGGERED_WH_TIME ";
		
		String KSCDT_SCH_OVERTIME_WORK = "KSCDT_SCH_OVERTIME_WORK.FRAME_NO as TBL2_FRAME_NO, "
				+ " KSCDT_SCH_OVERTIME_WORK.OVERTIME_WORK_TIME, KSCDT_SCH_OVERTIME_WORK.OVERTIME_WORK_TIME_TRANS, KSCDT_SCH_OVERTIME_WORK.OVERTIME_WORK_TIME_PREAPP ";
		
		String KSCDT_SCH_HOLIDAY_WORK = "KSCDT_SCH_HOLIDAY_WORK.FRAME_NO as TBL3_FRAME_NO, "
				+ " KSCDT_SCH_HOLIDAY_WORK.HOLIDAY_WORK_TS_START, KSCDT_SCH_HOLIDAY_WORK.HOLIDAY_WORK_TS_END, KSCDT_SCH_HOLIDAY_WORK.HOLIDAY_WORK_TIME, "
				+ " KSCDT_SCH_HOLIDAY_WORK.HOLIDAY_WORK_TIME_TRANS, KSCDT_SCH_HOLIDAY_WORK.HOLIDAY_WORK_TIME_PREAPP ";
		
		String KSCDT_SCH_BONUSPAY = "KSCDT_SCH_BONUSPAY.BONUSPAY_TYPE, KSCDT_SCH_BONUSPAY.FRAME_NO as TBL4_FRAME_NO, "
				+ " KSCDT_SCH_BONUSPAY.PREMIUM_TIME as TBL4_PREMIUM_TIME, KSCDT_SCH_BONUSPAY.PREMIUM_TIME_WITHIN, KSCDT_SCH_BONUSPAY.PREMIUM_TIME_WITHOUT ";
		
		String KSCDT_SCH_PREMIUM = "KSCDT_SCH_PREMIUM.FRAME_NO as TBL5_FRAME_NO, KSCDT_SCH_PREMIUM.PREMIUM_TIME as TBL5_PREMIUM_TIME ";
		
		String KSCDT_SCH_SHORTTIME = "KSCDT_SCH_SHORTTIME.CHILD_CARE_ATR, "
				+ " KSCDT_SCH_SHORTTIME.COUNT as TBL6_COUNT, KSCDT_SCH_SHORTTIME.TOTAL_TIME as TBL6_TOTAL_TIME, KSCDT_SCH_SHORTTIME.TOTAL_TIME_WITHIN, KSCDT_SCH_SHORTTIME.TOTAL_TIME_WITHOUT ";
		
		String KSCDT_SCH_COME_LATE = "KSCDT_SCH_COME_LATE.WORK_NO as TBL7_WORK_NO, "
				+ " KSCDT_SCH_COME_LATE.USE_HOURLY_HD_PAID as TBL7_USE_HOURLY_HD_PAID, KSCDT_SCH_COME_LATE.USE_HOURLY_HD_COM as TBL7_USE_HOURLY_HD_COM, "
				+ " KSCDT_SCH_COME_LATE.USE_HOURLY_HD_60H as TBL7_USE_HOURLY_HD_60H, KSCDT_SCH_COME_LATE.USE_HOURLY_HD_SP_NO as TBL7_USE_HOURLY_HD_SP_NO , "
				+ " KSCDT_SCH_COME_LATE.USE_HOURLY_HD_SP_TIME as TBL7_USE_HOURLY_HD_SP_TIME, KSCDT_SCH_COME_LATE.USE_HOURLY_HD_CHILDCARE as TBL7_USE_HOURLY_HD_CHILDCARE, "
				+ " KSCDT_SCH_COME_LATE.USE_HOURLY_HD_NURSECARE as TBL7_USE_HOURLY_HD_NURSECARE ";
		
		String KSCDT_SCH_GOING_OUT = "KSCDT_SCH_GOING_OUT.REASON_ATR,"
				+ " KSCDT_SCH_GOING_OUT.USE_HOURLY_HD_PAID as TBL8_USE_HOURLY_HD_PAID, KSCDT_SCH_GOING_OUT.USE_HOURLY_HD_COM  as TBL8_USE_HOURLY_HD_COM, "
				+ " KSCDT_SCH_GOING_OUT.USE_HOURLY_HD_60H as TBL8_USE_HOURLY_HD_60H, KSCDT_SCH_GOING_OUT.USE_HOURLY_HD_SP_NO as TBL8_USE_HOURLY_HD_SP_NO, "
				+ " KSCDT_SCH_GOING_OUT.USE_HOURLY_HD_SP_TIME as TBL8_USE_HOURLY_HD_SP_TIME, KSCDT_SCH_GOING_OUT.USE_HOURLY_HD_CHILDCARE as TBL8_USE_HOURLY_HD_CHILDCARE, "
				+ " KSCDT_SCH_GOING_OUT.USE_HOURLY_HD_NURSECARE as TBL8_USE_HOURLY_HD_NURSECARE ";
		
		String KSCDT_SCH_LEAVE_EARLY = "KSCDT_SCH_LEAVE_EARLY.WORK_NO as TBL9_WORK_NO, "
				+ " KSCDT_SCH_LEAVE_EARLY.USE_HOURLY_HD_PAID as TBL9_USE_HOURLY_HD_PAID, KSCDT_SCH_LEAVE_EARLY.USE_HOURLY_HD_COM  as TBL9_USE_HOURLY_HD_COM, "
				+ " KSCDT_SCH_LEAVE_EARLY.USE_HOURLY_HD_60H as TBL9_USE_HOURLY_HD_60H, KSCDT_SCH_LEAVE_EARLY.USE_HOURLY_HD_SP_NO as TBL9_USE_HOURLY_HD_SP_NO, "
				+ " KSCDT_SCH_LEAVE_EARLY.USE_HOURLY_HD_SP_TIME as TBL9_USE_HOURLY_HD_SP_TIME, KSCDT_SCH_LEAVE_EARLY.USE_HOURLY_HD_CHILDCARE as TBL9_USE_HOURLY_HD_CHILDCARE, "
				+ " KSCDT_SCH_LEAVE_EARLY.USE_HOURLY_HD_NURSECARE as TBL9_USE_HOURLY_HD_NURSECARE ";
		
		String COMMA = " , ";
		
		String sqlQueryWhere = " WHERE KSCDT_SCH_TIME.SID IN " + listEmp + " AND KSCDT_SCH_TIME.YMD BETWEEN " + "'" + period.start() + "' AND '" + period.end() + "'";
		
		String sqlQuery = "SELECT " + SELECT_KSCDT_SCH_TIME + COMMA + KSCDT_SCH_OVERTIME_WORK + COMMA + KSCDT_SCH_HOLIDAY_WORK + COMMA +
				KSCDT_SCH_BONUSPAY + COMMA + KSCDT_SCH_PREMIUM + COMMA + KSCDT_SCH_SHORTTIME + COMMA + KSCDT_SCH_COME_LATE + COMMA + KSCDT_SCH_GOING_OUT + COMMA + KSCDT_SCH_LEAVE_EARLY +
				" FROM KSCDT_SCH_TIME "
				+ " LEFT JOIN KSCDT_SCH_OVERTIME_WORK ON KSCDT_SCH_TIME.SID = KSCDT_SCH_OVERTIME_WORK.SID AND KSCDT_SCH_TIME.YMD = KSCDT_SCH_OVERTIME_WORK.YMD"
				+ " LEFT JOIN KSCDT_SCH_HOLIDAY_WORK ON KSCDT_SCH_TIME.SID = KSCDT_SCH_HOLIDAY_WORK.SID AND KSCDT_SCH_TIME.YMD = KSCDT_SCH_HOLIDAY_WORK.YMD"
				+ " LEFT JOIN KSCDT_SCH_BONUSPAY ON KSCDT_SCH_TIME.SID = KSCDT_SCH_BONUSPAY.SID AND KSCDT_SCH_TIME.YMD = KSCDT_SCH_BONUSPAY.YMD"
				+ " LEFT JOIN KSCDT_SCH_PREMIUM ON KSCDT_SCH_TIME.SID = KSCDT_SCH_PREMIUM.SID AND KSCDT_SCH_TIME.YMD = KSCDT_SCH_PREMIUM.YMD"
				+ " LEFT JOIN KSCDT_SCH_SHORTTIME ON KSCDT_SCH_TIME.SID = KSCDT_SCH_SHORTTIME.SID AND KSCDT_SCH_TIME.YMD = KSCDT_SCH_SHORTTIME.YMD"
				+ " LEFT JOIN KSCDT_SCH_COME_LATE ON KSCDT_SCH_TIME.SID = KSCDT_SCH_COME_LATE.SID AND KSCDT_SCH_TIME.YMD = KSCDT_SCH_COME_LATE.YMD"
				+ " LEFT JOIN KSCDT_SCH_GOING_OUT ON KSCDT_SCH_TIME.SID = KSCDT_SCH_GOING_OUT.SID AND KSCDT_SCH_TIME.YMD = KSCDT_SCH_GOING_OUT.YMD"
				+ " LEFT JOIN KSCDT_SCH_LEAVE_EARLY ON KSCDT_SCH_TIME.SID = KSCDT_SCH_LEAVE_EARLY.SID AND KSCDT_SCH_TIME.YMD = KSCDT_SCH_LEAVE_EARLY.YMD"
				+ sqlQueryWhere;

		return sqlQuery;
	}
	
	private Map<Pair<String, GeneralDate>, ActualWorkingTimeOfDaily> getListActualWorkingTimeOfDaily(String listEmp, DatePeriod period) {

		List<ScheduleTimeFromSql> listScheduleTimeFromSql = new ArrayList<>();
		Connection con = this.getEntityManager().unwrap(Connection.class);

		// xử lý KSCDT_SCH_TIME và các bảng con
		String CREATEQUERY_KSCDT_SCH_TIME = this.CREATEQUERY_KSCDT_SCH_TIME(listEmp, period);
		
		try {
			ResultSet rs = con.createStatement().executeQuery(CREATEQUERY_KSCDT_SCH_TIME);
			while (rs.next()) {
				
				// KSCDT_SCH_TIME
				String sid = rs.getString("SID");
				GeneralDate ymd = GeneralDate.fromString(rs.getString("YMD"), "yyyy-MM-dd"); 
				String cid = rs.getString("CID"); 
				Integer countTbl1 = rs.getInt("TBL1_COUNT"); 
				Integer totalTimeTbl1 = rs.getInt("TBL1_TOTAL_TIME"); 
				Integer totalTimeAct = rs.getInt("TOTAL_TIME_ACT");
				Integer prsWorkTime = rs.getInt("PRS_WORK_TIME") ;
				Integer prsWorkTimeAct = rs.getInt("PRS_WORK_TIME_ACT") ;
				Integer prsPrimeTime = rs.getInt("PRS_PRIME_TIME") ;
				Integer prsMidniteTime = rs.getInt("PRS_MIDNITE_TIME") ;
				Integer extBindTimeOtw = rs.getInt("EXT_BIND_TIME_OTW");
				Integer extBindTimeHw = rs.getInt("EXT_BIND_TIME_HDW") ;
				Integer extVarwkOtwTimeLegal = rs.getInt("EXT_VARWK_OTW_TIME_LEGAL") ;
				Integer extFlexTime = rs.getInt("EXT_FLEX_TIME") ;
				Integer extFlexTimePreApp = rs.getInt("EXT_FLEX_TIME_PREAPP"); 
				Integer extMidNiteOtwTime = rs.getInt("EXT_MIDNITE_OTW_TIME") ;
				Integer extMidNiteHdwTimeLghd = rs.getInt("EXT_MIDNITE_HDW_TIME_LGHD") ;
				Integer extMidNiteHdwTimeIlghd = rs.getInt("EXT_MIDNITE_HDW_TIME_ILGHD") ;
				Integer extMidNiteHdwTimePubhd = rs.getInt("EXT_MIDNITE_HDW_TIME_PUBHD") ;
				Integer extMidNiteTotal = rs.getInt("EXT_MIDNITE_TOTAL") ;
				Integer extMidNiteTotalPreApp = rs.getInt("EXT_MIDNITE_TOTAL_PREAPP") ;
				Integer intervalAtdClock = rs.getInt("INTERVAL_ATD_CLOCK") ;
				Integer intervalTime = rs.getInt("INTERVAL_TIME") ;
				Integer brkTotalTime = rs.getInt("BRK_TOTAL_TIME") ;
				Integer hdPaidTime = rs.getInt("HDPAID_TIME") ;
				Integer hdPaidHourlyTime = rs.getInt("HDPAID_HOURLY_TIME"); 
				Integer hdComTime = rs.getInt("HDCOM_TIME") ;
				Integer hdComHourlyTime = rs.getInt("HDCOM_HOURLY_TIME"); 
				Integer hd60hTime = rs.getInt("HD60H_TIME") ;
				Integer hd60hHourlyTime = rs.getInt("HD60H_HOURLY_TIME"); 
				Integer hdspTime = rs.getInt("HDSP_TIME") ;
				Integer hdspHourlyTime = rs.getInt("HDSP_HOURLY_TIME") ;
				Integer hdstkTime = rs.getInt("HDSTK_TIME") ;
				Integer hdHourlyTime = rs.getInt("HD_HOURLY_TIME") ;
				Integer hdHourlyShortageTime = rs.getInt("HD_HOURLY_SHORTAGE_TIME") ;
				Integer absenceTime = rs.getInt("ABSENCE_TIME") ;
				Integer vacationAddTime = rs.getInt("VACATION_ADD_TIME") ;
				Integer staggeredWhTime = rs.getInt("STAGGERED_WH_TIME");
				
				// KSCDT_SCH_OVERTIME_WORK
				Integer frameNoTbl2 = rs.getInt("TBL2_FRAME_NO") ;
				Integer overtimeWorkTime = rs.getInt("OVERTIME_WORK_TIME") ;
				Integer overtimeWorkTimeTrans = rs.getInt("OVERTIME_WORK_TIME_TRANS") ;
				Integer overtimeWorkTimePreApp = rs.getInt("OVERTIME_WORK_TIME_PREAPP") ;
				
				// KSCDT_SCH_HOLIDAY_WORK
				Integer frameNoTbl3 = rs.getInt("TBL3_FRAME_NO");
				Integer holidayWorkTsStart = rs.getInt("HOLIDAY_WORK_TS_START");
				Integer holidayWorkTsEnd = rs.getInt("HOLIDAY_WORK_TS_END");
				Integer holidayWorkTime = rs.getInt("HOLIDAY_WORK_TIME");
				Integer holidayWorkTimeTrans = rs.getInt("HOLIDAY_WORK_TIME_TRANS");
				Integer holidayWorkTimePreApp = rs.getInt("HOLIDAY_WORK_TIME_PREAPP");
				
				// KSCDT_SCH_BONUSPAY
				Integer bonuspayType = rs.getInt("BONUSPAY_TYPE");
				Integer frameNoTbl4 = rs.getInt("TBL4_FRAME_NO");
				Integer premiumTimeTbl4 = rs.getInt("TBL4_PREMIUM_TIME");
				Integer premiumTimeWithIn = rs.getInt("PREMIUM_TIME_WITHIN");
				Integer premiumTimeWithOut = rs.getInt("PREMIUM_TIME_WITHOUT");
				
				// KSCDT_SCH_PREMIUM
				Integer frameNoTbl5 = rs.getInt("TBL5_FRAME_NO");
				Integer premiumTimeTbl5 = rs.getInt("TBL5_PREMIUM_TIME");
				
				
				// KSCDT_SCH_SHORTTIME
				Integer childCareAtr = rs.getInt("CHILD_CARE_ATR");
				Integer countTbl6 = rs.getInt("TBL6_COUNT"); 
				Integer totalTimeTbl16= rs.getInt("TBL6_TOTAL_TIME"); 
				Integer totalTimeWithIn= rs.getInt("TOTAL_TIME_WITHIN"); 
				Integer totalTimeWithOut= rs.getInt("TOTAL_TIME_WITHOUT");
				
				// KSCDT_SCH_COME_LATE
				Integer workNoTbl7 = rs.getInt("TBL7_WORK_NO");
				Integer useHourlyHdPaidTbl7 = rs.getInt("TBL7_USE_HOURLY_HD_PAID"); 
				Integer useHourlyHdComTbl7 = rs.getInt("TBL7_USE_HOURLY_HD_COM"); 
				Integer useHourlyHd60hTbl7 = rs.getInt("TBL7_USE_HOURLY_HD_60H"); 
				Integer useHourlyHdSpNOTbl7 = rs.getInt("TBL7_USE_HOURLY_HD_SP_NO");
				Integer useHourlyHdSpTimeTbl7 = rs.getInt("TBL7_USE_HOURLY_HD_SP_TIME");
				Integer useHourlyHdChildCareTbl7 = rs.getInt("TBL7_USE_HOURLY_HD_CHILDCARE"); 
				Integer useHourlyHdNurseCareTbl7 = rs.getInt("TBL7_USE_HOURLY_HD_NURSECARE"); 
				
				// KSCDT_SCH_GOING_OUT
				Integer reasonAtr = rs.getInt("REASON_ATR");
				Integer useHourlyHdPaidTbl8 = rs.getInt("TBL8_USE_HOURLY_HD_PAID"); 
				Integer useHourlyHdComTbl8 = rs.getInt("TBL8_USE_HOURLY_HD_COM"); 
				Integer useHourlyHd60hTbl8 = rs.getInt("TBL8_USE_HOURLY_HD_60H"); 
				Integer useHourlyHdSpNOTbl8 = rs.getInt("TBL8_USE_HOURLY_HD_SP_NO");
				Integer useHourlyHdSpTimeTbl8 = rs.getInt("TBL8_USE_HOURLY_HD_SP_TIME");
				Integer useHourlyHdChildCareTbl8 = rs.getInt("TBL8_USE_HOURLY_HD_CHILDCARE"); 
				Integer useHourlyHdNurseCareTbl8 = rs.getInt("TBL8_USE_HOURLY_HD_NURSECARE");
				
				// KSCDT_SCH_LEAVE_EARLY
				Integer workNoTbl9 = rs.getInt("TBL9_WORK_NO");
				Integer useHourlyHdPaidTbl9 = rs.getInt("TBL9_USE_HOURLY_HD_PAID"); 
				Integer useHourlyHdComTbl9 = rs.getInt("TBL9_USE_HOURLY_HD_COM"); 
				Integer useHourlyHd60hTbl9 = rs.getInt("TBL9_USE_HOURLY_HD_60H"); 
				Integer useHourlyHdSpNOTbl9 = rs.getInt("TBL9_USE_HOURLY_HD_SP_NO");
				Integer useHourlyHdSpTimeTbl9 = rs.getInt("TBL9_USE_HOURLY_HD_SP_TIME");
				Integer useHourlyHdChildCareTbl9 = rs.getInt("TBL9_USE_HOURLY_HD_CHILDCARE"); 
				Integer useHourlyHdNurseCareTbl9 = rs.getInt("TBL9_USE_HOURLY_HD_NURSECARE");
				
				listScheduleTimeFromSql.add(new ScheduleTimeFromSql(sid, ymd, cid, countTbl1, totalTimeTbl1,
						totalTimeAct, prsWorkTime, prsWorkTimeAct, prsPrimeTime, prsMidniteTime, extBindTimeOtw,
						extBindTimeHw, extVarwkOtwTimeLegal, extFlexTime, extFlexTimePreApp, extMidNiteOtwTime,
						extMidNiteHdwTimeLghd, extMidNiteHdwTimeIlghd, extMidNiteHdwTimePubhd, extMidNiteTotal,
						extMidNiteTotalPreApp, intervalAtdClock, intervalTime, brkTotalTime, hdPaidTime,
						hdPaidHourlyTime, hdComTime, hdComHourlyTime, hd60hTime, hd60hHourlyTime, hdspTime,
						hdspHourlyTime, hdstkTime, hdHourlyTime, hdHourlyShortageTime, absenceTime, vacationAddTime,
						staggeredWhTime, frameNoTbl2, overtimeWorkTime, overtimeWorkTimeTrans, overtimeWorkTimePreApp,
						frameNoTbl3, holidayWorkTsStart, holidayWorkTsEnd, holidayWorkTime, holidayWorkTimeTrans,
						holidayWorkTimePreApp, bonuspayType, frameNoTbl4, premiumTimeTbl4, premiumTimeWithIn,
						premiumTimeWithOut, frameNoTbl5, premiumTimeTbl5, childCareAtr, countTbl6, totalTimeTbl16,
						totalTimeWithIn, totalTimeWithOut, workNoTbl7, useHourlyHdPaidTbl7, useHourlyHdComTbl7,
						useHourlyHd60hTbl7, useHourlyHdSpNOTbl7, useHourlyHdSpTimeTbl7, useHourlyHdChildCareTbl7,
						useHourlyHdNurseCareTbl7, reasonAtr, useHourlyHdPaidTbl8, useHourlyHdComTbl8,
						useHourlyHd60hTbl8, useHourlyHdSpNOTbl8, useHourlyHdSpTimeTbl8, useHourlyHdChildCareTbl8,
						useHourlyHdNurseCareTbl8, workNoTbl9, useHourlyHdPaidTbl9, useHourlyHdComTbl9,
						useHourlyHd60hTbl9, useHourlyHdSpNOTbl9, useHourlyHdSpTimeTbl9, useHourlyHdChildCareTbl9,
						useHourlyHdNurseCareTbl9));			
			}
			
			Map<Pair<String, GeneralDate>, List<ScheduleTimeFromSql>> mapPairScheduleTime = listScheduleTimeFromSql
					.stream().collect(Collectors.groupingBy(x -> Pair.of(x.sid, x.ymd)));
			
			return this.mapDataScheduleTime(mapPairScheduleTime);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Map<Pair<String, GeneralDate>, ActualWorkingTimeOfDaily> mapDataScheduleTime(Map<Pair<String, GeneralDate>, List<ScheduleTimeFromSql>> dataMap) {
		Map<Pair<String, GeneralDate>, ActualWorkingTimeOfDaily> result = new HashMap<>(); 
		dataMap.forEach((key, value) -> {
			
			String sid = key.getLeft();
			GeneralDate ymd = key.getRight();
			String cid = value.get(0).cid;
			// 拘束差異時間
			AttendanceTime constraintDiffTime = new AttendanceTime(0);
			// 拘束時間
			ConstraintTime constraintTime = new ConstraintTime(new AttendanceTime(0), new AttendanceTime(0));
			// 時差勤務時間
			AttendanceTime timeDiff = new AttendanceTime(value.get(0).staggeredWhTime);

			// 総労働時間
			KscdtSchOvertimeWork kscdtSchOvertimeWork = new KscdtSchOvertimeWork();
			KscdtSchHolidayWork kscdtSchHolidayWork = new KscdtSchHolidayWork();
			
			// WithinStatutoryMidNightTime
			WithinStatutoryMidNightTime midNightTime = new WithinStatutoryMidNightTime(
					new TimeDivergenceWithCalculation(new AttendanceTime(value.get(0).prsMidniteTime), new AttendanceTime(0), new AttendanceTimeOfExistMinus(0)));
			WithinStatutoryTimeOfDaily withinStatutoryTimeOfDaily = new WithinStatutoryTimeOfDaily(
					new AttendanceTime(value.get(0).prsWorkTime), new AttendanceTime(value.get(0).prsWorkTimeAct),
					new AttendanceTime(value.get(0).prsPrimeTime), midNightTime);
			
			// ExcessOfStatutoryMidNightTime
			ExcessOfStatutoryMidNightTime nightTime = new ExcessOfStatutoryMidNightTime(
					new TimeDivergenceWithCalculation(new AttendanceTime(value.get(0).extMidNiteTotal), new AttendanceTime(0), new AttendanceTimeOfExistMinus(0)),
					new AttendanceTime(value.get(0).extMidNiteTotalPreApp));
			ExcessOverTimeWorkMidNightTime midNightTimes = new ExcessOverTimeWorkMidNightTime(
					new TimeDivergenceWithCalculation(new AttendanceTime(value.get(0).extMidNiteTotalPreApp), new AttendanceTime(0), new AttendanceTimeOfExistMinus(0)));
			OverTimeOfDaily overTimeOfDaily = new OverTimeOfDaily(new ArrayList<>(), new ArrayList<>(),
					Finally.of(midNightTimes), new AttendanceTime(value.get(0).extVarwkOtwTimeLegal),
					new FlexTime(
							TimeDivergenceWithCalculationMinusExist.sameTime(new AttendanceTimeOfExistMinus(value.get(0).extFlexTime)),
							new AttendanceTime(value.get(0).extFlexTimePreApp)),
					new AttendanceTime(value.get(0).extBindTimeOtw));
			
			List<KscdtSchOvertimeWork> overtimeWorks = value.stream().filter(p -> p.frameNoTbl2 != null)
					.map(x -> new KscdtSchOvertimeWork(new KscdtSchOvertimeWorkPK(sid, ymd, x.frameNoTbl2), cid,
							x.overtimeWorkTime, x.overtimeWorkTimeTrans, x.overtimeWorkTimePreApp))
					.collect(Collectors.toList());
			
			List<KscdtSchHolidayWork> holidayWorks = value.stream().filter(p -> p.frameNoTbl3 != null)
					.map(x -> new KscdtSchHolidayWork(new KscdtSchHolidayWorkPK(sid, ymd, x.frameNoTbl3), cid,
							x.holidayWorkTsStart, x.holidayWorkTsEnd, x.holidayWorkTime, x.holidayWorkTimeTrans, x.holidayWorkTimePreApp))
					.collect(Collectors.toList());
			
			// ExcessOfStatutoryTimeOfDaily
			ExcessOfStatutoryTimeOfDaily excessOfStatutoryTimeOfDaily = new ExcessOfStatutoryTimeOfDaily(nightTime,
					Optional.ofNullable(kscdtSchOvertimeWork.toDomain(overTimeOfDaily, overtimeWorks)),
					Optional.ofNullable(kscdtSchHolidayWork.toDomain(value.get(0).extBindTimeHw, value.get(0).extMidNiteHdwTimeLghd,
							value.get(0).extMidNiteHdwTimeIlghd, value.get(0).extMidNiteHdwTimePubhd,holidayWorks)));
			
			// lateTimeOfDaily
			List<KscdtSchComeLate> kscdtSchComeLates = value.stream().filter(p -> p.workNoTbl7 != null)
					.map(x -> new KscdtSchComeLate(new KscdtSchComeLatePK(sid, ymd, x.workNoTbl7), cid,
							x.useHourlyHdPaidTbl7, x.useHourlyHdComTbl7, x.useHourlyHd60hTbl7, x.useHourlyHdSpNOTbl7, 
							x.useHourlyHdSpTimeTbl7, x.useHourlyHdChildCareTbl7, x.useHourlyHdNurseCareTbl7))
					.collect(Collectors.toList());
			//#114431
			List<LateTimeOfDaily> lateTimeOfDaily = new ArrayList<>();
			for(KscdtSchComeLate scl : kscdtSchComeLates) {
				LateTimeOfDaily temp = new LateTimeOfDaily(
						TimeWithCalculation.sameTime(AttendanceTime.ZERO), // value default
						TimeWithCalculation.sameTime(AttendanceTime.ZERO), // value default
						new WorkNo(scl.pk.workNo), 
						scl.toDomain(), 
						IntervalExemptionTime.defaultValue());// value default
				lateTimeOfDaily.add(temp);
			}
			
			// KscdtSchLeaveEarly
			List<KscdtSchLeaveEarly> kscdtSchLeaveEarly = value.stream().filter(p -> p.workNoTbl9 != null)
					.map(x -> new KscdtSchLeaveEarly(new KscdtSchLeaveEarlyPK(sid, ymd, x.workNoTbl9), cid,
							x.useHourlyHdPaidTbl9, x.useHourlyHdComTbl9, x.useHourlyHd60hTbl9, x.useHourlyHdSpNOTbl9, 
							x.useHourlyHdSpTimeTbl9, x.useHourlyHdChildCareTbl9, x.useHourlyHdNurseCareTbl9))
					.collect(Collectors.toList());
			
			List<LeaveEarlyTimeOfDaily> leaveEarlyTimeOfDaily = new ArrayList<>();
			for(KscdtSchLeaveEarly scl : kscdtSchLeaveEarly) {
				LeaveEarlyTimeOfDaily temp = new LeaveEarlyTimeOfDaily(
						TimeWithCalculation.sameTime(AttendanceTime.ZERO), // value default
						TimeWithCalculation.sameTime(AttendanceTime.ZERO), // value default
						new WorkNo(scl.pk.workNo), 
						scl.toDomain(), 
						IntervalExemptionTime.defaultValue());// value default
				leaveEarlyTimeOfDaily.add(temp);
			}
			
			// BreakTimeOfDaily
			DeductionTotalTime deductionTotalTime = DeductionTotalTime
					.of(TimeWithCalculation.sameTime(new AttendanceTime(value.get(0).brkTotalTime)), TimeWithCalculation.sameTime(AttendanceTime.ZERO), TimeWithCalculation.sameTime(AttendanceTime.ZERO));
			BreakTimeOfDaily breakTimeOfDaily = new BreakTimeOfDaily(null, deductionTotalTime, new BreakTimeGoOutTimes(0), new AttendanceTime(0), new ArrayList<>());

			// KscdtSchGoingOut
			List<KscdtSchGoingOut> kscdtSchGoingOut = value.stream().filter(p -> p.reasonAtr != null)
					.map(x -> new KscdtSchGoingOut(new KscdtSchGoingOutPK(sid, ymd, x.reasonAtr), cid,
							x.useHourlyHdPaidTbl8, x.useHourlyHdComTbl8, x.useHourlyHd60hTbl8, x.useHourlyHdSpNOTbl8, 
							x.useHourlyHdSpTimeTbl8, x.useHourlyHdChildCareTbl8, x.useHourlyHdNurseCareTbl8))
					.collect(Collectors.toList());
			
			List<OutingTimeOfDaily> lateOutingTimeOfDaily = new ArrayList<>();
			for(KscdtSchGoingOut sgo : kscdtSchGoingOut) {
				OutingTimeOfDaily temp = new OutingTimeOfDaily(
						new BreakTimeGoOutTimes(0), // value default
						GoingOutReason.valueOf(sgo.pk.reasonAtr), 
						sgo.toDomain(), 
						OutingTotalTime.of(TimeWithCalculation.sameTime(AttendanceTime.ZERO), // value default
								WithinOutingTotalTime.of(TimeWithCalculation.sameTime(AttendanceTime.ZERO),// value default
										TimeWithCalculation.sameTime(AttendanceTime.ZERO), // value default
										TimeWithCalculation.sameTime(AttendanceTime.ZERO)),// value default
								TimeWithCalculation.sameTime(AttendanceTime.ZERO)), // value default
						OutingTotalTime.of(TimeWithCalculation.sameTime(AttendanceTime.ZERO), // value default
								WithinOutingTotalTime.of(TimeWithCalculation.sameTime(AttendanceTime.ZERO),// value default
										TimeWithCalculation.sameTime(AttendanceTime.ZERO), // value default
										TimeWithCalculation.sameTime(AttendanceTime.ZERO)),// value default
								TimeWithCalculation.sameTime(AttendanceTime.ZERO)), // value default
						new ArrayList<>());// value default
				lateOutingTimeOfDaily.add(temp);
			}
			
			
			// KscdtSchBonusPay
			List<KscdtSchBonusPay> bonusPays = value.stream().filter(p -> p.bonuspayType != null && p.frameNoTbl4 != null)
					.map(x -> new KscdtSchBonusPay(new KscdtSchBonusPayPK(sid, ymd, x.bonuspayType, x.frameNoTbl4), cid,
							x.premiumTimeTbl4, x.premiumTimeWithIn, x.premiumTimeWithOut))
					.collect(Collectors.toList());
			KscdtSchBonusPay kscdtSchBonusPay = new KscdtSchBonusPay();
			RaiseSalaryTimeOfDailyPerfor raiseSalaryTimeOfDailyPerfor = new RaiseSalaryTimeOfDailyPerfor(
					kscdtSchBonusPay.toDomain(bonusPays), new ArrayList<>());
			
			TemporaryTimeOfDaily temporaryTime = new TemporaryTimeOfDaily(new ArrayList<>());
			IntervalTimeOfDaily intervalTime = IntervalTimeOfDaily.of(new AttendanceClock(0), new AttendanceTime(0));
			
			// KscdtSchShortTime
			// QA 110840 - cần ktra kỹ vì có thể sai
			List<KscdtSchShortTime> shortTimes = value.stream().filter(p -> p.childCareAtr != null)
					.map(x -> new KscdtSchShortTime(new KscdtSchShortTimePK(sid, ymd, x.childCareAtr), cid,
							x.countTbl6, x.totalTimeTbl16, x.totalTimeWithIn, x.totalTimeWithOut))
					.collect(Collectors.toList());
			
			KscdtSchShortTime kscdtSchShortTime = new KscdtSchShortTime();
			ShortWorkTimeOfDaily shotrTime = kscdtSchShortTime.toDomain(sid, ymd, shortTimes);
			
			
			// HolidayOfDaily
			SubstituteHolidayOfDaily substitute = new SubstituteHolidayOfDaily(new AttendanceTime(value.get(0).hdComTime),
					new AttendanceTime(value.get(0).hdComHourlyTime));
			OverSalaryOfDaily overSalary = new OverSalaryOfDaily(new AttendanceTime(value.get(0).hd60hTime),
					new AttendanceTime(value.get(0).hd60hHourlyTime));
			SpecialHolidayOfDaily specialHoliday = new SpecialHolidayOfDaily(new AttendanceTime(value.get(0).hdspTime),
					new AttendanceTime(value.get(0).hdspHourlyTime));
			YearlyReservedOfDaily yearlyReserved = new YearlyReservedOfDaily(new AttendanceTime(value.get(0).hdstkTime));
			TimeDigestOfDaily timeDigest = new TimeDigestOfDaily(new AttendanceTime(value.get(0).hdHourlyTime),
					new AttendanceTime(value.get(0).hdHourlyShortageTime));
			AnnualOfDaily annual = new AnnualOfDaily(new AttendanceTime(value.get(0).hdPaidTime), new AttendanceTime(value.get(0).hdPaidHourlyTime));
			HolidayOfDaily holidayOfDaily = new HolidayOfDaily(new AbsenceOfDaily(new AttendanceTime(value.get(0).absenceTime)),
					timeDigest, yearlyReserved, substitute, overSalary, specialHoliday, annual, new TransferHolidayOfDaily(new AttendanceTime(0)));
			
			TotalWorkingTime totalWorkingTime = new TotalWorkingTime(new AttendanceTime(value.get(0).totalTimeTbl1), new AttendanceTime(0),
					new AttendanceTime(value.get(0).totalTimeAct), withinStatutoryTimeOfDaily, excessOfStatutoryTimeOfDaily,
					lateTimeOfDaily,leaveEarlyTimeOfDaily , breakTimeOfDaily, lateOutingTimeOfDaily, raiseSalaryTimeOfDailyPerfor,
					new WorkTimes(value.get(0).countTbl1), temporaryTime, shotrTime, holidayOfDaily, new AttendanceTime(value.get(0).vacationAddTime), intervalTime);
			
			// 乖離時間
			DivergenceTimeOfDaily divTime = new DivergenceTimeOfDaily(new ArrayList<>());

			// 割増時間
			List<KscdtSchPremium> premiums = value.stream().filter(p -> p.frameNoTbl5 != null)
					.map(x -> new KscdtSchPremium(new KscdtSchPremiumPK(sid, ymd, x.frameNoTbl5), 
							cid, x.premiumTimeTbl5))
					.collect(Collectors.toList());
			
			KscdtSchPremium kscdtSchPremium = new KscdtSchPremium();
			PremiumTimeOfDailyPerformance premiumTime = new PremiumTimeOfDailyPerformance(kscdtSchPremium.toDomain(premiums));

			ActualWorkingTimeOfDaily workingTimeOfDaily = new ActualWorkingTimeOfDaily(constraintDiffTime, constraintTime,
					timeDiff, totalWorkingTime, divTime, premiumTime);
			result.put(key, workingTimeOfDaily);
		});
		return result;
	}
}
