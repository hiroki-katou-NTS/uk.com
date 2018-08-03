package nts.uk.ctx.exio.dom.exo.commonalgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.exio.dom.exo.category.CategorySetting;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtg;
import nts.uk.ctx.exio.dom.exo.category.ExOutCtgRepository;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemData;
import nts.uk.ctx.exio.dom.exo.categoryitemdata.CtgItemDataRepository;
import nts.uk.shr.com.enumcommon.NotUseAtr;

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
		int displayClassfication = NotUseAtr.USE.value;
		if (itemNo == null) {
			return ctgItemDataRepository.getAllByCategoryId(categoryId, displayClassfication);
		} else {
			List<CtgItemData> result = new ArrayList<>();
			Optional<CtgItemData> ctgItemDataOpt = ctgItemDataRepository.getCtgItemDataByIdAndDisplayClass(categoryId,
					itemNo, displayClassfication);
			if (ctgItemDataOpt.isPresent()) {
				result.add(ctgItemDataOpt.get());
			}
			return result;
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
		List<ExOutCtg> exOutCtgList = exOutCtgRepo.getExOutCtgList(CategorySetting.CATEGORY_SETTING);
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
