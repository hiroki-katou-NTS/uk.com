package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ApplicationReflectOutput;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReasonNotReflectRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReflectedStateRecord;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ReflectParameter;
import nts.uk.ctx.at.record.dom.workinformation.service.reflectprocess.ScheWorkUpdateService;


@Stateless
public class AfterOvertimeReflectServiceImpl implements AfterOvertimeReflectService {
	@Inject
	private AfterOvertimeReflectProcess afterOverTimeReflect;
	@Inject
	private PreOvertimeReflectProcess preAfterOvertimeReflectProcess;
	@Inject
	private WorkInformationRepository workRepository;
	@Inject
	private ScheWorkUpdateService scheWorkUpdate;
	@Override
	public ApplicationReflectOutput reflectAfterOvertime(OvertimeParameter overtimePara) {
		ApplicationReflectOutput output = new ApplicationReflectOutput(overtimePara.getOvertimePara().getReflectedState(), 
				overtimePara.getOvertimePara().getReasonNotReflect());
		//予定勤種・就時の反映
		afterOverTimeReflect.checkScheReflect(overtimePara);
		//勤種・就時の反映
		boolean isWorkReflect = preAfterOvertimeReflectProcess.changeFlg(overtimePara);
		//予定勤種・就時反映後の予定勤種・就時を取得する
		//勤種・就時反映後の予定勤種・就時を取得する
		Optional<WorkInfoOfDailyPerformance> optDailyData = workRepository.find(overtimePara.getEmployeeId(),
				overtimePara.getDateInfo());		
		if(!optDailyData.isPresent()) {
			return output;
		}
		WorkInfoOfDailyPerformance dailyData = optDailyData.get();
		//予定開始終了時刻の反映
		WorkTimeTypeOutput workTimeTypeScheData = new WorkTimeTypeOutput( dailyData.getScheduleWorkInformation().getWorkTimeCode().v(), dailyData.getScheduleWorkInformation().getWorkTypeCode().v());		
		afterOverTimeReflect.checkScheWorkStarEndReflect(overtimePara, isWorkReflect, workTimeTypeScheData);
		//開始終了時刻の反映
		WorkTimeTypeOutput workTimeTypeRecordData = new WorkTimeTypeOutput(dailyData.getRecordWorkInformation().getWorkTimeCode().v(), dailyData.getRecordWorkInformation().getWorkTypeCode().v());
		afterOverTimeReflect.recordStartEndReflect(overtimePara, workTimeTypeRecordData);
		//残業時間の反映
		afterOverTimeReflect.reflectOvertimeFrame(overtimePara);
		//所定外深夜時間の反映
		afterOverTimeReflect.reflectTimeShiftNight(overtimePara.getEmployeeId(), overtimePara.getDateInfo(), overtimePara.getOvertimePara().getOverTimeShiftNight());
		//フレックス時間の反映
		//INPUT．フレックス時間をチェックする
		if(overtimePara.getOvertimePara().getFlexExessTime() > 0) {
			scheWorkUpdate.updateFlexTime(overtimePara.getEmployeeId(), overtimePara.getDateInfo(), overtimePara.getOvertimePara().getFlexExessTime(), false);
		}
		return null;
	}
}
