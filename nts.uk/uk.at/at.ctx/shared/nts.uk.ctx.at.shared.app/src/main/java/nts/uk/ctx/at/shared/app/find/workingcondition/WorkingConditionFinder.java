package nts.uk.ctx.at.shared.app.find.workingcondition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.pereg.app.ComboBoxObject;
import nts.uk.shr.pereg.app.find.PeregFinder;
import nts.uk.shr.pereg.app.find.PeregQuery;
import nts.uk.shr.pereg.app.find.dto.DataClassification;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

public class WorkingConditionFinder implements PeregFinder<WorkingConditionDto>{

	@Override
	public String targetCategoryCode() {
		return "CS00020";
	}

	@Override
	public Class<WorkingConditionDto> dtoClass() {
		return WorkingConditionDto.class;
	}

	@Override
	public DataClassification dataType() {
		return DataClassification.EMPLOYEE;
	}

	@Override
	public PeregDomainDto getSingleData(PeregQuery query) {
		if(query.getInfoId() == null && query.getStandardDate() == null) return null;		
		Optional<WorkingCondition> wc = getWorkingCondition(query);
		if(!wc.isPresent()) return null;
		//TODO get working condition item by history id
		Optional<WorkingConditionItem> wcItems = null; 
		return WorkingConditionDto.createWorkingConditionDto(wc.get().getDateHistoryItem().get(0), wcItems.get());
	}

	@Override
	public List<PeregDomainDto> getListData(PeregQuery query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ComboBoxObject> getListFirstItems(PeregQuery query) {
		//TODO get WorkingCondition by sid and companyId
		Optional<WorkingCondition> wc = null;
		if(!wc.isPresent()) return new ArrayList<>();
		return wc.get().getDateHistoryItem().stream()
				.sorted((a, b) -> b.start().compareTo(a.start()))
				.map(x -> ComboBoxObject.toComboBoxObject(x.identifier(), x.start().toString(), x.end().toString()))
				.collect(Collectors.toList());
	}

	private Optional<WorkingCondition> getWorkingCondition(PeregQuery query){
		if(query.getInfoId() == null){
			//TODO get by employee and standard date
		}else{
			//TODO get by history id
		}
		return null;
	}
}
