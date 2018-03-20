package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReasonNotReflectRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReflectedStateRecord;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;

@Stateless
public class PreOvertimeReflectServiceImpl implements PreOvertimeReflectService {
	@Inject
	private PreOvertimeReflectProcess priorProcess;
	@Inject
	private WorkInformationRepository workRepository;
	@Inject
	private StartEndTimeOffReflect startEndtimeOffReflect;
	@Override
	public ApplicationReflectOutput overtimeReflect(PreOvertimeParameter param) {
		try {
			ApplicationReflectOutput output = new ApplicationReflectOutput(param.getOvertimePara().getReflectedState(), param.getOvertimePara().getReasonNotReflect());
			//予定勤種・就時の反映
			priorProcess.workTimeWorkTimeUpdate(param);
			//勤種・就時の反映
			boolean changeFlg = priorProcess.changeFlg(param);
			//予定勤種・就時反映後の予定勤種・就時を取得する
			//勤種・就時反映後の予定勤種・就時を取得する
			Optional<WorkInfoOfDailyPerformance> optDailyData = workRepository.find(param.getEmployeeId(), param.getDateInfo());
			if(!optDailyData.isPresent()) {
				return output;
			}
			//予定開始終了時刻の反映
			WorkInfoOfDailyPerformance dailyData = optDailyData.get();
			priorProcess.startAndEndTimeReflectSche(param, changeFlg, dailyData);
			//開始終了時刻の反映
			startEndtimeOffReflect.startEndTimeOffReflect(param, dailyData);
			//残業時間の反映
			priorProcess.getReflectOfOvertime(param);
			//所定外深夜時間の反映
			priorProcess.overTimeShiftNight(param.getEmployeeId(), param.getDateInfo(), param.isTimeReflectFlg(), param.getOvertimePara().getOverTimeShiftNight());
			//フレックス時間の反映
			priorProcess.reflectOfFlexTime(param.getEmployeeId(), param.getDateInfo(), param.isTimeReflectFlg(), param.getOvertimePara().getFlexExessTime());
			
			//日別実績の修正からの計算
			//TODO nho
			
			output.setReflectedState(ReflectedStateRecord.REFLECTED);
			output.setReasonNotReflect(ReasonNotReflectRecord.WORK_CONFIRMED);
			return output;
	
		} catch (Exception ex) {
			return new ApplicationReflectOutput(param.getOvertimePara().getReflectedState(), param.getOvertimePara().getReasonNotReflect());
		}
	}

}
