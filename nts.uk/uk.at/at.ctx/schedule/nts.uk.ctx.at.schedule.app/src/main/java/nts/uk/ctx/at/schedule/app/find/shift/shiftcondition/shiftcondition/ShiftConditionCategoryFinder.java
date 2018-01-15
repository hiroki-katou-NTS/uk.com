package nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftConditionCategoryRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ShiftConditionCategoryFinder {
	@Inject
	ShiftConditionCategoryRepository repo;

	/**
	 * get all shift condition category
	 * 
	 * @return list condition category
	 */
	public List<ShiftConditionCategoryDto> getAllShiftConditonCategory() {
		String companyId = AppContexts.user().companyId();
		return repo.getListShifConditionCategory(companyId).stream()
				.map(domain -> ShiftConditionCategoryDto.fromDomain(domain)).collect(Collectors.toList());
	}
}
