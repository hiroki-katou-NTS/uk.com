/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ws.optionalitem;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.record.app.command.optitem.applicable.EmpConditionSaveCommand;
import nts.uk.ctx.at.record.app.command.optitem.applicable.EmpConditionSaveCommandHandler;
import nts.uk.ctx.at.record.app.find.optitem.applicable.EmpConditionDto;
import nts.uk.ctx.at.record.app.find.optitem.applicable.EmpConditionFinder;

/**
 * The Class EmpConditionWs.
 */
@Path("ctx/at/record/optionalitem/empcondition")
@Produces("application/json")
public class EmpConditionWs {

	/** The finder. */
	@Inject
	private EmpConditionFinder finder;

	/** The handler. */
	@Inject
	private EmpConditionSaveCommandHandler handler;

	/**
	 * Find.
	 *
	 * @return the emp condition dto
	 */
	@POST
	@Path("find")
	public EmpConditionDto find() {
		return this.finder.find();
	}

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(EmpConditionSaveCommand command) {
		this.handler.handle(command);
	}
}
