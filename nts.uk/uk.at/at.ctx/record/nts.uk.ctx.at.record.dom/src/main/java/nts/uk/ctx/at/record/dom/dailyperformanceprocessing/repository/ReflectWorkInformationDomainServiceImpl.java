package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;

@Stateless
public class ReflectWorkInformationDomainServiceImpl implements ReflectWorkInformationDomainService{
	@Inject
	private WorkInformationRepository workInforRepo;
	/*
	// true (reflect) and false (no reflect)
	@Override
	public boolean changeWorkInformation(String employeeId, GeneralDate date) {
		
		// 1* lấy từ a nam
		Optional<WorkInfoOfDailyPerformance> WorkInfoOptional = this.workInforRepo.find(employeeId,
				date);
		
		//1*
		WorkInformation recordWorkInformation = WorkInfoOptional.get().getRecordWorkInformation();
		// 1* Lấy 休日出勤時の勤務情報  trả về 勤務情報なし  , 公休出勤時 ....
		//(fixed)
		String result = "勤務情報なし";
		// 1* 
		
		if("勤務情報なし".equals(result)){
			return false;
		}
		return true;
	}
	*/
	// true (reflect) and false (no reflect)
	@Override
	public boolean changeWorkInformation(WorkInfoOfDailyPerformance workInfo) {
		WorkInformation recordWorkInformation = workInfo.getRecordWorkInformation();
		// 1* Lấy 休日出勤時の勤務情報  trả về 勤務情報なし  , 公休出勤時 ....
				//(fixed)
				String result = "勤務情報なし";
				// 1* 
				
				if("勤務情報なし".equals(result)){
					return false;
				}
				return true;
	}

}
