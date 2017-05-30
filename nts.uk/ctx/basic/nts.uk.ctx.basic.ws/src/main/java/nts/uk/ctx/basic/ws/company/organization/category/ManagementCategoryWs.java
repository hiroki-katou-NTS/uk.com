/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.category;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.find.company.organization.category.ManagementCategoryFinder;
import nts.uk.ctx.basic.app.find.company.organization.category.dto.ManagementCategoryFindDto;

/**
 * The Class ManagementCategoryWs.
 */
@Path("basic/company/organization/management/category")
@Produces(MediaType.APPLICATION_JSON)
public class ManagementCategoryWs  extends WebService{

	/** The finder. */
	@Inject
	private ManagementCategoryFinder finder;
	
	
	/**
	 * Inits the.
	 *
	 * @return the list
	 */
	@Path("findAll")
	@POST
	public List<ManagementCategoryFindDto> init() {
		return this.finder.findAll();
	}
}
