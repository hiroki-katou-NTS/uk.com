package nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod;

import java.util.Optional;

public interface ItemSalaryPeriodRepository {
	Optional<ItemSalaryPeriod> find(String itemCode);

	void add(ItemSalaryPeriod domain);

	void update(ItemSalaryPeriod domain);

	void delete(String itemCd);
}
