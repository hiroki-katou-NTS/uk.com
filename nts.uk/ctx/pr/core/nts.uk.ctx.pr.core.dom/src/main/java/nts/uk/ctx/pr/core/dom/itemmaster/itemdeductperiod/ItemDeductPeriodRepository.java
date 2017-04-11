package nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod;

import java.util.Optional;

public interface ItemDeductPeriodRepository {

	Optional<ItemDeductPeriod> find( String itemCode);

	void add(ItemDeductPeriod itemDeductPeriod);

	void delete(String itemCode);

	void update(ItemDeductPeriod domain);

}
