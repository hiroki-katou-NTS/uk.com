/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.address.find;

import java.util.List;

import javax.ejb.Stateless;

/**
 * The Interface AddressFinder.
 */

public interface AddressFinder {

	/**
	 * Find address selection list.
	 *
	 * @param zipCode the zip code
	 * @return the list
	 */
	public List<AddressSelection> findAddressSelectionList(String zipCode);

}
