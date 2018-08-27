package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.google.common.collect.Lists;

import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistory;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.SelectionHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
public class PerInfoHistorySelectionFinder {

	@Inject
	private SelectionHistoryRepository historySelectionRepo;

	public List<PerInfoHistorySelectionDto> historySelection(String selectedId) {
		String companyId = AppContexts.user().companyId();
		Optional<SelectionHistory> historyOptional = this.historySelectionRepo.get(selectedId, companyId);
		
		if(!historyOptional.isPresent()) {
			return new ArrayList<PerInfoHistorySelectionDto>();
		}
		
		SelectionHistory history = historyOptional.get();
		List<DateHistoryItem> reversedDateHistoryItems = Lists.reverse(history.getDateHistoryItems());
		
		return reversedDateHistoryItems.stream().map(x -> PerInfoHistorySelectionDto.createDto(x))
				.collect(Collectors.toList());
	}
}
