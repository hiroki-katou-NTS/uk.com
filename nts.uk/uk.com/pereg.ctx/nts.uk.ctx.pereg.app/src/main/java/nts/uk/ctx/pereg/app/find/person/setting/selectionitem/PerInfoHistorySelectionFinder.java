package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.setting.selectionitem.history.PerInfoHistorySelectionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerInfoHistorySelectionFinder {

	@Inject
	PerInfoHistorySelectionRepository historySelectionRepo;

	public List<PerInfoHistorySelectionDto> historySelection(String selectedId) {
		
		String roleID = AppContexts.user().roles().forGroupCompaniesAdmin();
		String companyId = roleID != null ? AppContexts.user().zeroCompanyIdInContract() : AppContexts.user().companyId();

		// ドメインモデル「選択肢履歴」を取得する(lấy Domain Model 「選択肢履歴」)
		List<PerInfoHistorySelectionDto> historyList = this.historySelectionRepo
				.getAllBySelecItemIdAndCompanyId(selectedId, companyId).stream()
				.map(i -> PerInfoHistorySelectionDto.fromDomainHistorySelection(i)).collect(Collectors.toList());

		return historyList;
	}
}
