package nts.uk.ctx.at.schedule.app.find.shift.shiftcondition.shiftcondition;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.shiftcondition.shiftcondition.ShiftConditionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ShiftConditionFinder {
	@Inject
	ShiftConditionRepository repo;

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
}
