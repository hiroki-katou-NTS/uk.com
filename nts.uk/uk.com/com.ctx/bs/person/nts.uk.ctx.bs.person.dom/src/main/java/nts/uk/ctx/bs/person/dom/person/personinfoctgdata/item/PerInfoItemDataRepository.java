package nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item;

import java.util.List;

/**
 * @author sonnlb
 *
 */
public interface PerInfoItemDataRepository {

	List<EmpInfoItemData> getAllInfoItem(String categoryCd);

}
