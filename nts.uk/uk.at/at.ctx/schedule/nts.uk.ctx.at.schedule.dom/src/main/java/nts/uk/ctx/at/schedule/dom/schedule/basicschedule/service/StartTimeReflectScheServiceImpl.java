package nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.service.servicedto.TimeReflectScheDto;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.BounceAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.ScheduleEditState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleStateRepository;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class StartTimeReflectScheServiceImpl implements StartEndTimeReflectScheService{
	@Inject
	private BasicScheduleRepository basicReposi;
	@Inject
	private WorkScheduleStateRepository workScheReposi;

	@Override
	public void updateStartTimeRflect(TimeReflectScheDto timeDto) {
		//勤務予定基本情報
		Optional<BasicSchedule> optBasicScheByDate = basicReposi.find(timeDto.getEmployeeId(), timeDto.getDateInfo());
		if(!optBasicScheByDate.isPresent()
				|| (!timeDto.isUpdateStart() && !timeDto.isUpdateEnd())) {
			return;
		}		
		 
		
		BasicSchedule basicScheByDate = optBasicScheByDate.get();
		List<WorkScheduleTimeZone> workScheduleTimeZones = basicScheByDate.getWorkScheduleTimeZones();
		WorkScheduleTimeZone timeZoneData = null;
		if(workScheduleTimeZones.isEmpty()) {
			if(timeDto.isUpdateStart() && timeDto.isUpdateEnd()) {
				timeZoneData = new WorkScheduleTimeZone(1, new TimeWithDayAttr(timeDto.getStartTime() == null ? 0 : timeDto.getStartTime()),
						new TimeWithDayAttr(timeDto.getEndTime()== null ? 0 : timeDto.getEndTime()), BounceAtr.DIRECT_BOUNCE);
				List<WorkScheduleTimeZone> lstTimeZoneData = new ArrayList<>();
				lstTimeZoneData.add(timeZoneData);
				basicScheByDate.setWorkScheduleTimeZones(lstTimeZoneData);
			} else {
				return;
			}
			
		} else {
			List<WorkScheduleTimeZone> lstTimeZoneData = workScheduleTimeZones.stream()
					.filter(x -> x.getScheduleCnt() == timeDto.getFrameNumber())
					.collect(Collectors.toList());
			if(lstTimeZoneData.isEmpty()) {
				return;
			}
			timeZoneData = lstTimeZoneData.get(0);	
			//開始時刻を反映する
			//終了時刻の反映
			timeZoneData.updateTime(timeDto.isUpdateStart() ? new TimeWithDayAttr(timeDto.getStartTime() == null ? 0 : timeDto.getStartTime()) : timeZoneData.getScheduleStartClock(), 
					timeDto.isUpdateEnd() ? new TimeWithDayAttr(timeDto.getEndTime() == null ? 0 : timeDto.getEndTime()) : timeZoneData.getScheduleEndClock());
		}
		
		
		basicReposi.update(basicScheByDate);
		
		//開始時刻の編集状態を更新する  勤務予定項目状態
		//予定項目ID=予定開始時刻(予定勤務回数=INPUT．枠番号)の項目ID 3, 5
		WorkScheduleState sateData = null;
		if(timeDto.getFrameNumber() == 1) {
			if(timeDto.isUpdateStart()) {
				sateData = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION, 3, timeDto.getDateInfo(), timeDto.getEmployeeId());
				workScheReposi.updateOrInsert(sateData);
			}
			if(timeDto.isUpdateEnd()) {
				sateData = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION, 4, timeDto.getDateInfo(), timeDto.getEmployeeId());	
				workScheReposi.updateOrInsert(sateData);
			}
				
		} else {
			if(timeDto.isUpdateStart()) {
				sateData = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION, 5, timeDto.getDateInfo(), timeDto.getEmployeeId());
				workScheReposi.updateOrInsert(sateData);
			}
			if(timeDto.isUpdateEnd()) {
				sateData = new WorkScheduleState(ScheduleEditState.REFLECT_APPLICATION, 6, timeDto.getDateInfo(), timeDto.getEmployeeId());	
				workScheReposi.updateOrInsert(sateData);
			}	
		}
		
	}

}
