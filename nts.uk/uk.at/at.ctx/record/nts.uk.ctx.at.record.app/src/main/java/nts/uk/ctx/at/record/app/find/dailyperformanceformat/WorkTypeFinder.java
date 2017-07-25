package nts.uk.ctx.at.record.app.find.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.WorkTypeDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.WorkType;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * 
 * @author nampt 勤務種別
 *
 */
@Stateless
public class WorkTypeFinder {
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	public List<WorkTypeDto> findAll(){
		LoginUserContext login = AppContexts.user();
		String companyId = login.companyId();
		
		List<WorkType> workTypes = workTypeRepository.findAll(companyId);
		
		if (workTypes.isEmpty()) {
			throw new BusinessException("#Msg_242");
		} 
				
		List<WorkTypeDto> workTypeDtos = workTypes.stream().map(item -> {return new WorkTypeDto(item.getWorkTypeCode().v(), item.getWorkTypeName().v());})
				.collect(Collectors.toList());
		workTypeDtos.sort((e2,e1)-> Integer.parseInt(e1.getWorkTypeCode()) - Integer.parseInt(e2.getWorkTypeCode()));	
		
		return workTypeDtos;
	}

}
