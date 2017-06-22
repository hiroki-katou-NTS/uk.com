/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.ws.company.organization.classification.history;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.basic.app.find.company.organization.classification.history.ClassificationHistoryFinder;
import nts.uk.ctx.basic.app.find.company.organization.classification.history.dto.ClassificationHistoryDto;
import nts.uk.ctx.basic.app.find.company.organization.classification.history.dto.ClassificationHistoryInDto;

/**
 * The Class ClassificationHistoryWs.
 */
@Path("basic/company/organization/classification/history")
@Produces(MediaType.APPLICATION_JSON)
public class ClassificationHistoryWs extends WebService {
	
	/** The finder. */
	@Inject
	private ClassificationHistoryFinder finder;
	
	/**
	 * Search data.
	 *
	 * @param input the input
	 * @return the list
	 */
	@Path("searchData")
	@POST
	public List<ClassificationHistoryDto> searchData(ClassificationHistoryInDto input) {
		return this.finder.findByClassification(input);
	}
}
