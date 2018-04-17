package nts.uk.ctx.pereg.app.find.person.setting.selectionitem;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoSelectionItem;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class PerInfoSelectionItemFinder {

	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;

	public List<PerInfoSelectionItemDto> getAllPerInfoSelectionItem(boolean hasCompanyId) {
		String contractCode = AppContexts.user().contractCode();
		
		LoginUserContext loginUserContext = AppContexts.user();
		String roleID = loginUserContext.roles().forGroupCompaniesAdmin();
		
		// 個人情報共通アルゴリズム「ログイン者がグループ会社管理者かどうか判定する」を実行する
		hasCompanyId = StringUtils.isEmpty(roleID) ? false : true;
		if (hasCompanyId) {
			// グループ会社管理者でない場合トップページへ戻す処理を追加
			// エラーメッセージ（#Msg_1103）を表示するHiển thị error message （#Msg_1103）
			throw new BusinessException("Msg_1103");
		} else {
			// ログイン者がグループ会社管理者かどうかの判定を追加
			return this.perInfoSelectionItemRepo.getAllSelectionItemByContractCd(contractCode).stream()
					.map(i -> PerInfoSelectionItemDto.fromDomain(i)).collect(Collectors.toList());
		}
	}

	public PerInfoSelectionItemDto getPerInfoSelectionItem(String selectionItemId) {
		Optional<PerInfoSelectionItem> opt = this.perInfoSelectionItemRepo
				.getSelectionItemBySelectionItemId(selectionItemId);

		if (!opt.isPresent()) {
			return null;
		}
		return PerInfoSelectionItemDto.fromDomain(opt.get());
	}
	// getAllSelection

	public List<PerInfoSelectionItemDto> getAllSelectionItem(int selectionItemClsAtr) {
		return this.perInfoSelectionItemRepo.getAllSelection(selectionItemClsAtr, AppContexts.user().contractCode())
				.stream().map(c -> PerInfoSelectionItemDto.fromDomain(c)).collect(Collectors.toList());
	}
}
