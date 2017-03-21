package nts.uk.ctx.pr.core.dom.itemmaster.itemdeductperiod;

import java.util.Optional;

public interface ItemDeductPeriodRepository {

	Optional<ItemDeductPeriod> find(String companyCode, String itemCode);

}
