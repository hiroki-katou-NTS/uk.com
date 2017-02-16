/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.wagetable;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.wagetable.find.CertificationFindInDto;
import nts.uk.ctx.pr.core.app.wagetable.find.CertificationFinder;

@Path("pr/wagetable/certification")
@Produces("application/json")
public class CertificationWs extends WebService {

	/** The find. */
	@Inject
	private CertificationFinder find;

	/**
	 * Find all.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	@POST
	@Path("findall/{companyCode}")
	public List<CertificationFindInDto> findAll(@PathParam("companyCode") String companyCode) {
		return find.findAll(companyCode);
	}
}
