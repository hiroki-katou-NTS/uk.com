/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.classification;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.find.company.organization.classification.ClassificationFinder;
import nts.uk.ctx.basic.app.find.company.organization.classification.dto.ClassificationFindDto;

/**
 * The Class ManagementCategoryWs.
 */
@Path("basic/company/organization/classification")
@Produces(MediaType.APPLICATION_JSON)
public class ClassificationWs  extends WebService{

	/** The finder. */
	@Inject
	private ClassificationFinder finder;
	
	
	/**
	 * Inits the.
	 *
	 * @return the list
	 */
	@Path("findAll")
	@POST
	public List<ClassificationFindDto> init() {
		return this.finder.findAll();
	}
}
