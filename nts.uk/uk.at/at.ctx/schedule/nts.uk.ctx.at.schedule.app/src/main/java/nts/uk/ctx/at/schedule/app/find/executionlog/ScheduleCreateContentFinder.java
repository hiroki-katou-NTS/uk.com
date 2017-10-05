package nts.uk.ctx.at.schedule.app.find.executionlog;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.executionlog.dto.ScheduleCreateContentDto;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContent;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleCreateContentRepository;

@Stateless
public class ScheduleCreateContentFinder {
	
	@Inject
	ScheduleCreateContentRepository scheduleCreateContentRepository; 
	
	public ScheduleCreateContentDto findByExecutionId(String executionId){
		Optional<ScheduleCreateContent> op = scheduleCreateContentRepository.findByExecutionId(executionId);
		if(op.isPresent())
		{
			ScheduleCreateContentDto dto = new ScheduleCreateContentDto();
			op.get().saveToMemento(dto);
			return dto;
		}
		return null;
	}
}
