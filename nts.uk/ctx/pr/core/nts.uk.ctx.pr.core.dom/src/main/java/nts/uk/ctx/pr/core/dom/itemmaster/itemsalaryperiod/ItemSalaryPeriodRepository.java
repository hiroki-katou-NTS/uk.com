package nts.uk.ctx.pr.core.dom.itemmaster.itemsalaryperiod;

import java.util.Optional;

public interface ItemSalaryPeriodRepository {
	Optional<ItemSalaryPeriod> find(String companyCode, String itemCode);
}
