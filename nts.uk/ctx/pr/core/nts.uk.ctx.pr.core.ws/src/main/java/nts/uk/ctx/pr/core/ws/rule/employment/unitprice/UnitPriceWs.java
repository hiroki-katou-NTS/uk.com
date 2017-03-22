/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.rule.employment.unitprice;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository;

/**
 * The Class UnitPriceWs.
 */
@Path("pr/proto/unitprice")
@Produces(MediaType.APPLICATION_JSON)
public class UnitPriceWs {

	/** The unit price repo. */
	@Inject
	private UnitPriceRepository unitPriceRepo;

	/**
	 * Find.
	 *
	 * @param baseDate the base date
	 * @return the list
	 */
	@POST
	@Path("getCompanyUnitPrice/{baseDate}")
	public List<String> find(@PathParam("baseDate") Integer baseDate) {
		List<String> listUnitPriceCode = unitPriceRepo.getCompanyUnitPriceCode(baseDate).stream().map(code -> code.v())
				.collect(Collectors.toList());
		return listUnitPriceCode;
	}

}
