package nts.uk.ctx.at.record.infra.repository.monthly.verticaltotal;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.monthly.AttendanceTimeOfMonthlyKey;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthly;
import nts.uk.ctx.at.record.dom.monthly.verticaltotal.VerticalTotalOfMonthlyRepository;
import nts.uk.ctx.at.record.infra.entity.monthly.KrcdtMonAttendanceTimePK;
import nts.uk.ctx.at.record.infra.entity.monthly.verticaltotal.KrcdtMonVerticalTotal;
import nts.uk.ctx.at.shared.dom.shortworktime.ChildCareAtr;

/**
 * リポジトリ実装：月別実績の縦計
 * @author shuichu_ishida
 */
@Stateless
public class JpaVerticalTotalOfMonthly extends JpaRepository implements VerticalTotalOfMonthlyRepository {

	/** 更新 */
	@Override
	public void update(AttendanceTimeOfMonthlyKey attendanceTimeOfMonthlyKey,
			VerticalTotalOfMonthly verticalTotalOfMonthly) {

		this.toUpdate(attendanceTimeOfMonthlyKey, verticalTotalOfMonthly);
	}
	
	/**
	 * データ更新
	 * @param domainKey キー値：月別実績の勤怠時間
	 * @param domain ドメイン：月別実績の縦計
	 */
	private void toUpdate(AttendanceTimeOfMonthlyKey domainKey, VerticalTotalOfMonthly domain){

		// 締め日付
		val closureDate = domainKey.getClosureDate();
		
		// キー
		val key = new KrcdtMonAttendanceTimePK(
				domainKey.getEmployeeId(),
				domainKey.getYearMonth().v(),
				domainKey.getClosureId().value,
				closureDate.getClosureDay().v(),
				(closureDate.getLastDayOfMonth() ? 1 : 0));
		
		val workDays = domain.getWorkDays();
		val workTime = domain.getWorkTime();
		KrcdtMonVerticalTotal entity = this.queryProxy().find(key, KrcdtMonVerticalTotal.class).get();
		if (entity == null) return;
		entity.workDays = workDays.getWorkDays().getDays().v();
		entity.workTimes = workDays.getWorkTimes().getTimes().v();
		entity.twoTimesWorkTimes = workDays.getTwoTimesWorkTimes().getTimes().v();
		entity.temporaryWorkTimes = workDays.getTemporaryWorkTimes().getTimes().v();
		entity.predetermineDays = workDays.getPredetermineDays().getPredeterminedDays().v();
		entity.holidayDays = workDays.getHolidayDays().getDays().v();
		entity.attendanceDays = workDays.getAttendanceDays().getDays().v();
		entity.holidayWorkDays = workDays.getHolidayWorkDays().getDays().v();
		entity.totalAbsenceDays = workDays.getAbsenceDays().getTotalAbsenceDays().v();
		entity.totalAbsenceTime = workDays.getAbsenceDays().getTotalAbsenceTime().v();
		entity.payAttendanceDays = workDays.getPayDays().getPayAttendanceDays().v();
		entity.payAbsenceDays = workDays.getPayDays().getPayAbsenceDays().v();
		
		entity.childcareGoOutTimes = 0;
		entity.childcareGoOutTime = 0;
		entity.careGoOutTimes = 0;
		entity.careGoOutTime = 0;
		val goOutForChildCares = workTime.getGoOut().getGoOutForChildCares();
		if (goOutForChildCares.containsKey(ChildCareAtr.CHILD_CARE)){
			val goOutForChildCare = goOutForChildCares.get(ChildCareAtr.CHILD_CARE);
			entity.childcareGoOutTimes = goOutForChildCare.getTimes().v();
			entity.childcareGoOutTime = goOutForChildCare.getTime().v();
		}
		if (goOutForChildCares.containsKey(ChildCareAtr.CARE)){
			val goOutForCare = goOutForChildCares.get(ChildCareAtr.CARE);
			entity.careGoOutTimes = goOutForCare.getTimes().v();
			entity.careGoOutTime = goOutForCare.getTime().v();
		}
		
		entity.premiumMidnightTime = workTime.getPremiumTime().getMidnightTime().v();
		entity.premiumLegalOutsideWorkTime = workTime.getPremiumTime().getLegalOutsideWorkTime().v();
		entity.premiumIllegalOutsideWorkTime = workTime.getPremiumTime().getIllegalOutsideWorkTime().v();
		entity.premiumLegalHolidayWorkTime = workTime.getPremiumTime().getLegalHolidayWorkTime().v();
		entity.premiumIllegalHolidayWorkTime = workTime.getPremiumTime().getIllegalHolidayWorkTime().v();
		entity.breakTime = workTime.getBreakTime().getBreakTime().v();
		entity.legalHolidayTime = workTime.getHolidayTime().getLegalHolidayTime().v();
		entity.illegalHolidayTime = workTime.getHolidayTime().getIllegalHolidayTime().v();
		entity.illegalSpecialHolidayTime = workTime.getHolidayTime().getIllegalSpecialHolidayTime().v();
		entity.overWorkMidnightTime = workTime.getMidnightTime().getOverWorkMidnightTime().getTime().v();
		entity.calcOverWorkMidnightTime = workTime.getMidnightTime().getOverWorkMidnightTime().getCalcTime().v();
		entity.legalMidnightTime = workTime.getMidnightTime().getLegalMidnightTime().getTime().v();
		entity.calcLegalMidnightTime = workTime.getMidnightTime().getLegalMidnightTime().getCalcTime().v();
		entity.illegalMidnightTime = workTime.getMidnightTime().getIllegalMidnightTime().getTime().getTime().v();
		entity.calcIllegalMidnightTime = workTime.getMidnightTime().getIllegalMidnightTime().getTime().getCalcTime().v();
		entity.illegalBeforeMidnightTime = workTime.getMidnightTime().getIllegalMidnightTime().getBeforeTime().v();
		entity.legalHolidayWorkMidnightTime = workTime.getMidnightTime().getLegalHolidayWorkMidnightTime().getTime().v();
		entity.calcLegalHolidayWorkMidnightTime = workTime.getMidnightTime().getLegalHolidayWorkMidnightTime().getCalcTime().v();
		entity.illegalHolidayWorkMidnightTime = workTime.getMidnightTime().getIllegalHolidayWorkMidnightTime().getTime().v();
		entity.calcIllegalHolidayWorkMidnightTime = workTime.getMidnightTime().getIllegalHolidayWorkMidnightTime().getCalcTime().v();
		entity.specialHolidayWorkMidnightTime = workTime.getMidnightTime().getSpecialHolidayWorkMidnightTime().getTime().v();
		entity.calcSpecialHolidayWorkMidnightTime = workTime.getMidnightTime().getSpecialHolidayWorkMidnightTime().getCalcTime().v();
		entity.lateTimes = workTime.getLateLeaveEarly().getLate().getTimes().v();
		entity.lateTime = workTime.getLateLeaveEarly().getLate().getTime().getTime().v();
		entity.calcLateTime = workTime.getLateLeaveEarly().getLate().getTime().getCalcTime().v();
		entity.leaveEarlyTimes = workTime.getLateLeaveEarly().getLeaveEarly().getTimes().v();
		entity.leaveEarlyTime = workTime.getLateLeaveEarly().getLeaveEarly().getTime().getTime().v();
		entity.calcLeaveEarlyTime = workTime.getLateLeaveEarly().getLeaveEarly().getTime().getCalcTime().v();
		entity.attendanceLeaveGateBeforeAttendanceTime = workTime.getAttendanceLeaveGateTime().getTimeBeforeAttendance().v();
		entity.attendanceLeaveGateAfterLeaveWorkTime = workTime.getAttendanceLeaveGateTime().getTimeAfterLeaveWork().v();
		entity.attendanceLeaveGateStayingTime = workTime.getAttendanceLeaveGateTime().getStayingTime().v();
		entity.attendanceLeaveGateUnemployedTime = workTime.getAttendanceLeaveGateTime().getUnemployedTime().v();
		entity.budgetVarienceTime = workTime.getBudgetTimeVarience().getTime().v();
	}
}
