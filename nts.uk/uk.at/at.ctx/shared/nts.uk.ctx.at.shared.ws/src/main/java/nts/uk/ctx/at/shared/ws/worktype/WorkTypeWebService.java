/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.worktype;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeFinder;

@Path("at/share/worktype")
@Produces("application/json")
public class WorkTypeWebService extends WebService {

	@Inject
	private WorkTypeFinder find;

	@POST
	@Path("getpossibleworktype")
	public List<WorkTypeDto> getPossibleWorkType(List<String> lstPossible) {
		return this.find.getPossibleWorkType(lstPossible);
	}

	/**
	 * Find by CID and DisplayAtr = display (added by sonnh1)
	 * 
	 * @return list WorkTypeDto
	 */
	@POST
	@Path("getByCIdAndDisplayAtr")
	public List<WorkTypeDto> getByCIdAndDisplayAtr() {
		return this.find.findByCIdAndDisplayAtr();
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<WorkTypeDto> findAll() {
		return this.find.findByCompanyId();
	}
	
	/**
	 * Find by id.
	 *
	 * @param workTypeCode the work type code
	 * @return the work type dto
	 */
	@POST
	@Path("findById/{workTypeCode}")
	public WorkTypeDto findById(@PathParam("workTypeCode") String workTypeCode){
		return this.find.findById(workTypeCode);
	}
}
