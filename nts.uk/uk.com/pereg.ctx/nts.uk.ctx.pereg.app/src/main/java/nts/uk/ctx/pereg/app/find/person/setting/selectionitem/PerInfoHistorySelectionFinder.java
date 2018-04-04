package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.pereg.dom.person.info.category.PersonInfoCategory;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoHistorySelectionRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class PerInfoHistorySelectionFinder {

	@Inject
	PerInfoHistorySelectionRepository historySelectionRepo;

	public List<PerInfoHistorySelectionDto> historySelection(String selectedId) {
		// gia lap: Kiem tra quyen User
		// boolean isSystemAdmin = false;
		// ログインしているユーザーの権限をチェックする(Kiểm tra quyền User login)
		// String cid = isSystemAdmin == true ? PersonInfoCategory.ROOT_COMPANY_ID : AppContexts.user().companyId();
		
		// get GroupCompaniesAdmin
		LoginUserContext loginUserContext = AppContexts.user();
		String roleID = loginUserContext.roles().forGroupCompaniesAdmin();
		
		// 個人情報共通アルゴリズム「ログイン者がグループ会社管理者かどうか判定する」を実行する
		boolean isSystemAdmin = StringUtils.isEmpty(roleID) ? false : true;
		if (isSystemAdmin) {
			return null;
		} else {
			// 共通アルゴリズム「契約内ゼロ会社の会社IDを取得する」を実行する
			String cid = AppContexts.user().zeroCompanyIdInContract();

			// ドメインモデル「選択肢履歴」を取得する(lấy Domain Model 「選択肢履歴」)
			List<PerInfoHistorySelectionDto> historyList = this.historySelectionRepo
					.getAllHistoryBySelectionItemIdAndCompanyId(selectedId, cid).stream()
					.map(i -> PerInfoHistorySelectionDto.fromDomainHistorySelection(i)).collect(Collectors.toList());

			return historyList;
		}
	}
}
