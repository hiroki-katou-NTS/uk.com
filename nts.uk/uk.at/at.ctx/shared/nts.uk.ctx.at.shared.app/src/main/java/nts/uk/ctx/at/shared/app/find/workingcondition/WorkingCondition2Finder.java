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
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.PeregQueryByListEmp;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.GridPeregDomainBySidDto;
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
		String cid = AppContexts.user().companyId();

		List<GridPeregDomainDto> result = new ArrayList<>();

		List<String> sids = query.getEmpInfos().stream().map(c -> c.getEmployeeId()).collect(Collectors.toList());

		query.getEmpInfos().forEach(c -> {
			result.add(new GridPeregDomainDto(c.getEmployeeId(), c.getPersonId(), null));
		});

		List<WorkingCondition> workingCondiditions = wcRepo.getBySidsAndCid(sids, query.getStandardDate(), cid);
		Map<String, DateHistoryItem> dateHistLst = new HashMap<>();

		workingCondiditions.stream().forEach(c -> {
			dateHistLst.put(c.getEmployeeId(), c.getDateHistoryItem().get(0));
		});

		List<String> historyIds = dateHistLst.values().stream().map(c -> c.identifier()).collect(Collectors.toList());
		List<WorkingConditionItem> workingCondiditionItems = wcItemRepo.getByListHistoryID(historyIds);

		result.stream().forEach(c -> {
			Optional<WorkingConditionItem> histItemOpt = workingCondiditionItems.stream()
					.filter(emp -> emp.getEmployeeId().equals(c.getEmployeeId())).findFirst();
			if (histItemOpt.isPresent()) {
				DateHistoryItem dateHistItem = dateHistLst.get(c.getEmployeeId());
				c.setPeregDomainDto(dateHistItem != null
						? WorkingCondition2Dto.createWorkingConditionDto(dateHistItem, histItemOpt.get())
						: null);
			}
		});

		return result;
	}

	@Override
	public List<GridPeregDomainBySidDto> getListData(PeregQueryByListEmp query) {
		// TODO Auto-generated method stub
		return null;
	}
}
