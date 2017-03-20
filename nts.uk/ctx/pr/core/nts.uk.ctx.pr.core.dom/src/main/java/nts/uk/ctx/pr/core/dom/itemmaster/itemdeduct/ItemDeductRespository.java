package nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct;

import java.util.Optional;

public interface ItemDeductRespository {

	Optional<ItemDeduct> find(String companyCode, String itemCode);

}
