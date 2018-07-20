package nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftCondition;
import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftConditionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class ShiftConditionFinder {
	@Inject
	private ShiftConditionRepository repo;

	/**
	 * get all shift condition
	 * 
	 * @return list shift condition
	 */
	public List<ShiftConditionDto> getAllShiftCondition() {
		String companyId = AppContexts.user().companyId();
		return repo.getListShiftCondition(companyId).stream().map(domain -> ShiftConditionDto.fromDomain(domain))
				.collect(Collectors.toList());
	}
	
	public ItemConditionNames checkExistedItems(List<String> items) {
		String companyId = AppContexts.user().companyId();
		String name = TextResource.localize("KSM011_75");

		 List<Integer> itemNos = items.stream().map(item -> Integer.parseInt(item.replace("c", ""))).collect(Collectors.toList());
		 Map<Integer, String> result = repo.getListShiftCondition(companyId).stream().collect(
				 Collectors.toMap(ShiftCondition::getConditionNo, x->x.getConditionName().v()));
		 StringBuilder nameList = new StringBuilder();
		 
		 ItemConditionNames conditionNames = new ItemConditionNames();
		 IntStream.range(0, itemNos.size()).forEach(item ->{
			 nameList.append(result.containsKey(itemNos.get(item)) ? result.get(itemNos.get(item)): name);
			 if(item < itemNos.size() - 1)
			 {
				 nameList.append(", ");
			 }
		 });
		 conditionNames.setExisted(!itemNos.stream().anyMatch(item -> !result.keySet().contains(item)));
		 conditionNames.setItems(nameList.toString());
		 return conditionNames;
		
	}
}
