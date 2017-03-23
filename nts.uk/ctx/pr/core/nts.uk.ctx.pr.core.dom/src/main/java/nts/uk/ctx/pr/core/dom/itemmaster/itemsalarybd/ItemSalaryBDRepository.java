package nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd;

import java.util.List;

public interface ItemSalaryBDRepository {
	List<ItemSalaryBD> findAll(String companyCode, String itemCode);
}
