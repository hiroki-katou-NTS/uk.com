package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AdTimeAnyItemStoredForDailyCalc;
import nts.uk.ctx.at.record.dom.actualworkinghours.repository.AttendanceTimeRepository;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDailyRepo;

/**
 * 日別実績の勤怠時間と任意項目を同時更新し、ストアドを実行するためのサービス
 * @author keisuke_hoshina
 *
 */
@Stateless
public class AdTimeAndAnyItemAdUpServiceImpl implements AdTimeAndAnyItemAdUpService{

	@Inject
	private AttendanceTimeRepository attendanceTimeRepository;
	
	@Inject
	private AnyItemValueOfDailyRepo anyItemValueOfDailyRepo;
	
	@Inject
	private AdTimeAnyItemStoredForDailyCalc adTimeAnyItemStoredForDailyCalc;
	
	@Override
	public void addAndUpdate(String empId ,GeneralDate ymd,
							Optional<AttendanceTimeOfDailyPerformance> attendanceTime, Optional<AnyItemValueOfDaily> anyItem) {
		//勤怠時間更新
		if(attendanceTime.isPresent()) {
			if(attendanceTimeRepository.find(empId, ymd).isPresent()) {
				attendanceTimeRepository.update(attendanceTime.get());
			}
			else {
				attendanceTimeRepository.add(attendanceTime.get());
			}
		}
		//任意項目更新
		if(anyItem.isPresent()) {
			if(anyItemValueOfDailyRepo.find(anyItem.get().getEmployeeId(), anyItem.get().getYmd()).isPresent()){
				anyItemValueOfDailyRepo.update(anyItem.get());
			}
			else {
				anyItemValueOfDailyRepo.add(anyItem.get());
			}
					
		}
		//ストアド実行
		if(empId != null && ymd != null)
			adTimeAnyItemStoredForDailyCalc.storeAd(empId, ymd);
		
	}

}
