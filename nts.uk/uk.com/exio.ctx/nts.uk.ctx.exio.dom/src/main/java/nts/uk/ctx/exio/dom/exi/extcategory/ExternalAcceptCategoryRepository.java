package nts.uk.ctx.exio.dom.exi.extcategory;

import java.util.Optional;

public interface ExternalAcceptCategoryRepository {
	/**
	 * 外部受入カテゴリIDから外部受入カテゴリを取得
	 * @param categoryId
	 * @return
	 */
	Optional<ExternalAcceptCategory> getByCategoryId(int categoryId);
}
