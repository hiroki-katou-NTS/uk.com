package nts.uk.ctx.exio.dom.exo.commonalgorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.authset.ExOutCtgAuthSetRepository;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CtgUseAuth {

	@Inject
	private ExOutCtgAuthSetRepository exOutCtgAuthSetRepo;

	/**
	 * 外部出力カテゴリ使用権限
	 * 
	 * @param ctg
	 *            ドメイン「外部出力カテゴリ
	 * @param roleIdList
	 *            ロール（リスト）
	 * @return 結果（OK/NG）
	 */
	public boolean isUseAuth(ExOutCtg ctg, List<String> roleIdList) {
		if (roleIdList == null || roleIdList.isEmpty()) {
			// リストが存在しない場合
			return false;
		}
		String cid = AppContexts.user().companyId();
		for (String roleId : roleIdList) {
			// ドメインモデル「外部出力カテゴリ利用権限の設定」を取得する
			if (exOutCtgAuthSetRepo.find(cid, roleId, ctg.getFunctionNo()).isPresent()) {
				// 取得できた場合
				return true;
			}
		}
		return false;
	}
}
