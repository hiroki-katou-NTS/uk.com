package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.editstate.repository.EditStateOfDailyPerformanceRepository;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;

@Stateless
public class PriorReflectServiceImpl implements PriorReflectService {
	@Inject
	private PriorReflectProcess priorProcess;
	@Inject
	private WorkInformationRepository workRepository;
	@Override
	public OvertimeReflectOutput overtimeReflect(PreOvertimeParameter param) {
		OvertimeReflectOutput output = new OvertimeReflectOutput(param.getOvertimePara().getReflectedState(), param.getOvertimePara().getReasonNotReflect());
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
		return null;
	}

}
