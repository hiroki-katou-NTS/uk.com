/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.workingcondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregEmpInfoQuery;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainDto;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Stateless
public class WorkingCondition2Finder implements PeregFinder<WorkingCondition2Dto>{

	@Inject
	private WorkingConditionRepository wcRepo;
	
	@Inject
	private WorkingConditionItemRepository wcItemRepo;
	
	@Override
	public String targetCategoryCode() {
		return "CS00070";
	}

	@Override
	public Class<WorkingCondition2Dto> dtoClass() {
		return WorkingCondition2Dto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {		
		Optional<WorkingCondition> wc = getWorkingCondition(query);
		if(!wc.isPresent()) return null;
		Optional<WorkingConditionItem> wcItems = wcItemRepo.getByHistoryId(wc.get().getDateHistoryItem().get(0).identifier()); 
		if(!wcItems.isPresent()) return null;
		return WorkingCondition2Dto.createWorkingConditionDto(wc.get().getDateHistoryItem().get(0), wcItems.get());
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		Optional<WorkingCondition> wc = wcRepo.getBySid(AppContexts.user().companyId(), query.getEmployeeId());
		if(!wc.isPresent()) return new ArrayList<>();
		List<DateHistoryItem> hists = wc.get().getDateHistoryItem();
		if(hists.size() == 0) return new ArrayList<>();
		return hists.stream()
				.sorted((a, b) -> b.start().compareTo(a.start()))
				.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), 
						x.end().equals(GeneralDate.max()) 
						//&& query.getCtgType() == 3 
						? "" : x.end().toString()))
				.collect(Collectors.toList());
	}
	

	private Optional<WorkingCondition> getWorkingCondition(PeregQuery query){
		if(query.getInfoId() == null){
			return wcRepo.getBySidAndStandardDate(query.getEmployeeId(), query.getStandardDate());
		}else{
			return wcRepo.getByHistoryId(query.getInfoId());
		}
	}

	@Override
	public List<GridPeregDomainDto> getAllData(PeregQueryByListEmp query) {
		if (query.getEmpInfos() == null) {
			return new ArrayList<>();
		}
		if (query.getEmpInfos().size() == 0) {
			return new ArrayList<>();
		}
		
		GeneralDate standarDate = query.getStandardDate();
		String cid = AppContexts.user().companyId();
		// key - sid , value - pid getEmployeeId getPersonId 
		Map<String, String> mapSids = query.getEmpInfos().stream().collect(Collectors.toMap(PeregEmpInfoQuery:: getEmployeeId, PeregEmpInfoQuery:: getPersonId));
		List<WorkingCondition> workingCondiditions =  wcRepo.getBySidsAndCid(new ArrayList<String>(mapSids.keySet()), standarDate, cid);
		Map<String, DateHistoryItem> dateHistLst = new HashMap<>();
		
		workingCondiditions.stream().forEach(c -> {
			dateHistLst.put(c.getEmployeeId(), c.getDateHistoryItem().get(0));
		});
		
		List<String> historyIds = dateHistLst.values().stream().map(c -> c.identifier()).collect(Collectors.toList());
		List<WorkingConditionItem>  workingCondiditionItems = wcItemRepo.getByListHistoryID(historyIds);
		
		List<GridPeregDomainDto> result = workingCondiditionItems.stream().map(c -> {
			DateHistoryItem date =  dateHistLst.get(c.getEmployeeId());
			return new GridPeregDomainDto(c.getEmployeeId(), mapSids.get(c.getEmployeeId()), WorkingCondition2Dto.createWorkingConditionDto(date, c ));
		}).collect(Collectors.toList());
		
		if(query.getEmpInfos().size() > result.size()) {
			for(int i  = result.size(); i < query.getEmpInfos().size() ; i++) {
				PeregEmpInfoQuery emp = query.getEmpInfos().get(i);
				result.add(new GridPeregDomainDto(emp.getEmployeeId(), emp.getPersonId(), null));
			}
		}
		return result;
	}
}
