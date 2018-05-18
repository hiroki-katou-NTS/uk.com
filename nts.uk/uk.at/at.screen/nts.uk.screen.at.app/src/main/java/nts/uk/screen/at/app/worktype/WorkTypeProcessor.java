package nts.uk.screen.at.app.worktype;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktype.DeprecateClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkTypeProcessor {

	@Inject
	private WorkTypeQueryRepository workTypeQueryRepository;
	
	/**
	 * Find work type by company 
	 * @param companyId
	 * @return
	 */
	public List<WorkTypeDto> findWorkTypeAll(){
		String companyId = AppContexts.user().companyId();
		return this.workTypeQueryRepository.findAllWorkType(companyId);
	}
	
	/**
	 * Find work type by work type attribute
	 * @param workTypeAtr 分類
	 * @return
	 */
	public List<WorkTypeDto> findWorkTypeByDailyWorkType(List<Integer> workTypeAtr){
		String companyId = AppContexts.user().companyId();
		
		if (CollectionUtil.isEmpty(workTypeAtr)) {
			return Collections.emptyList();
		}
		
		return this.workTypeQueryRepository.findAllWorkType(companyId, workTypeAtr);
	}
	
	public List<WorkTypeDto> findWorkTypeDailyWorkType(List<Integer> workTypeAtr){
		String companyId = AppContexts.user().companyId();
		
		if (CollectionUtil.isEmpty(workTypeAtr)) {
			return Collections.emptyList();
		}
		
		return this.workTypeQueryRepository.findWorkType(companyId, workTypeAtr);
	}

	/**
	 * 
	 * @return
	 */
	public List<WorkTypeDto> findWorkTypeSpe(){
		String companyId = AppContexts.user().companyId();
		return this.workTypeQueryRepository.findAllWorkTypeSPE(companyId, DeprecateClassification.NotDeprecated.value, WorkTypeClassification.SpecialHoliday.value, WorkTypeClassification.Absence.value);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<WorkTypeDto> findWorkTypeDisp(){
		String companyId = AppContexts.user().companyId();
		return this.workTypeQueryRepository.findAllWorkTypeDisp(companyId, DeprecateClassification.NotDeprecated.value);
	}
}
