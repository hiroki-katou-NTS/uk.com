package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.divergence.time.setting.*;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class DivergenceTimeInputMethodFinder {
	@Inject
	private DivergenceReasonInputMethodFinder divReasonFinder;
	@Inject
	private DivergenceTimeFinder divTimeFinder;
	
//	public List<DivergenceReasonInputMethodDto> getAllDivTime(){
//		List<DivergenceTimeDto> listdivTime = divTimeFinder.getAllDivTime();
//		List<DivergenceReasonInputMethodDto> listDivReason = divReasonFinder.getAllDivTime();
//		
//		List<DivergenceTimeInputMethodDto> listDivTimeReason;
//		
//		listDivReason.stream().map(e->{new DivergenceTimeInputMethodDto(0, null, 0, null, 0, false, false, false, false);});
//		
//		
//		return null;
//	}
	
	

}

//List<CompanyDivergenceReferenceTime> listDomain = this.comDivergenceRefTimeRepository.findAll(historyId);
//
//if (listDomain.isEmpty()){
//	return Collections.emptyList();
//}
//
//return listDomain.stream().map(e -> {
//	CompanyDivergenceReferenceTimeDto dto = new CompanyDivergenceReferenceTimeDto();
//	e.saveToMemento(dto);
//	return dto;
//}).collect(Collectors.toList());