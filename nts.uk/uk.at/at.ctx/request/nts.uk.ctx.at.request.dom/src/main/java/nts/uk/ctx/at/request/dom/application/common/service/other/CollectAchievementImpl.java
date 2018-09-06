package nts.uk.ctx.at.request.dom.application.common.service.other;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedule.basicschedule.ScBasicScheduleImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.schedulemanagementcontrol.ScheduleManagementControlAdapter;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.WorkTimeOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.WorkTypeOutput;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CollectAchievementImpl implements CollectAchievement {
	
	@Inject
	private RecordWorkInfoAdapter recordWorkInfoAdapter;
	
	@Inject
	private ScheduleManagementControlAdapter scheduleManagementControlAdapter;

	@Inject
	private ScBasicScheduleAdapter scBasicScheduleAdapter;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkTimeSettingRepository WorkTimeRepository;
	
	@Override
	public AchievementOutput getAchievement(String companyID, String applicantID, GeneralDate appDate) {
		RecordWorkInfoImport recordWorkInfoImport = recordWorkInfoAdapter.getRecordWorkInfo(applicantID, appDate);
		WorkTypeOutput workTypeOutput = null;
		WorkTimeOutput workTimeOutput = null;
		Integer startTime1 = null, endTime1 = null, startTime2 = null, endTime2 = null;
		if(Strings.isBlank(recordWorkInfoImport.getWorkTypeCode()) && Strings.isBlank(recordWorkInfoImport.getWorkTimeCode())){
			if(scheduleManagementControlAdapter.isScheduleManagementAtr(applicantID).equals(Boolean.FALSE)){
				return new AchievementOutput(appDate, new WorkTypeOutput("", ""), new WorkTimeOutput("", ""), null, null, null, null);
			}
			Optional<ScBasicScheduleImport> opScBasicScheduleImport = scBasicScheduleAdapter.findByID(applicantID, appDate);
			if(!opScBasicScheduleImport.isPresent()){
				return new AchievementOutput(appDate, new WorkTypeOutput("", ""), new WorkTimeOutput("", ""), null, null, null, null);
			}
			ScBasicScheduleImport scBasicScheduleImport = opScBasicScheduleImport.get();
			startTime1 = scBasicScheduleImport.getScheduleStartClock1();
			endTime1 = scBasicScheduleImport.getScheduleEndClock1();
			startTime2 = scBasicScheduleImport.getScheduleStartClock2();
			endTime2 = scBasicScheduleImport.getScheduleEndClock2();
			workTypeOutput = new WorkTypeOutput(scBasicScheduleImport.getWorkTypeCode(), "");
			workTimeOutput = new WorkTimeOutput(scBasicScheduleImport.getWorkTimeCode(), "");
		} else {
			startTime1 = recordWorkInfoImport.getAttendanceStampTimeFirst();
			endTime1 = recordWorkInfoImport.getLeaveStampTimeFirst();
			startTime2 = recordWorkInfoImport.getAttendanceStampTimeSecond();
			endTime2 = recordWorkInfoImport.getLeaveStampTimeSecond();
			workTypeOutput = new WorkTypeOutput(recordWorkInfoImport.getWorkTypeCode(), "");
			workTimeOutput = new WorkTimeOutput(recordWorkInfoImport.getWorkTimeCode(), "");
		}
		workTypeOutput = workTypeRepository.findByPK(companyID, workTypeOutput.getWorkTypeCode())
			.map(x -> new WorkTypeOutput(x.getWorkTypeCode().v(), x.getName().v()))
			.orElse(new WorkTypeOutput("", ""));
		workTimeOutput = WorkTimeRepository.findByCode(companyID, workTimeOutput.getWorkTimeCD())
				.map(x -> new WorkTimeOutput(x.getWorktimeCode().v(), x.getWorkTimeDisplayName().getWorkTimeName().v()))
				.orElse(new WorkTimeOutput("", ""));
		return new AchievementOutput(appDate, workTypeOutput, workTimeOutput, startTime1, endTime1, startTime2, endTime2);
	}

}
