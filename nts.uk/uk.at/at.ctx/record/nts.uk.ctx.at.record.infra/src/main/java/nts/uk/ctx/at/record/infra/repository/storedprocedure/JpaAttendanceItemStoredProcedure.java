package nts.uk.ctx.at.record.infra.repository.storedprocedure;

import java.sql.Date;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.StoredProcedureQuery;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.util.value.Finally;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.attendanceitem.StoredProcedureFactory;
import nts.uk.ctx.at.record.dom.daily.TimeDivergenceWithCalculation;
import nts.uk.ctx.at.record.dom.daily.holidayworktime.HolidayWorkTimeOfDaily;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

@Stateless
public class JpaAttendanceItemStoredProcedure extends JpaRepository implements StoredProcedureFactory {

	@Override
	public void runStoredProcedure(String comId, Optional<AttendanceTimeOfDailyPerformance> attendanceTime,
			WorkInfoOfDailyPerformance workInfo) {

		StoredProcedureQuery stQuery = this.getEntityManager().createNamedStoredProcedureQuery("SSPR_DAIKYUZAN_PRC");

		stQuery.setParameter("CID", comId).setParameter("SID", workInfo.getEmployeeId())
				.setParameter("YMD", Date.valueOf(workInfo.getYmd().localDate()))
				.setParameter("WorkTypeCode", workInfo.getRecordInfo().getWorkTypeCode().v())
				.setParameter("WorkTimeCode", workInfo.getRecordInfo().getWorkTimeCode() == null ? null : workInfo.getRecordInfo().getWorkTimeCode().v())
				.setParameter("HoliWorkTimes", calcHolWorkTime(attendanceTime));

		stQuery.execute();
	}

	private int calcHolWorkTime(Optional<AttendanceTimeOfDailyPerformance> attendanceTime) {
		if (!attendanceTime.isPresent()) {
			return 0;
		}

		if (attendanceTime.get().getActualWorkingTimeOfDaily().getTotalWorkingTime().getExcessOfStatutoryTimeOfDaily()
				.getWorkHolidayTime().isPresent()) {
			HolidayWorkTimeOfDaily hol = attendanceTime.get().getActualWorkingTimeOfDaily().getTotalWorkingTime()
					.getExcessOfStatutoryTimeOfDaily().getWorkHolidayTime().get();
			return hol.getHolidayWorkFrameTime().stream().mapToInt(c -> getHolTimeOrDefaul(c.getTransferTime())).sum();
		}

		return 0;
	}

	private int getHolTimeOrDefaul(Finally<TimeDivergenceWithCalculation> holTime) {

		if (holTime.isPresent()) {
			return holTime.get().getTime().valueAsMinutes();
		}

		return 0;
	}

}
