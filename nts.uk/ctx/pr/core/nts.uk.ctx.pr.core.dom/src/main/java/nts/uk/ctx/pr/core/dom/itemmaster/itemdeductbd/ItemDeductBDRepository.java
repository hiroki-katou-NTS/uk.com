package nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd;

import java.util.List;

public interface ItemDeductBDRepository {
	
	List<ItemDeductBD> findAll(String companyCode, String itemCode);

}
