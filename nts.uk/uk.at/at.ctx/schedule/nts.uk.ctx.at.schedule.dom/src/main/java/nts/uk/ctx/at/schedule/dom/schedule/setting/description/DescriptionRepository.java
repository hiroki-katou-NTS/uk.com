package nts.uk.ctx.at.schedule.dom.schedule.setting.description;

import java.util.List;

public interface DescriptionRepository {
	List<ScheduleAuthority> findByAut();
	
	List<ScheduleCommon> findByCom();
	
	List<ScheduleDate> findByDate();
	
	List<ScheduleShift> findByShift();
	
	List<ScheduleWorkplace> findByWork();
}
