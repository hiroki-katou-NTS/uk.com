package nts.uk.ctx.at.request.app.find.application.workchange;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.RecordWorkInfoImport;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AppWorkChangeRecordWorkInfoFinder {

	@Inject
	private RecordWorkInfoAdapter recordWorkInfoAdapter;
	
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	public RecordWorkInfoDto getRecordWorkInfor(String appDate, String employeeID){
		// 申請者
		String employeeId = Strings.isBlank(employeeID) ? AppContexts.user().employeeId() : employeeID;
		String companyId = AppContexts.user().companyId();
		String workTypeName = "";
		String workTimeName = "";
		//(就業.contexts)「勤務実績」 imported (Request list No 05)s
		RecordWorkInfoImport recordInfo = recordWorkInfoAdapter.getRecordWorkInfo(employeeId, GeneralDate.fromString(appDate, DATE_FORMAT));
		// Get work type name & work time name
		Optional<WorkTimeSetting> workTime = workTimeRepository.findByCode(companyId, recordInfo.getWorkTimeCode());
		if (workTime.isPresent()) {
			workTimeName = workTime.get().getWorkTimeDisplayName().getWorkTimeName().v();
		}
		Optional<WorkType> workType = workTypeRepository.findByPK(companyId, recordInfo.getWorkTypeCode());
		if (workType.isPresent()) {
			workTypeName = workType.get().getName().v();
		}
		//TODO: (就業.contexts)「勤務実績」．休憩時刻1、休憩時刻2 ?????
		RecordWorkInfoDto dto = new RecordWorkInfoDto(
				appDate, 
				recordInfo.getWorkTypeCode(), 
				workTypeName,
				recordInfo.getWorkTimeCode(), 
				workTimeName, 
				recordInfo.getAttendanceStampTimeFirst(),
				recordInfo.getLeaveStampTimeFirst() , 
				recordInfo.getAttendanceStampTimeSecond() , 
				recordInfo.getLeaveStampTimeSecond(), 
				null, 
				null);
		//Test data
		//RecordWorkInfoDto dto = new RecordWorkInfoDto("2017/12/08", "001", "山田001", "AAC", "通常８ｈ", 420, 480, 600, 660, 360, 540);
		
		return dto;
	}
}
