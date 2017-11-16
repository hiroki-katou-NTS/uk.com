package nts.uk.ctx.at.record.dom.workinformation;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface WorkInformationRepository {
	
	/** Find an Employee WorkInformation of Daily Performance */
	Optional<WorkInfoOfDailyPerformance> find(String employeeId, GeneralDate ymd);
	
	List<WorkInfoOfDailyPerformance> findByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);
	
	void delete(String employeeId, GeneralDate ymd);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);
	
	/**
	 * KIF 001
	 * @param employeeId
	 * @param ymd
	 * @param scheduleWorkTypeCode
	 * @param scheduleWorkTimeCode
	 */
	void updateWorkInfo(String employeeId, GeneralDate ymd, String scheduleWorkTypeCode, String scheduleWorkTimeCode);

	/**
	 * KIF 001
	 * @param employeeId
	 * @param ymd
	 * @param workNo
	 * @param attendance
	 * @param leaveWork
	 */
	void updateScheduleTime(String employeeId, GeneralDate ymd, BigDecimal workNo, int attendance, int leaveWork);
	
	/**
	 * KIF 001
	 * @param employeeId
	 * @param ymd
	 * @param goStraight
	 * @param backStraight
	 */
	void updateDirectLine(String employeeId, GeneralDate ymd, int goStraight, int backStraight);
	
	/**
	 * KIF 001
	 * @param employeeId
	 * @param ymd
	 * @param workTime
	 * @param workType
	 */
	void updateDayOfWeek(String employeeId, GeneralDate ymd, String workTime, String workType);
}