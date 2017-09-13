/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.ws.optionalitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.optionalitem.OptionalItemSaveCommand;
import nts.uk.ctx.at.record.app.command.optionalitem.OptionalItemSaveCommandHandler;
import nts.uk.ctx.at.record.app.find.optionalitem.OptionalItemDto;
import nts.uk.ctx.at.record.app.find.optionalitem.OptionalItemFinder;
import nts.uk.ctx.at.record.app.find.optionalitem.OptionalItemHeaderDto;

/**
 * The Class OptionalItemWs.
 */
@Path("ctx/at/record/optionalitem/")
@Produces("application/json")
public class OptionalItemWs extends WebService {

	/** The finder. */
	@Inject
	private OptionalItemFinder finder;

	/** The save. */
	@Inject
	private OptionalItemSaveCommandHandler handler;

	/**
	 * Find.
	 *
	 * @return the optional item dto
	 */
	@POST
	@Path("find")
	public OptionalItemDto find() {
		return this.finder.find();
	}

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findall")
	public List<OptionalItemHeaderDto> findAll() {
		return this.finder.findAll();
	}

	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(OptionalItemSaveCommand command) {
		this.handler.handle(command);
	}

}
