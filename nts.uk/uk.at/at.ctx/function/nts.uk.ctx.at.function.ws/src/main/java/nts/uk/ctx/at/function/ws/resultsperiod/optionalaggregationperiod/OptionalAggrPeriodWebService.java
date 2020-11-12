package nts.uk.ctx.at.function.ws.resultsperiod.optionalaggregationperiod;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.resultsperiod.optionalaggregationperiod.OptionalAggregationPeriodCommand;
import nts.uk.ctx.at.function.app.command.resultsperiod.optionalaggregationperiod.RemoveOptionalAggregationPeriodCommandHandler;
import nts.uk.ctx.at.function.app.command.resultsperiod.optionalaggregationperiod.SaveOptionalAggregationPeriodCommandHandler;
import nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod.AnyAggrPeriodDto;
import nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodImportFinder;

/**
 * The Class OptionalAggrPeriodWebService.
 */
@Path("at/function/resultsperiod")
@Produces("application/json")
public class OptionalAggrPeriodWebService extends WebService {

	/** The optional aggr period finder. */
	@Inject
	private OptionalAggrPeriodImportFinder optionalAggrPeriodFinder;
	
	@Inject
	private SaveOptionalAggregationPeriodCommandHandler saveHandler;
	
	@Inject
	private RemoveOptionalAggregationPeriodCommandHandler removeHandler;
	
	/**
	 * Gets the all dtos.
	 *	ドメインモデル「任意集計期間」を取得する
	 * @return the all dtos
	 */
	@POST
	@Path("findAll")
	public List<AnyAggrPeriodDto> getAllDtos() {
		return this.optionalAggrPeriodFinder.findAll();
	}
	
	/**
	 * Save.
	 *	任意集計期間の登録処理
	 * @param command the command
	 */
	@POST
	@Path("save")
	public void save(OptionalAggregationPeriodCommand command) {
		this.saveHandler.handle(command);
	}

	/**
	 * Removes the aggr period dto.
	 *	ドメインモデル「任意集計期間」を削除する
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	@POST
	@Path("removeAggrPeriod/{aggrFrameCode}")
	public void removeAggrPeriodDto(@PathParam("aggrFrameCode") String aggrFrameCode) {
		this.removeHandler.delete(aggrFrameCode);
	}
	
}
