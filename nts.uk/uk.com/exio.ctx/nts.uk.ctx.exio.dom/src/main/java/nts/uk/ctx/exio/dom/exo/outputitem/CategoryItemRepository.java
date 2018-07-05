package nts.uk.ctx.exio.dom.exo.outputitem;

import java.util.List;
import java.util.Optional;

/**
 * @author son.tc カテゴリ項目
 *
 */

public interface CategoryItemRepository {
	List<CategoryItem> getAllCtgItem();

	Optional<CategoryItem> getCtgItemById(String ctgItemNo, String cid, String outItemCd, String condSetCd);

	void add(CategoryItem domain);

	void update(CategoryItem domain);

	void remove(String ctgItemNo, String cid, String outItemCd, String condSetCd);
}
