package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import java.util.List;
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
		String roleID = AppContexts.user().roles().forGroupCompaniesAdmin();
		String companyId = roleID != null ? AppContexts.user().zeroCompanyIdInContract()
				: AppContexts.user().companyId();
		SelectionHistory history = this.historySelectionRepo.get(selectedId, companyId).get();
		List<DateHistoryItem> reversedDateHistoryItems = Lists.reverse(history.getDateHistoryItems());
		
		return reversedDateHistoryItems.stream().map(x -> PerInfoHistorySelectionDto.createDto(x))
				.collect(Collectors.toList());
	}
}
