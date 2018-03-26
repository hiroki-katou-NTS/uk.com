package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.servicedto.TimeReflectScheDto;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.ScheduleEditState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleStateRepository;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class StartTimeReflectScheServiceImpl implements StartTimeReflectScheService{
	@Inject
	private BasicScheduleRepository basicReposi;
	@Inject
	private WorkScheduleStateRepository workScheReposi;

	@Override
	public void updateStartTimeRflect(TimeReflectScheDto startTimeDto) {
		//勤務予定基本情報
		Optional<BasicSchedule> optBasicScheByDate = basicReposi.find(startTimeDto.getEmployeeId(), startTimeDto.getDateInfo());
		if(!optBasicScheByDate.isPresent()) {
			return;
		}		
		 
		//開始時刻を反映する
		BasicSchedule basicScheByDate = optBasicScheByDate.get();
		List<WorkScheduleTimeZone> workScheduleTimeZones = basicScheByDate.getWorkScheduleTimeZones();
		WorkScheduleTimeZone timeZoneData = workScheduleTimeZones.stream()
				.filter(x -> x.getScheduleCnt() == startTimeDto.getFrameNumber())
				.collect(Collectors.toList()).get(0);
		
		timeZoneData.updateTime(new TimeWithDayAttr(startTimeDto.getStartTime()), timeZoneData.getScheduleEndClock());
		basicReposi.update(basicScheByDate);
		
		//開始時刻の編集状態を更新する  勤務予定項目状態
		//予定項目ID=予定開始時刻(予定勤務回数=INPUT．枠番号)の項目ID 3, 5
		WorkScheduleState sateData = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION, 3, startTimeDto.getDateInfo(), startTimeDto.getEmployeeId());
		workScheReposi.updateScheduleEditState(sateData);
		sateData = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION, 5, startTimeDto.getDateInfo(), startTimeDto.getEmployeeId());
		workScheReposi.updateScheduleEditState(sateData);
	}

}
