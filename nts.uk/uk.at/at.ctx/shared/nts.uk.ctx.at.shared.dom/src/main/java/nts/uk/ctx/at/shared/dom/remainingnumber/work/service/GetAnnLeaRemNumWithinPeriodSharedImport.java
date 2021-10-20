package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;

public interface GetAnnLeaRemNumWithinPeriodSharedImport {
	/**
	 * 年休
	 * @param companyId
	 * @param employeeId
	 * @param aggrPeriod
	 * @param mode
	 * @param criteriaDate
	 * @param isGetNextMonthData
	 * @param isCalcAttendanceRate
	 * @param isOverWrite
	 * @param forOverWriteList
	 * @param noCheckStartDate
	 * @return
	 */
	List<AnnualLeaveErrorSharedImport> annualLeaveErrors(String companyId, String employeeId, DatePeriod aggrPeriod, 
			boolean mode,GeneralDate criteriaDate, boolean isGetNextMonthData, boolean isCalcAttendanceRate,
			Optional<Boolean> isOverWrite, Optional<List<TempAnnualLeaveMngs>> forOverWriteList, Optional<Boolean> noCheckStartDate, DatePeriod overWriteDatePeriod);
	/**
	 * 年休積休
	 * @param companyId
	 * @param employeeId
	 * @param aggrPeriod
	 * @param mode
	 * @param criteriaDate
	 * @param isGetNextMonthData
	 * @param isCalcAttendanceRate
	 * @param isOverWrite
	 * @param tempAnnDataforOverWriteList
	 * @param tempRsvDataforOverWriteList
	 * @param isOutputForShortage
	 * @param noCheckStartDate
	 * @return
	 */
	List<ReserveLeaveErrorImport> reserveLeaveErrors(String companyId, String employeeId, DatePeriod aggrPeriod, boolean mode,
			GeneralDate criteriaDate, boolean isGetNextMonthData, boolean isCalcAttendanceRate,
			Optional<Boolean> isOverWrite,
			Optional<List<TempAnnualLeaveMngs>> tempAnnDataforOverWriteList,
			Optional<List<TmpResereLeaveMng>> tempRsvDataforOverWriteList,
			Optional<Boolean> isOutputForShortage,
			Optional<Boolean> noCheckStartDate, DatePeriod overWriteDatePeriod);
}
