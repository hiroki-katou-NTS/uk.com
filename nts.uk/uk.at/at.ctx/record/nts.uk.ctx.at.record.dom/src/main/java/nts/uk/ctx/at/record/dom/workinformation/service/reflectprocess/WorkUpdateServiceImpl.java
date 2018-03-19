package nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;
import nts.uk.ctx.at.record.dom.editstate.enums.EditStateSetting;
import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.ScheduleTimeSheet;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.WorkInformation;

@Stateless
public class WorkUpdateServiceImpl implements ScheWorkUpdateService{
	@Inject
	private WorkInformationRepository workRepository;
	@Inject
	private EditStateOfDailyPerformanceRepository dailyReposiroty;
	
	
	@Override
	public void updateWorkTimeType(ReflectParameter para, List<Integer> lstItem, boolean scheUpdate) {
		Optional<WorkInfoOfDailyPerformance> optDailyPerfor = workRepository.find(para.getEmployeeId(), para.getDateData());
		if(!optDailyPerfor.isPresent()) {
			return;
		}
		//日別実績の勤務情報
		WorkInfoOfDailyPerformance dailyPerfor = optDailyPerfor.get();
		WorkInformation workInfor = new WorkInformation(para.getWorkTimeCode(), para.getWorkTypeCode());
		if(scheUpdate) {
			dailyPerfor.setScheduleWorkInformation(workInfor);
			workRepository.updateByKey(dailyPerfor);
		} else {
			dailyPerfor.setRecordWorkInformation(workInfor);
		}
		
		//日別実績の編集状態
		List<EditStateOfDailyPerformance> lstDaily = new ArrayList<>();
		lstItem.stream().forEach(z -> {
			Optional<EditStateOfDailyPerformance> optItemData = dailyReposiroty.findByKeyId(para.getEmployeeId(), para.getDateData(), z);
			if(optItemData.isPresent()) {
				EditStateOfDailyPerformance itemData = optItemData.get();
				EditStateOfDailyPerformance data = new EditStateOfDailyPerformance(itemData.getEmployeeId(), 
						itemData.getAttendanceItemId(), itemData.getYmd(), 
						EditStateSetting.REFLECT_APPLICATION);
				lstDaily.add(data);
			}
		});
		
		if(!lstDaily.isEmpty()) {
			dailyReposiroty.updateByKey(lstDaily);
		}
		
	}
	@Override
	public void updateStartTimeOfReflect(TimeReflectParameter para) {
		//予定開始時刻を反映する
		//日別実績の勤務情報
		Optional<WorkInfoOfDailyPerformance> optDailyPerfor = workRepository.find(para.getEmployeeId(), para.getDateData());
		if(!optDailyPerfor.isPresent()) {
			return;
		}
		//日別実績の勤務情報
		WorkInfoOfDailyPerformance dailyPerfor = optDailyPerfor.get();
		if(dailyPerfor.getScheduleTimeSheets().isEmpty()) {
			return;
		}
		List<ScheduleTimeSheet> lstTimeSheetFrameNo = dailyPerfor.getScheduleTimeSheets().stream()
				.filter(x -> x.getWorkNo().v() == para.getFrameNo()).collect(Collectors.toList());
		if(lstTimeSheetFrameNo.isEmpty()) {
			return;
		}
		ScheduleTimeSheet timeSheetFrameNo = lstTimeSheetFrameNo.get(0);
		ScheduleTimeSheet timeSheet;
		if(para.isPreCheck()) {
			timeSheet = new ScheduleTimeSheet(timeSheetFrameNo.getWorkNo().v(), 
					para.getTime(), 
					timeSheetFrameNo.getLeaveWork().v());
		} else {
			timeSheet = new ScheduleTimeSheet(timeSheetFrameNo.getWorkNo().v(),
					timeSheetFrameNo.getAttendance().v(),
					para.getTime());
		}
		
		lstTimeSheetFrameNo.add(para.getFrameNo(), timeSheet); //can xac nhan lai
		dailyPerfor.setScheduleTimeSheets(lstTimeSheetFrameNo);
		workRepository.updateByKey(dailyPerfor);
		//日別実績の編集状態
		//予定開始時刻の項目ID
		int itemId;
		if(para.getFrameNo() == 1) {
			itemId = 3;
		} else {
			itemId = 5;
		}
		Optional<EditStateOfDailyPerformance> optItemData = dailyReposiroty.findByKeyId(para.getEmployeeId(), para.getDateData(), itemId);
		if(!optDailyPerfor.isPresent()) {
			return;
		}
		EditStateOfDailyPerformance itemData = optItemData.get();
		EditStateOfDailyPerformance data = new EditStateOfDailyPerformance(itemData.getEmployeeId(), 
				itemData.getAttendanceItemId(), itemData.getYmd(), 
				EditStateSetting.REFLECT_APPLICATION);
		List<EditStateOfDailyPerformance> lstData = new ArrayList();
		lstData.add(data);
		dailyReposiroty.updateByKey(lstData);
	}
	@Override
	public void updateReflectStartEndTime(TimeReflectParameter para) {
		//開始時刻を反映する 
		//TODO khong hieu, chua lam duoc
		//開始時刻の編集状態を更新する
		List<EditStateOfDailyPerformance> lstDaily = new ArrayList<>();
		List<Integer> lstItem = new ArrayList();
		lstItem.add(28);
		lstItem.add(29);
		lstItem.stream().forEach(z -> {
			Optional<EditStateOfDailyPerformance> optItemData = dailyReposiroty.findByKeyId(para.getEmployeeId(), para.getDateData(), z);
			if(optItemData.isPresent()) {
				EditStateOfDailyPerformance itemData = optItemData.get();
				EditStateOfDailyPerformance data = new EditStateOfDailyPerformance(itemData.getEmployeeId(), 
						itemData.getAttendanceItemId(), itemData.getYmd(), 
						EditStateSetting.REFLECT_APPLICATION);
				lstDaily.add(data);
			}
		});
		
		if(!lstDaily.isEmpty()) {
			dailyReposiroty.updateByKey(lstDaily);
		}
		
	}

}
