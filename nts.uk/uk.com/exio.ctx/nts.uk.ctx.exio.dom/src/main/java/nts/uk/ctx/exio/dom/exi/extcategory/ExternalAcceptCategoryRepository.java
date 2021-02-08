package nts.uk.ctx.exio.dom.exi.extcategory;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.exio.dom.exi.condset.SystemType;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public interface ExternalAcceptCategoryRepository {
	/**
	 * 外部受入カテゴリIDから外部受入カテゴリを取得
	 * @param categoryId
	 * @return
	 */
	Optional<ExternalAcceptCategory> getByCategoryId(int categoryId);
	
	/**
	 * システムと使用するしないか外部受入カテゴリを取得
	 * @param systemType
	 * @param useAtr
	 * @return
	 */
	List<ExternalAcceptCategory> getBySystem(SystemType systemType, NotUseAtr useAtr);
}
