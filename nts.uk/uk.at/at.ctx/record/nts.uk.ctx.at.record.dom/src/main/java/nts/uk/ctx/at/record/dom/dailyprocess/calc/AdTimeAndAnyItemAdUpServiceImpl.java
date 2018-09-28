package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.attendanceitem.StoredProcdureProcess;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.AttendanceLeavingGateOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.PCLogOnInfoOfDaily;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.AttendanceLeavingGateOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.attendanceleavinggate.repo.PCLogOnInfoOfDailyRepo;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 日別実績の勤怠時間と任意項目を同時更新し、ストアドを実行するためのサービス
 * @author keisuke_hoshina
 *
 */
@Stateless
public class AdTimeAndAnyItemAdUpServiceImpl implements AdTimeAndAnyItemAdUpService {

	@Inject
	private AttendanceTimeRepository attendanceTimeRepository;
	
	@Inject
	private AnyItemValueOfDailyRepo anyItemValueOfDailyRepo;
	
//	@Inject
//	private AdTimeAnyItemStoredForDailyCalc adTimeAnyItemStoredForDailyCalc;
	
	@Inject
	private StoredProcdureProcess storedProcedureProcess;
	
	@Inject
	private WorkInformationRepository workInfo;
	
	@Inject
	private PCLogOnInfoOfDailyRepo pcLogon;
	
	@Inject
	private AttendanceLeavingGateOfDailyRepo attendanceGate;
	
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeave;
	
	@Inject
	private WorkInformationRepository workInformationRepository;
	
	@Override
	public void addAndUpdate(String empId ,GeneralDate ymd,
							Optional<AttendanceTimeOfDailyPerformance> attendanceTime, Optional<AnyItemValueOfDaily> anyItem) {
		workInfo.find(empId, ymd).ifPresent(wi -> {
			Optional<PCLogOnInfoOfDaily> pc = pcLogon.find(empId, ymd);
			Optional<AttendanceLeavingGateOfDaily> al = attendanceGate.find(empId, ymd);
			Optional<TimeLeavingOfDailyPerformance> tl = timeLeave.findByKey(empId, ymd);
			wi.changeCalcState(CalculationState.Calculated);
			IntegrationOfDaily daily = new IntegrationOfDaily(wi, null, null, Optional.empty(), pc, new ArrayList<>(),
								Optional.empty(), new ArrayList<>(), attendanceTime, Optional.empty(), tl,
								Optional.empty(), Optional.empty(), al, anyItem, new ArrayList<>(), Optional.empty(), new ArrayList<>());
			
			addAndUpdate(daily);
		});
//		//勤怠時間更新
//		if(attendanceTime.isPresent()) {
//			if(attendanceTimeRepository.find(empId, ymd).isPresent()) {
//				attendanceTimeRepository.update(attendanceTime.get());
//			}
//			else {
//				attendanceTimeRepository.add(attendanceTime.get());
//			}
//		}
//		//任意項目更新
//		if(anyItem.isPresent()) {
//			if(anyItemValueOfDailyRepo.find(anyItem.get().getEmployeeId(), anyItem.get().getYmd()).isPresent()){
//				anyItemValueOfDailyRepo.update(anyItem.get());
//			}
//			else {
//				anyItemValueOfDailyRepo.add(anyItem.get());
//			}
//					
//		}
//		//ストアド実行
//		if(empId != null && ymd != null)
//			adTimeAnyItemStoredForDailyCalc.storeAd(empId, ymd);
		
	}
	
	@Override
	public void addAndUpdate(List<IntegrationOfDaily> daily) {
		addAndUpdate(daily, null);
	}
	
	@Override
	public void addAndUpdate(List<IntegrationOfDaily> daily, Map<WorkTypeCode, WorkType> workTypes) {
		storedProcedureProcess.dailyProcessing(daily, workTypes).stream().forEach(d -> {
			//勤怠時間更新
			d.getAttendanceTimeOfDailyPerformance().ifPresent(at -> {
				attendanceTimeRepository.update(at);
			});
			//任意項目更新
			d.getAnyItemValue().ifPresent(ai -> {
				if(anyItemValueOfDailyRepo.find(ai.getEmployeeId(), ai.getYmd()).isPresent()){
					anyItemValueOfDailyRepo.update(ai);
				} else {
					anyItemValueOfDailyRepo.add(ai);
				}
			});
		});
	}
	
	@Override
	public void addAndUpdate(IntegrationOfDaily daily) {
		addAndUpdate(Arrays.asList(daily));
	}

}
