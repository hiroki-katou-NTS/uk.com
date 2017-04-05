package nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd;

import java.util.List;
import java.util.Optional;

public interface ItemSalaryBDRepository {
	List<ItemSalaryBD> findAll( String itemCode);

	void add(ItemSalaryBD itemSalaryBD);

	
	void delete(String itemCd, String itemBreakdownCd);

	Optional<ItemSalaryBD> find(String itemCd, String itemBreakdownCd);

	void update(ItemSalaryBD domain);
	
	
	
}
