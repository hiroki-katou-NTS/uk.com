/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.ws.shift.pattern;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.MonthlyPatternFinder;
import nts.uk.ctx.at.schedule.app.find.shift.pattern.dto.MonthlyPatternDto;

/**
 * The Class MonthlyPatternWs.
 */
@Path("ctx/at/schedule/pattern/monthy")
@Produces(MediaType.APPLICATION_JSON)
public class MonthlyPatternWs extends WebService{

	/** The finder. */
	@Inject
	private MonthlyPatternFinder finder;
	
	
	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findAll")
	public List<MonthlyPatternDto> findAll(){
		return this.finder.findAll();
	}
	
	/**
	 * Find by id.
	 *
	 * @param monthlyPatternCode the monthly pattern code
	 * @return the monthly pattern dto
	 */
	@POST
	@Path("findById/{monthlyPatternCode}")
	public MonthlyPatternDto findById(@PathParam("monthlyPatternCode") String monthlyPatternCode){
		return this.finder.findById(monthlyPatternCode);
	}
}
