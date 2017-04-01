package nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd;

import java.util.List;
import java.util.Optional;

public interface ItemDeductBDRepository {
	
	List<ItemDeductBD> findAll(String companyCode, String itemCode);

	void add(ItemDeductBD itemDeductBD);

	void delete(String itemCd, String itemBreakdownCd);

	Optional<ItemDeductBD> find(String itemCd, String itemBreakdownCd);

	void update(ItemDeductBD itemDeductBD);

}
