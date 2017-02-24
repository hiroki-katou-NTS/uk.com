package nts.uk.ctx.pr.core.ws.rule.employment.unitprice;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.error.BusinessException;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.command.CreateUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.command.CreateUnitPriceHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.command.DeleteUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.command.DeleteUnitPriceHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.command.UpdateUnitPriceHistoryCommand;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.command.UpdateUnitPriceHistoryCommandHandler;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.find.UnitPriceHistoryDto;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.find.UnitPriceHistoryFinder;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.find.UnitPriceItemDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UnitPriceHistoryWebService.
 */
@Path("pr/proto/unitprice")
@Produces("application/json")
public class UnitPriceHistoryWs extends WebService {

	/** The unit price history finder. */
	@Inject
	private UnitPriceHistoryFinder unitPriceHistoryFinder;

	/** The create unit price history command handler. */
	@Inject
	private CreateUnitPriceHistoryCommandHandler createUnitPriceHistoryCommandHandler;

	/** The update unit price history command handler. */
	@Inject
	private UpdateUnitPriceHistoryCommandHandler updateUnitPriceHistoryCommandHandler;

	/** The delete unit price history command handler. */
	@Inject
	private DeleteUnitPriceHistoryCommandHandler deleteUnitPriceHistoryCommandHandler;

	/**
	 * Find all.
	 *
	 * @return the list
	 */
	@POST
	@Path("findall")
	public List<UnitPriceItemDto> findAll() {
		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		// Return
		return this.unitPriceHistoryFinder.findAll(companyCode);
	}

	/**
	 * Find.
	 *
	 * @param id
	 *            the id
	 * @return the unit price history dto
	 */
	@POST
	@Path("find/{id}")
	public UnitPriceHistoryDto find(@PathParam("id") String id) {
		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		Optional<UnitPriceHistoryDto> optHistoryDto = unitPriceHistoryFinder.find(companyCode, id);

		if (!optHistoryDto.isPresent()) {
			throw new BusinessException("????");
		}

		return optHistoryDto.get();
	}

	/**
	 * Creates the.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("create")
	public void create(CreateUnitPriceHistoryCommand command) {
		this.createUnitPriceHistoryCommandHandler.handle(command);
	}

	/**
	 * Update.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("update")
	public void update(UpdateUnitPriceHistoryCommand command) {
		this.updateUnitPriceHistoryCommandHandler.handle(command);
	}

	/**
	 * Removes the.
	 *
	 * @param command
	 *            the command
	 */
	@POST
	@Path("remove")
	public void remove(DeleteUnitPriceHistoryCommand command) {
		this.deleteUnitPriceHistoryCommandHandler.handle(command);
	}
}
