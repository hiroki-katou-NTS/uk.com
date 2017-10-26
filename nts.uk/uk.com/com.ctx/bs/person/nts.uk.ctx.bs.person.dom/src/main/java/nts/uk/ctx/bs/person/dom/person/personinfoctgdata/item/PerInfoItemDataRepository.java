package nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item;

import java.util.List;

/**
 * @author LaiTV
 *
 */
public interface PerInfoItemDataRepository {

	List<PersonInfoItemData> getAllInfoItem(String categoryCd);

}
