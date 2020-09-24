package nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod;

import java.util.Optional;

import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodAdapter;
import nts.uk.ctx.at.function.dom.resultsperiod.optionalaggregationperiod.OptionalAggrPeriodImport;

/**
 * The Class OptionalAggrPeriodFinder.
 */
public class OptionalAggrPeriodFinder {

	/** The optional aggr period adapter. */
	@Inject
	private OptionalAggrPeriodAdapter optionalAggrPeriodAdapter;
	
	/**
	 * Find by cid.
	 * 	ドメインモデル「任意集計期間」を取得する
	 *
	 * @param companyId the company id
	 * @return the aggr period dto
	 */
	public AggrPeriodDto findByCid(String companyId) {
		// Step ドメインモデル「任意集計期間」を取得する
		Optional<OptionalAggrPeriodImport> optionalAggrPeriod = this.optionalAggrPeriodAdapter.findByCid(companyId);
		//	「任意集計期間」を取得できたかチェックする
		if (!optionalAggrPeriod.isPresent()) {
			return null;
		}
		return this.optionalAggrPeriodAdapter.findByCid(companyId)
				.map(item -> AggrPeriodDto.builder()
					.aggrFrameCode(item.getAggrFrameCode())
					.companyId(item.getCompanyId())
					.endDate(item.getEndDate())
					.startDate(item.getStartDate())
					.optionalAggrName(item.getOptionalAggrName())
					.build()
				).get();
	}
	
	/**
	 * Adds the aggr period dto.
	 *	任意集計期間の登録処理
	 * @param the aggr period dto
	 */
	public void addOptionalAggrPeriod(AggrPeriodDto dto) {
		// Step 1 「任意集計期間」の重複をチェックする
		boolean isExist = this.checkExist(dto.getCompanyId(), dto.getAggrFrameCode());
		// Is duplicated
		if (isExist) {
			//	エラーメッセージを表示する
			return;
		}
		// Step 2 ドメインモデル「任意集計期間」を登録する
		this.optionalAggrPeriodAdapter.addOptionalAggrPeriod(OptionalAggrPeriodImport.builder()
				.aggrFrameCode(dto.getAggrFrameCode())
				.companyId(dto.getCompanyId())
				.optionalAggrName(dto.getOptionalAggrName())
				.startDate(dto.getStartDate())
				.endDate(dto.getEndDate())
				.build());
	}
	
	/**
	 * Delete optional aggr period.
	 *	ドメインモデル「任意集計期間」を削除する
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 */
	public void deleteOptionalAggrPeriod(String companyId, String aggrFrameCode) {
		this.optionalAggrPeriodAdapter.deleteOptionalAggrPeriod(companyId, aggrFrameCode);
	}
	
	/**
	 * Update optional aggr period.
	 *	任意集計期間を選択する
	 * @param optionalAggrPeriod the optional aggr period
	 */
	public void updateOptionalAggrPeriod(AggrPeriodDto dto) {
		this.optionalAggrPeriodAdapter.updateOptionalAggrPeriod(OptionalAggrPeriodImport.builder()
				.aggrFrameCode(dto.getAggrFrameCode())
				.companyId(dto.getCompanyId())
				.optionalAggrName(dto.getOptionalAggrName())
				.startDate(dto.getStartDate())
				.endDate(dto.getEndDate())
				.build());
		
	}
	
	/**
	 * Check exist.
	 *
	 * @param companyId the company id
	 * @param aggrFrameCode the aggr frame code
	 * @return true, if successful
	 */
	public boolean checkExist(String companyId, String aggrFrameCode) {
		return this.optionalAggrPeriodAdapter.checkExist(companyId, aggrFrameCode);
	}
}
