/**
 * 
 */
package repository.person.currentaddress;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddress;
import nts.uk.ctx.bs.person.dom.person.currentaddress.CurrentAddressRepository;

/**
 * @author danpv
 *
 */
@Stateless
public class CurrentAddRepoImpl implements CurrentAddressRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.person.dom.person.currentdddress.CurrentAddressRepository#get(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public CurrentAddress get(String personId, GeneralDate standandDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
