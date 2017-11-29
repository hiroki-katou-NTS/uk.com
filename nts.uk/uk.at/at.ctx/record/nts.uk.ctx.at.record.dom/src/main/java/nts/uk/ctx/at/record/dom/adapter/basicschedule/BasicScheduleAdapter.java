package nts.uk.ctx.at.record.dom.adapter.basicschedule;

import java.util.List;

public interface BasicScheduleAdapter {
	
	List<BasicScheduleSidDto> findAllBasicSchedule();
	
	List<WorkScheduleSidImport> findAllWorkSchedule();

}
