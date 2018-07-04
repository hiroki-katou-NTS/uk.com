package nts.uk.ctx.exio.dom.exo.outitem;

import java.util.List;
import java.util.Optional;

/**
 * @author son.tc カテゴリ項目
 *
 */

public interface CtgItemRepository {
	List<CtgItem> getAllCtgItem();

	Optional<CtgItem> getCtgItemById(String ctgItemNo, String cid, String outItemCd, String condSetCd);

	void add(CtgItem domain);

	void update(CtgItem domain);

	void remove(String ctgItemNo, String cid, String outItemCd, String condSetCd);
}
