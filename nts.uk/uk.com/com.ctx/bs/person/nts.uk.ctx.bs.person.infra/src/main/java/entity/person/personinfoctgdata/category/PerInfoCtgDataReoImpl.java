/**
 * 
 */
package entity.person.personinfoctgdata.category;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgData;
import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository;

/**
 * @author danpv
 *
 */
@Stateless
public class PerInfoCtgDataReoImpl implements PerInfoCtgDataRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository#getByRecordId(java.lang.String)
	 */
	@Override
	public Optional<PerInfoCtgData> getByRecordId(String recordId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgDataRepository#getByPerIdAndCtgId(java.lang.String, java.lang.String)
	 */
	@Override
	public List<PerInfoCtgData> getByPerIdAndCtgId(String perId, String ctgId) {
		// TODO Auto-generated method stub
		return null;
	}

}
