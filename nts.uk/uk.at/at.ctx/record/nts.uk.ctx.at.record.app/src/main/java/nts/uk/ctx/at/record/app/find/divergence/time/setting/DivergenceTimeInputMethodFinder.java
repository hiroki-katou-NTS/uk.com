package nts.uk.ctx.at.record.app.find.divergence.time.setting;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DivergenceTimeInputMethodFinder.
 */
@Stateless
public class DivergenceTimeInputMethodFinder {
	
//	/** The div reason finder. */
//	@Inject
//	private DivergenceReasonInputMethodFinder divReasonFinder;
//	
//	/** The div time finder. */
//	@Inject
//	private DivergenceTimeFinder divTimeFinder;
//	
//	/**
//	 * Gets the all div time.
//	 *
//	 * @return the all div time
//	 */
//	public List<DivergenceTimeInputMethodDto> getAllDivTime(){
//		String companyId = AppContexts.user().companyId();
//		List<DivergenceTimeDto> listdivTime = divTimeFinder.getAllDivTime();
//		List<DivergenceReasonInputMethodDto> listDivReason = divReasonFinder.getAllDivTime();
//		
//		List<DivergenceTimeInputMethodDto> listDivTimeInput = new ArrayList<DivergenceTimeInputMethodDto>();
//		
//		listdivTime.forEach(e-> {
//			DivergenceReasonInputMethodDto object= (DivergenceReasonInputMethodDto) listDivReason.stream().filter(a->a.getDivergenceTimeNo()==e.getDivergenceTimeNo()).findFirst().get();
//			listDivTimeInput.add(new DivergenceTimeInputMethodDto(e.getDivergenceTimeNo(),
//																	companyId,
//																	e.getDivergenceTimeUseSet(),
//																	e.getDivergenceTimeName(),
//																	e.getDivType(),
//																	e.isReasonInput(),
//																	e.isReasonSelect(),
//																	object.getDivergenceReasonInputed(),
//																	object.getDivergenceReasonSelected(),
//																	null)
//								);
//		});
//		
//		return listDivTimeInput;
//	}
	
	

}

