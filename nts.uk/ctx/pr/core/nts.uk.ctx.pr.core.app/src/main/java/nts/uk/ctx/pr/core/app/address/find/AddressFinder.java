/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.address.find;

import java.util.List;

/**
 * The Interface AddressFinder.
 */
public interface AddressFinder {

	public List<AddressSelection> findAddressSelectionList(String zipCode);

}
