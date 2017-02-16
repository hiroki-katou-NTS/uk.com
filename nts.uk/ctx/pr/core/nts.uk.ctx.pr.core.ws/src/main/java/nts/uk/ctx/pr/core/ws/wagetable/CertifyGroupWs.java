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
import nts.uk.ctx.pr.core.app.wagetable.find.CertifyGroupFindInDto;
import nts.uk.ctx.pr.core.app.wagetable.find.CertifyGroupFinder;
import nts.uk.ctx.pr.core.dom.wagetable.CertifyGroup;

@Path("pr/wagetable/certifygroup")
@Produces("application/json")
public class CertifyGroupWs extends WebService {

	/** The find. */
	@Inject
	private CertifyGroupFinder find;

	@POST
	@Path("findall/{companyCode}")
	public List<CertifyGroupFindInDto> findAll(@PathParam("companyCode") String companyCode) {
		return find.findAll(companyCode);
	}
}
