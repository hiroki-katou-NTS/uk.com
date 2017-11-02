/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.currentaddress;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * @author danpv
 *
 */
public interface CurrentAddressRepository {

	public CurrentAddress get(String personId, GeneralDate standandDate);
	
	public List<CurrentAddress> getListByPid(String pid);
	
	public CurrentAddress getCurAddById(String curAddId);
}
