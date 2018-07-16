package nts.uk.ctx.exio.dom.exo.commonalgorithm;

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

	/**
	 * 外部出力カテゴリ取得項目
	 */
	public List<CtgItemData> getExternalOutputCategoryItem(String categoryId, String itemNo) {
		if (StringUtil.isNullOrEmpty(itemNo, true)) {
			return ctgItemDataRepository.getAllByCategoryId(categoryId);
		} else {
			return ctgItemDataRepository.getAllByKey(categoryId, itemNo);
		}
	}
	
	/**
	 * 外部出力カテゴリ取得リスト
	 */
	public List<ExOutCtg> getExternalOutputCategoryList() {
		// ドメインモデル「外部出力カテゴリ」を取得する
		List<ExOutCtg> exOutCtgList = exOutCtgRepo.getExOutCtgList();
		// 外部出力カテゴリ１（リスト）と外部出力カテゴリ２（リスト）をマージする
		// TODO
		return exOutCtgList;
	}
}
