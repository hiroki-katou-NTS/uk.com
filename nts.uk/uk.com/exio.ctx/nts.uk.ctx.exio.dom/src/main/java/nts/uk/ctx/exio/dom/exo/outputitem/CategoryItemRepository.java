package nts.uk.ctx.exio.dom.exo.outputitem;

import java.util.List;
import java.util.Optional;

/**
 * @author son.tc カテゴリ項目
 *
 */

public interface CategoryItemRepository {
	List<CategoryItem> getAllCtgItem();

	Optional<CategoryItem> getCtgItemById(String categoryItemNo, String cid, String outputItemCode,
			String conditionSetCode);

	void add(CategoryItem domain);

	void update(CategoryItem domain);

	void remove(String categoryItemNo, String cid, String outputItemCode, String conditionSetCode);
}
