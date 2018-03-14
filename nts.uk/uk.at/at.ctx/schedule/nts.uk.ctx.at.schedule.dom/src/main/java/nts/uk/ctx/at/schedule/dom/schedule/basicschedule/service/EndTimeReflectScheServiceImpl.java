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
public class EndTimeReflectScheServiceImpl implements EndTimeReflectScheService{
	@Inject
	private BasicScheduleRepository basicReposi;
	@Inject
	private WorkScheduleStateRepository workScheReposi;
	@Override
	public void updateEndTimeRflect(TimeReflectScheDto timeDto) {
		//勤務予定基本情報
		Optional<BasicSchedule> optBasicScheByDate = basicReposi.find(timeDto.getEmployeeId(), timeDto.getDateInfo());
		if(!optBasicScheByDate.isPresent()) {
			return;
		}
		BasicSchedule basicScheByDate = optBasicScheByDate.get();
		List<WorkScheduleTimeZone> workScheduleTimeZones = basicScheByDate.getWorkScheduleTimeZones();
		WorkScheduleTimeZone timeZoneData = workScheduleTimeZones.stream()
				.filter(x -> x.getScheduleCnt() == timeDto.getFrameNumber())
				.collect(Collectors.toList()).get(0);
		
		timeZoneData.updateTime(timeZoneData.getScheduleStartClock(), new TimeWithDayAttr(timeDto.getEndTime()));
		//終了時刻を反映する
		basicReposi.update(basicScheByDate);
		//終了時刻の編集状態を更新する
		//予定項目ID=予定開始時刻(予定勤務回数=INPUT．枠番号)の項目ID 4, 6
		WorkScheduleState sateData = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION, 4, timeDto.getDateInfo(), timeDto.getEmployeeId());
		workScheReposi.updateScheduleEditState(sateData);
		sateData = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION, 6, timeDto.getDateInfo(), timeDto.getEmployeeId());
		workScheReposi.updateScheduleEditState(sateData);
	}

}
