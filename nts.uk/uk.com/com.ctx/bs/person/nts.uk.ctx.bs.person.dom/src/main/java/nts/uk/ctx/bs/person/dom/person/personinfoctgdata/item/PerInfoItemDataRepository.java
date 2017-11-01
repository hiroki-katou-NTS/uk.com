package nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item;

import java.util.List;

/**
 *
 */
public interface PerInfoItemDataRepository {

	List<PersonInfoItemData> getAllInfoItem(String categoryCd);
	
	List<PersonInfoItemData> getAllInfoItemByRecordId(String recordId);

}
