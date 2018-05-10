package nts.uk.file.at.app.export.dailyschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.dailyattendanceitem.AttendanceResultImport;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceType;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.adapter.dailyperformance.DailyPerformanceAdapter;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.file.at.app.export.dailyschedule.totalsum.DayType;
import nts.uk.file.at.app.export.dailyschedule.totalsum.NumberOfItem;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalCountDay;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.workinfomation.WorkInfoOfDailyPerformanceDetailDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * Total day count class
 * @author HoangNDH
 *
 */
@Stateless
public class TotalDayCountWs {
	@Inject
	private AttendanceTypeRepository attendanceTypeRepository;
	
	@Inject
	private AttendanceResultImportAdapter attendanceResultAdapter;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private DailyPerformanceScreenRepo dailyPerformanceRepo;
	
	/**
	 * 所定日数を計算する
	 * @param employeeId
	 * @param lstDate
	 */
	private void calculatePredeterminedDay(int employeeId, DateRange dateRange, TotalCountDay totalCountDay) {
		// ドメインモデル「画面で利用できる勤怠項目一覧」を取得する
		AttendanceType attendanceType = attendanceTypeRepository.getItemByScreenUseAtr(AppContexts.user().companyId(), 19)
				.stream().filter(x -> x.getAttendanceItemId() == 1 && x.getAttendanceItemType().value == 1).findFirst().get();
		
		// ドメインモデル「勤務種類」を取得する
		Optional<WorkType> optWorkType = workTypeRepository.findByPK(AppContexts.user().companyId(), String.valueOf(attendanceType.getAttendanceItemId()));
		
		if (optWorkType.isPresent()) {
			// ドメインモデル「日別実績の勤務情報」を取得する
			List<String> empList = new ArrayList<>();
			empList.add(String.valueOf(employeeId));
			// 予定勤務種類コードを取得する
			List<WorkInfoOfDailyPerformanceDetailDto> dailyPerformanceList = dailyPerformanceRepo.find(empList, dateRange);
			
			// 所定日数をカウントする
			totalCountDay.setPredeterminedDay(dailyPerformanceList.stream().filter(x -> x.getScheduleWorkInformation().getWorkTypeCode() == optWorkType.get().getWorkTypeCode().v()).collect(Collectors.toList()).size());
		}
		totalCountDay.setPredeterminedDay(0);
	}
	
	/**
	 * 所定外の日数を計算する
	 * @param employeeId
	 * @param dateRange
	 * @param dayType
	 * @return
	 */
	private void calculateNonPredeterminedDay(int employeeId, DateRange dateRange, TotalCountDay totalCountDay) {
		// ドメインモデル「画面で利用できる勤怠項目一覧」を取得する
		AttendanceType attendanceType = attendanceTypeRepository.getItemByScreenUseAtr(AppContexts.user().companyId(), 19)
				.stream().filter(x -> x.getAttendanceItemId() == 58 && x.getAttendanceItemType().value == 1).findFirst().get();
		
		for (DayType dayType : DayType.values()) {
			// ドメインモデル「勤務種類」を取得する
			Optional<WorkType> optWorkType = workTypeRepository.findByPK(AppContexts.user().companyId(), String.valueOf(attendanceType.getAttendanceItemId()));
			
			if (optWorkType.isPresent()) {
				WorkType workType = optWorkType.get();
				
				if (workType.getDailyWork().getMorning().value == dayType.value) {
					// ドメインモデル「日別実績の勤務情報」を取得する
					List<String> empList = new ArrayList<>();
					empList.add(String.valueOf(employeeId));
					// 予定勤務種類コードを取得する
					List<WorkInfoOfDailyPerformanceDetailDto> dailyPerformanceList = dailyPerformanceRepo.find(empList, dateRange);
					
					// 日数をカウントする
					int dayCount = dailyPerformanceList.stream().filter(x -> x.getScheduleWorkInformation().getWorkTypeCode() == workType.getWorkTypeCode().v()).collect(Collectors.toList()).size();
					
					switch (dayType) {
					case ATTEND:
						totalCountDay.setWorkingDay(dayCount);
						break;
					case ABSENCE:
						totalCountDay.setAbsenceDay(dayCount);
						break;
					case ANNUAL_HOLIDAY:
						totalCountDay.setYearOffUsage(dayCount);
						break;
					case OFF_WORK:
						totalCountDay.setOffDay(dayCount);
						break;
					case SPECIAL:
						totalCountDay.setSpecialHoliday(dayCount);
						break;
					case YEARLY_RESERVED:
						totalCountDay.setHeavyHolDay(dayCount);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * 回数を計算する
	 * @param employeeId
	 * @param dateRange
	 * @param totalCountDay
	 */
	private void calculateDayCount(int employeeId, DateRange dateRange, TotalCountDay totalCountDay) {
		// 勤怠項目ID = 592 or 598 or 604 or 610
		List<Integer> lstAttendanceId = new ArrayList<>();
		lstAttendanceId.add(592);
		lstAttendanceId.add(598);
		lstAttendanceId.add(604);
		lstAttendanceId.add(610);
		
		// ドメインモデル「画面で利用できる勤怠項目一覧」を取得する
		AttendanceType attendanceType = attendanceTypeRepository.getItemByScreenUseAtr(AppContexts.user().companyId(), 19)
				.stream().filter(x -> lstAttendanceId.contains(x.getAttendanceItemId()) && 
									   x.getAttendanceItemType().value == 1).findFirst().get();
		
		List<String> lstEmployeeId = new ArrayList<>();
		lstEmployeeId.add(String.valueOf(employeeId));
		
		List<AttendanceResultImport> attendanceResultImport = attendanceResultAdapter.getValueOf(lstEmployeeId, new DatePeriod(dateRange.getStartDate(), dateRange.getEndDate()), lstAttendanceId);
		
		
		for (NumberOfItem item : NumberOfItem.values()) {
			List<GeneralDate> lstDate = dateRange.toListDate();
			
			// Variable to count by item through date list
			int dayCount = 0;
			
			for (GeneralDate currentDate : lstDate) {
				// Literally 0-1 result
				Optional<AttendanceResultImport> optAttendanceResult = attendanceResultImport.stream().filter(x -> x.getWorkingDate().compareTo(currentDate) == 0).findFirst();
				if (optAttendanceResult.isPresent()) {
					AttendanceResultImport attendanceResult = optAttendanceResult.get();
					
					// Count by item attendance id
					dayCount += attendanceResult.getAttendanceItems().stream().filter(x -> x.getItemId() == item.attendanceId).
									filter(x -> x.getValue() != null && Integer.parseInt(x.getValue()) != 0).count();
				}
			}
			
			switch (item) {
			case Late_Come_1:
			case Late_Come_2:
				totalCountDay.setLateComeDay(totalCountDay.getLateComeDay() + dayCount);
				break;
			case Early_Leave_1:
			case Early_Leave_2:
				totalCountDay.setEarlyLeaveDay(totalCountDay.getEarlyLeaveDay() + dayCount);
				break;
			}
		}
	}
	
	/**
	 * 日数計を合算する
	 * @param employeeId
	 * @param dateRange
	 * @param totalCountDay
	 */
	public void calculateAllDayCount(int employeeId, DateRange dateRange, TotalCountDay totalCountDay) {
		calculatePredeterminedDay(employeeId, dateRange, totalCountDay);
		calculateNonPredeterminedDay(employeeId, dateRange, totalCountDay);
		calculateDayCount(employeeId, dateRange, totalCountDay);
	}
}
