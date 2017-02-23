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
import nts.uk.ctx.pr.core.app.wagetable.command.CertifyGroupAddCommand;
import nts.uk.ctx.pr.core.app.wagetable.command.CertifyGroupAddCommandHandler;
import nts.uk.ctx.pr.core.app.wagetable.command.CertifyGroupDeleteCommand;
import nts.uk.ctx.pr.core.app.wagetable.command.CertifyGroupDeleteCommandHandler;
import nts.uk.ctx.pr.core.app.wagetable.command.CertifyGroupUpdateCommand;
import nts.uk.ctx.pr.core.app.wagetable.command.CertifyGroupUpdateCommandHandler;
import nts.uk.ctx.pr.core.app.wagetable.find.CertifyGroupFinder;
import nts.uk.ctx.pr.core.app.wagetable.find.dto.CertifyGroupFindDto;
import nts.uk.ctx.pr.core.app.wagetable.find.dto.CertifyGroupFindOutDto;

@Path("pr/wagetable/certifygroup")
@Produces("application/json")
public class CertifyGroupWs extends WebService {

	/** The find. */
	@Inject
	private CertifyGroupFinder find;

	/** The add. */
	@Inject
	private CertifyGroupAddCommandHandler add;

	/** The update. */
	@Inject
	private CertifyGroupUpdateCommandHandler update;

	/** The delete. */
	@Inject
	private CertifyGroupDeleteCommandHandler delete;

	/**
	 * Find all.
	 *
	 * @param companyCode
	 *            the company code
	 * @return the list
	 */
	@POST
	@Path("findall")
	public List<CertifyGroupFindOutDto> findAll() {
		return find.findAll();
	}

	@POST
	@Path("find/{code}")
	public CertifyGroupFindDto find(@PathParam("code") String code) {
		return find.find(code);
	}

	/**
	 * Adds the certify group.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("add")
	public void addCertifyGroup(CertifyGroupAddCommand command) {
		this.add.handle(command);
	}

	/**
	 * Update certify group.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("update")
	public void updateCertifyGroup(CertifyGroupUpdateCommand command) {
		this.update.handle(command);
	}

	/**
	 * Update certify group.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("delete")
	public void deleteCertifyGroup(CertifyGroupDeleteCommand command) {
		this.delete.handle(command);
	}
}
