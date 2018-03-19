package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.TimeReflectParameter;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingWork;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.shared.dom.worktype.service.AttendanceOfficeAtr;
import nts.uk.ctx.at.shared.dom.worktype.service.WorkTypeIsClosedService;

@Stateless
public class StartEndTimeOffReflectImpl implements StartEndTimeOffReflect{

	@Inject
	private WorkTypeIsClosedService worktypeService;
	@Inject 
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDaily;
	@Inject
	private ScheWorkUpdateService scheWorkUpdate;
	@Override
	public void startEndTimeOffReflect(PreOvertimeParameter param) {
		//INPUT．勤種反映フラグ(実績)をチェックする
		if(!param.isActualReflectFlg()) {
			return;
		}
		//自動打刻をクリアする
		
		
	}

	@Override
	public void clearAutomaticEmbossing(String employeeId, GeneralDate dateData, String worktypeCode,
			boolean isClearAuto, Integer timeData) {
		// INPUT．自動セット打刻をクリアフラグをチェックする
		if(!isClearAuto) {
			return;
		}
		//打刻自動セット区分を取得する
		if(!worktypeService.checkStampAutoSet(worktypeCode, AttendanceOfficeAtr.ATTENDANCE)) {
			//打刻元情報を取得する
			Optional<TimeLeavingOfDailyPerformance> optTimeLeaving = timeLeavingOfDaily.findByKey(employeeId, dateData);
			if(!optTimeLeaving.isPresent()) {
				return;
			}
			TimeLeavingOfDailyPerformance timeLeaving = optTimeLeaving.get();
			List<TimeLeavingWork> leavingStamp = timeLeaving.getTimeLeavingWorks();
			if(leavingStamp.isEmpty()) {
				return;
			}
			List<TimeLeavingWork> lstLeavingStamp1 = leavingStamp.stream()
					.filter(x -> x.getWorkNo().v() == 1).collect(Collectors.toList());
			if(lstLeavingStamp1.isEmpty()) {
				return;
			}
			TimeLeavingWork leavingStamp1 = lstLeavingStamp1.get(0);
			WorkStamp workStamp = leavingStamp1.getLeaveStamp().get().getStamp().get();
			//出勤の打刻元情報が「打刻自動セット(個人情報)、直行直帰申請」
			if(workStamp.getStampSourceInfo() == StampSourceInfo.STAMP_AUTO_SET_PERSONAL_INFO
					|| workStamp.getStampSourceInfo() == StampSourceInfo.GO_STRAIGHT_APPLICATION) {
				//開始時刻の反映
				TimeReflectParameter timeReflect = new TimeReflectParameter(employeeId, dateData, timeData, 1, true);
			}
		}
	}
}
