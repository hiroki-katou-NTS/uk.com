/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.pattern.monthly.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workingcondition.WorkScheduleBasicCreMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;

/**
 * The Class MonthlyPatternSettingFinder.
 */
@Stateless
public class MonthlyPatternSettingFinder {

	/** The repository working cond item. */
	@Inject
	private WorkingConditionItemRepository repositoryWorkingCondItem;
	
	/** The repository working cond. */
	@Inject
	private WorkingConditionRepository repositoryWorkingCond;
	
    /** The Constant NONE_SETTING. */
    public static final String NONE_SETTING = "KSM005_43";
    
    /**
     * Find all monthly pattern setting by sid.
     *
     * @param employeeIds the employee ids
     * @return the list
     */
    public List<MonthlyPatternSettingDto> findAllMonthlyPatternSettingBySid(List<String> employeeIds, List<String> monthlyPatternCodes){

		List<WorkingConditionItem> listResult = this.repositoryWorkingCondItem
				.getByListSidAndMonthlyPatternNotNull(employeeIds, monthlyPatternCodes).stream()
				.filter(item -> item.getScheduleMethod().isPresent() ? item.getScheduleMethod().get()
						.getBasicCreateMethod() == WorkScheduleBasicCreMethod.MONTHLY_PATTERN : false)
				.collect(Collectors.toList());

		if (!listResult.isEmpty()) {
    		List<MonthlyPatternSettingDto> list = new ArrayList<>();
    		listResult.stream().forEach(e -> {
    			// new Dto
    	    	MonthlyPatternSettingDto dto = new MonthlyPatternSettingDto();
    	    	dto.setEmployeeId(e.getEmployeeId());
    			dto.setHistoryId(e.getHistoryId());
    			dto.setMonthlyPatternCode(e.getMonthlyPattern().get().v());
    			
    			list.add(dto);
    		});
    		return list;
    	}
    	return null;
    }
	
	/**
	 * Find by id.
	 *
	 * @param historyId the history id
	 * @return the monthly pattern setting dto
	 */
	public MonthlyPatternSettingDto findById(String historyId){	
		// new Dto
		MonthlyPatternSettingDto dto = new MonthlyPatternSettingDto();
		
		//call repository find by employee id
		Optional<WorkingConditionItem> optWkConItem = this.repositoryWorkingCondItem.getByHistoryId(historyId);
		
		// check exist data
		if(optWkConItem.isPresent()){
			dto.setEmployeeId(optWkConItem.get().getEmployeeId());
			dto.setHistoryId(optWkConItem.get().getHistoryId());
			dto.setMonthlyPatternCode(optWkConItem.get().getMonthlyPattern().get().v());
			return dto;
		}
		return null;
	}
	
	/**
	 * Find by id.
	 *
	 * @param historyId the history id
	 * @return the monthly pattern setting dto
	 */
	public MonthlyPatternSettingDto findBySId(String employeeId){	
		// new Dto
		MonthlyPatternSettingDto dto = new MonthlyPatternSettingDto();
		
		//call repository find by employee id
		Optional<WorkingConditionItem> optWkConItem = this.repositoryWorkingCondItem.getBySid(employeeId);
		
		// check exist data
		if(optWkConItem.isPresent()){
			dto.setEmployeeId(optWkConItem.get().getEmployeeId());
			dto.setHistoryId(optWkConItem.get().getHistoryId());
			dto.setMonthlyPatternCode(optWkConItem.get().getMonthlyPattern().get().v());
			return dto;
		}
		return null;
	}
	
	/**
	 * Find list working condition by SID.
	 *
	 * @param employeeId the employee id
	 * @return the list
	 */
	public List<HistoryDto> findListWorkingConditionBySID(String employeeId){
		
		Optional<WorkingCondition> optWorkingCond = this.repositoryWorkingCond.getBySid(employeeId);
		
		if(optWorkingCond.isPresent()){
			List<HistoryDto> listHist = optWorkingCond.get().getDateHistoryItem().stream().map(e -> {
				HistoryDto histDto = new HistoryDto(e.identifier(), new Period(e.start(), e.end()));
				return histDto;
			}).collect(Collectors.toList());
			
			return listHist;
		}
		
		return null;
	}
}
