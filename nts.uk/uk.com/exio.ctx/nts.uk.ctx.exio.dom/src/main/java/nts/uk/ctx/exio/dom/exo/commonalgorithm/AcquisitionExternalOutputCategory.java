package nts.uk.ctx.exio.dom.exo.commonalgorithm;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.StringUtil;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtgRepository;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;

@Stateless
public class AcquisitionExternalOutputCategory {

	@Inject
	private CtgItemDataRepository ctgItemDataRepository;

	@Inject
	private ExOutCtgRepository exOutCtgRepo;

	@Inject
	private CtgUseAuth ctgUseAuth;

	/**
	 * 外部出力カテゴリ取得項目
	 */
	public List<CtgItemData> getExternalOutputCategoryItem(Integer categoryId, Integer itemNo) {
		if (itemNo == null) {
			return ctgItemDataRepository.getAllByCategoryId(categoryId);
		} else {
			return ctgItemDataRepository.getAllByKey(categoryId, itemNo);
		}
	}

	/**
	 * 外部出力カテゴリ取得リスト
	 * 
	 * @param roleIdList
	 *            指定ロール（リスト）
	 * @return 外部出力カテゴリ（リスト）
	 */
	public List<ExOutCtg> getExternalOutputCategoryList(List<String> roleIdList) {
		List<ExOutCtg> exOutCtgListResult = new ArrayList<>();
		// ドメインモデル「外部出力カテゴリ」を取得する
		List<ExOutCtg> exOutCtgList = exOutCtgRepo.getExOutCtgList();
		for (ExOutCtg exOutCtg : exOutCtgList) {
			// アルゴリズム「外部出力カテゴリ使用権限」を実行する
			if (ctgUseAuth.isUseAuth(exOutCtg, roleIdList)) {
				// 外部出力カテゴリ（リスト）に追加
				exOutCtgListResult.add(exOutCtg);
			}
		}
		return exOutCtgListResult;
	}
}
