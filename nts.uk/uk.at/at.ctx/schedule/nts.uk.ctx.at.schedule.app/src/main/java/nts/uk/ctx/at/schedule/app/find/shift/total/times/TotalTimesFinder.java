package nts.uk.ctx.at.schedule.app.find.shift.total.times;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.app.find.shift.total.times.dto.TotalConditionDto;
import nts.uk.ctx.at.schedule.app.find.shift.total.times.dto.TotalSubjectsDto;
import nts.uk.ctx.at.schedule.app.find.shift.total.times.dto.TotalTimesDto;
import nts.uk.ctx.at.schedule.dom.shift.total.times.TotalTimesRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class TotalTimesFinder {
	@Inject
	private TotalTimesRepository totalTimesRepo;
	
	public List<TotalTimesDto> getAllTotalTimes(){
		String companyId = AppContexts.user().companyId();
		List<TotalTimesDto> lst = this.totalTimesRepo.getAllTotalTimes(companyId).stream()
				.map(data -> new TotalTimesDto(data.getTotalCountNo().intValue(), data.getCountAtr().toName(), data.getUseAtr().value
						, data.getTotalTimesName().v(), data.getTotalTimesABName().v(), data.getSummaryAtr().toName()
						, new TotalConditionDto(data.getTotalCondition().getUpperLimitSettingAtr(), data.getTotalCondition().getLowerLimitSettingAtr(), data.getTotalCondition().getThresoldUpperLimit().v(), data.getTotalCondition().getThresoldLowerLimit().v())
						, new TotalSubjectsDto(data.getTotalSubjects().getWorkTypeCode().v(), data.getTotalSubjects().getWorkTypeAtr())))
				.collect(Collectors.toList());
		return lst;
	}
}
