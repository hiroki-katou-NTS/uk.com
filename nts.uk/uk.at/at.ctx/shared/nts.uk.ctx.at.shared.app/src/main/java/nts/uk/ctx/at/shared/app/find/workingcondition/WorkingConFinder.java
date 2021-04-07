package nts.uk.ctx.at.shared.app.find.workingcondition;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 
 * @author quytb
 *
 */

@Stateless
public class WorkingConFinder {
	@Inject
	private WorkingConditionRepository wcRepo;

	public List<WorkingConDto> getListHist(String sid) {
		Optional<WorkingCondition> wc = wcRepo.getBySid(AppContexts.user().companyId(), sid);
		if (!wc.isPresent())
			return new ArrayList<>();
		List<DateHistoryItem> hists = wc.get().getDateHistoryItem();
		if (hists.size() == 0)
			return new ArrayList<>();
		return hists.stream().sorted((a, b) -> b.start().compareTo(a.start()))
				.map(x -> WorkingConDto.toWorkingConDto(x.identifier(), 
						x.start().toString(), x.end().toString()))
				.collect(Collectors.toList());
	}
}
