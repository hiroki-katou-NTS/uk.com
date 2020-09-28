package nts.uk.ctx.at.function.ws.resultsperiod.optionalaggregationperiod;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod.AggrPeriodDto;
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
	
	/**
	 * Gets the aggr period dto.
	 *	ドメインモデル「任意集計期間」を取得する
	 * @param companyId the company id
	 * @return the aggr period dto
	 */
	@POST
	@Path("getAggrPeriod/{companyId}")
	public AggrPeriodDto getAggrPeriodDto(@PathParam("companyId") String companyId) {
		return this.optionalAggrPeriodFinder.findByCid(companyId);
	}

	/**
	 * Creates the aggr period dto.
	 *	任意集計期間の登録処理
	 * @param dto the dto
	 */
	@POST
	@Path("createAggrPeriod")
	public void createAggrPeriodDto(AggrPeriodDto dto) {
		this.optionalAggrPeriodFinder.addOptionalAggrPeriod(dto);
	}

	/**
	 * Removes the aggr period dto.
	 *	ドメインモデル「任意集計期間」を削除する
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	@POST
	@Path("removeAggrPeriod/{companyId}/{aggrFrameCode}")
	public void removeAggrPeriodDto(@PathParam("companyId") String companyId, @PathParam("aggrFrameCode") String aggrFrameCode) {
		this.optionalAggrPeriodFinder.deleteOptionalAggrPeriod(companyId, aggrFrameCode);
	}
	
	/**
	 * Update aggr period dto.
	 *	任意集計期間を選択する
	 * @param aggrPeriodDto the aggr period dto
	 */
	@POST
	@Path("updateAggrPeriod")
	public void updateAggrPeriodDto(AggrPeriodDto aggrPeriodDto) {
		this.optionalAggrPeriodFinder.updateOptionalAggrPeriod(aggrPeriodDto);
	}

	/**
	 * Check exist aggr period.
	 *	「任意集計期間」の重複をチェックする
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	@POST
	@Path("checkExistAggrPeriod/{companyId}/{aggrFrameCode}")
	public void checkExistAggrPeriod(@PathParam("companyId") String companyId, @PathParam("aggrFrameCode") String aggrFrameCode) {
		this.optionalAggrPeriodFinder.checkExist(companyId, aggrFrameCode);
	}
}
