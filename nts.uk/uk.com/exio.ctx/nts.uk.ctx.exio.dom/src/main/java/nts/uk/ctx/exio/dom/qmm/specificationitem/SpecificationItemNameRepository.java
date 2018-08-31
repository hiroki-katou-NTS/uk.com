package nts.uk.ctx.exio.dom.qmm.specificationitem;

import java.util.List;
import java.util.Optional;

/**
 * 明細書項目名称
 */
public interface SpecificationItemNameRepository {

	List<SpecificationItemName> getAllSpecItemName();

	Optional<SpecificationItemName> getSpecItemNameById(String cid, String salaryItemId);

	void add(SpecificationItemName domain);

	void update(SpecificationItemName domain);

	void remove(String cid, String salaryItemId);

}
