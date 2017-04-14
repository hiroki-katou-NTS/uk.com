/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.address;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.address.find.AddressFinder;
import nts.uk.ctx.pr.core.app.address.find.AddressSelection;

/**
 * The Class AddressWs.
 */
@Path("ctx/pr/core/address")
@Produces("application/json")
public class AddressWs extends WebService {

	/** The finder. */
	@Inject
	private AddressFinder finder;

	/**
	 * Find all.
	 *
	 * @param zipCode
	 *            the zip code
	 * @return the list
	 */
	@POST
	@Path("find/{zipCode}")
	public List<AddressSelection> findAll(@PathParam("zipCode") String zipCode) {
		return this.finder.findAddressSelectionList(zipCode);
	}

}
