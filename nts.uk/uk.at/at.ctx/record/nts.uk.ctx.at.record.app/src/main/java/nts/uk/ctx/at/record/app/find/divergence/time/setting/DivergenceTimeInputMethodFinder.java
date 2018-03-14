package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	public List<DivergenceTimeInputMethodDto> getAllDivTime(){
		List<DivergenceTimeDto> listdivTime = divTimeFinder.getAllDivTime();
		List<DivergenceReasonInputMethodDto> listDivReason = divReasonFinder.getAllDivTime();
		
		List<DivergenceTimeInputMethodDto> listDivTimeInput = new ArrayList<DivergenceTimeInputMethodDto>();
		
		listdivTime.forEach(e-> {
			DivergenceReasonInputMethodDto object= (DivergenceReasonInputMethodDto) listDivReason.stream().filter(a->a.getDivergenceTimeNo()==e.getDivergenceTimeNo()).findFirst().get();
			listDivTimeInput.add(new DivergenceTimeInputMethodDto(e.getDivergenceTimeNo(),
																	e.getCompanyId(),
																	e.getDivTimeUseSet(),
																	e.getDivTimeName(),
																	e.getDivType(),
																	e.isReasonInput(),
																	e.isReasonSelect(),
																	object.getDivergenceReasonInputed(),
																	object.getDivergenceReasonSelected(),
																	null)
								);
		});
		
		return listDivTimeInput;
	}
	
	

}

