package nts.uk.ctx.at.record.app.find.divergencetime;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergencetime.DivergenceTimeRepository;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendancetype.AttendanceTypeDivergenceServiceDto;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendancetype.AttendanceTypeDivergenceSevice;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DivergenceItemSetFinder {
	@Inject
	private DivergenceTimeRepository divTimeRepo;
	
	@Inject 
	private AttendanceTypeDivergenceSevice atType;
	//user contexts
	
	
	public List<DivergenceItemSetDto> getAllDivReasonByCode(String divTimeId){
		String companyId = AppContexts.user().companyId();
		List<DivergenceItemSetDto> lst = this.divTimeRepo.getallItembyCode(companyId,Integer.valueOf(divTimeId))
				.stream()
				.map(c->DivergenceItemSetDto.fromDomain(c))
				.collect(Collectors.toList());
		return lst;
	}
	
	public List<AttendanceTypeDivergenceServiceDto> getAllAtType(int screenUseAtr){
		String companyId = AppContexts.user().companyId();
		List<AttendanceTypeDivergenceServiceDto> data = atType.getItemByScreenUseAtr(companyId, screenUseAtr);
		return data;
	}
}
