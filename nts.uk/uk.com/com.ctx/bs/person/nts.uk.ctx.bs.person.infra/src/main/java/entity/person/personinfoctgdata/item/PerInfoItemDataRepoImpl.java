/**
 * 
 */
package entity.person.personinfoctgdata.item;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PerInfoItemDataRepository;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PersonInfoItemData;

/**
 * @author danpv
 *
 */
@Stateless
public class PerInfoItemDataRepoImpl implements PerInfoItemDataRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PerInfoItemDataRepository#getAllInfoItem(java.lang.String)
	 */
	@Override
	public List<PersonInfoItemData> getAllInfoItem(String categoryCd) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.person.dom.person.personinfoctgdata.item.PerInfoItemDataRepository#getAllInfoItemByRecordId(java.lang.String)
	 */
	@Override
	public List<PersonInfoItemData> getAllInfoItemByRecordId(String recordId) {
		// TODO Auto-generated method stub
		return null;
	}

}
