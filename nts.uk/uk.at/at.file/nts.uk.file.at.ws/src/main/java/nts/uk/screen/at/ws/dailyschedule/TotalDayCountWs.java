package nts.uk.screen.at.ws.dailyschedule;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceType;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.adapter.dailyperformance.DailyPerformanceAdapter;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.file.at.app.export.dailyschedule.totalsum.DayType;
import nts.uk.file.at.app.export.dailyschedule.totalsum.TotalCountDay;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.workinfomation.WorkInfoOfDailyPerformanceDetailDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * Total day count class
 * @author HoangNDH
 *
 */
@Stateless
public class TotalDayCountWs {
	@Inject
	AttendanceTypeRepository attendanceTypeRepository;
	
	@Inject
	WorkTypeRepository workTypeRepository;
	
	@Inject
	DailyPerformanceScreenRepo dailyPerformanceRepo;
	
	/**
	 * 所定日数を計算する
	 * @param employeeId
	 * @param lstDate
	 */
	public void calculatePredeterminedDay(int employeeId, DateRange dateRange, TotalCountDay totalCountDay) {
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
	public void calculateNonPredeterminedDay(int employeeId, DateRange dateRange, TotalCountDay totalCountDay) {
		// ドメインモデル「画面で利用できる勤怠項目一覧」を取得する
		AttendanceType attendanceType = attendanceTypeRepository.getItemByScreenUseAtr(AppContexts.user().companyId(), 19)
				.stream().filter(x -> x.getAttendanceItemId() == 58 && x.getAttendanceItemType().value == 1).findFirst().get();
		
		for (DayType dayType : DayType.values()) {
			// ドメインモデル「勤務種類」を取得する
			
			// 
		}
	}
}
