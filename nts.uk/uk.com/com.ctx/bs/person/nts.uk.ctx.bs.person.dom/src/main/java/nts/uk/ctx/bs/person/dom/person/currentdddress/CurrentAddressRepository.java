/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.currentdddress;

import nts.arc.time.GeneralDate;

/**
 * @author danpv
 *
 */
public interface CurrentAddressRepository {

	public CurrentAddress get(String personId, GeneralDate standandDate);
	
}
