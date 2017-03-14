package nts.uk.ctx.pr.core.dom.itemmaster.itemsalary;

import java.util.Optional;

public interface ItemSalaryRespository {
	Optional<ItemSalary> find(String companyCode, String itemCode);
}
