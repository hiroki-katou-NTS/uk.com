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
	public void addAndUpdate(AttendanceTimeOfDailyPerformance attendanceTime, Optional<AnyItemValueOfDaily> anyItem) {
		String empId = attendanceTime != null ? attendanceTime.getEmployeeId()
											  : anyItem.isPresent() ? anyItem.get().getEmployeeId()
													  			    : null;
		GeneralDate ymd = attendanceTime != null ? attendanceTime.getYmd()
				  								 : anyItem.isPresent() ? anyItem.get().getYmd()
				  										 		       : null;
		//勤怠時間更新
		if(attendanceTime != null) {
			if(attendanceTimeRepository.find(attendanceTime.getEmployeeId(), attendanceTime.getYmd()).isPresent()) {
				attendanceTimeRepository.update(attendanceTime);
			}
			else {
				attendanceTimeRepository.add(attendanceTime);
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
			adTimeAnyItemStoredForDailyCalc.storeAd(attendanceTime.getEmployeeId(), attendanceTime.getYmd());
		
	}

}
