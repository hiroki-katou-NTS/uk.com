package nts.uk.ctx.at.schedule.dom.appreflectprocess.service.appforleave;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.appreflectprocess.service.CommonReflectParamSche;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.StartEndTimeReflectScheService;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.servicedto.TimeReflectScheDto;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.ScheduleEditState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleStateRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class ForleaveReflectScheImpl implements ForleaveReflectSche{
	@Inject
	private BasicScheduleRepository basicSche;
	@Inject
	private WorkScheduleStateRepository workScheReposi;
	@Inject
	private BasicScheduleService basicService;
	@Inject
	private StartEndTimeReflectScheService startEndTimeScheService;
	@Override
	public boolean forlearveReflectSche(CommonReflectParamSche reflectParam) {
		try {
			//勤種の反映
			//勤務種類を反映する
			//ドメインモデル「勤務予定基本情報」を取得する			
			basicSche.changeWorkTypeTime(reflectParam.getEmployeeId(), reflectParam.getDatePara(), reflectParam.getWorktypeCode(), reflectParam.getWorkTimeCode());
			//勤務種類の編集状態を更新する
			WorkScheduleState scheData = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION,
					1,
					reflectParam.getDatePara(),
					reflectParam.getEmployeeId());
			workScheReposi.updateScheduleEditState(scheData);
			//時刻の反映
			return true;
		} catch (Exception e) {
			return false;
		}
		
		
	}
	@Override
	public void reflectTime(String employeeId, GeneralDate dateData, String workTypeCode, Integer startTime, Integer endTime) {
		//1日半日出勤・1日休日系の判定
		WorkStyle checkworkDay = basicService.checkWorkDay(workTypeCode);
		if(checkworkDay == WorkStyle.ONE_DAY_REST) {
			startEndTimeScheService.updateStartTimeRflect(new TimeReflectScheDto(employeeId, dateData, startTime, endTime, 1, true, true));
		}
	}	
}


