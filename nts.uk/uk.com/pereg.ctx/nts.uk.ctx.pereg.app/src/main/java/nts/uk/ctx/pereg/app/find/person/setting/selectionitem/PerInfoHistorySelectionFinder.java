package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoHistorySelectionRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class PerInfoHistorySelectionFinder {

	@Inject
	PerInfoHistorySelectionRepository historySelectionRepo;

	public List<PerInfoHistorySelectionDto> historySelection(String selectedId) {

		// gia lap: Kiem tra quyen User
		boolean isSystemAdmin = false;

		// ログインしているユーザーの権限をチェックする(Kiểm tra quyền User login)
		String cid = isSystemAdmin == true ? PersonInfoCategory.ROOT_COMPANY_ID : AppContexts.user().companyId();

		// ドメインモデル「選択肢履歴」を取得する(lấy Domain Model 「選択肢履歴」)
		List<PerInfoHistorySelectionDto> historyList = this.historySelectionRepo
				.getAllHistoryBySelectionItemIdAndCompanyId(selectedId, cid).stream()
				.map(i -> PerInfoHistorySelectionDto.fromDomainHistorySelection(i)).collect(Collectors.toList());

		return historyList;
	}
}
