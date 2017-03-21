package nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct;

import java.util.Optional;

public interface ItemDeductRespository {

	Optional<ItemDeduct> find(String companyCode, String itemCode);

	void add(ItemDeduct itemDeduct);

	void delete(String companyCode, String itemCode);

	void update(ItemDeduct domain);

}
