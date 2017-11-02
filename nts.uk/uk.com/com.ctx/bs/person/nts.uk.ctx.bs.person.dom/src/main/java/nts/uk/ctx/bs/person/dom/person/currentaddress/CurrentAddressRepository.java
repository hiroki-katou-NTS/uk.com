/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.currentaddress;

import nts.arc.time.GeneralDate;

/**
 * @author danpv
 *
 */
public interface CurrentAddressRepository {

	public CurrentAddress get(String personId, GeneralDate standandDate);
	
	public CurrentAddress getCurAddById(String curAddId);
}
