package nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.resultsperiod.optionalaggregationperiod.AnyAggrPeriodImport;

/**
 * The class AnyAggrPeriodDto.
 * 任意集計期間
 */
@Data
@AllArgsConstructor
public class AnyAggrPeriodDto {

	/**  会社ID. */
	private String companyId;

	/**  任意集計枠コード. */
	private String aggrFrameCode;

	/**  任意集計名称. */
	private String optionalAggrName;

	/**  対象期間. */
	private GeneralDate startDate;

	/**  対象期間. */
	private GeneralDate endDate;

	/**
	 * No args constructor.
	 */
	private AnyAggrPeriodDto() {
	}

	/**
	 * Creates from import.
	 *
	 * @param aggrPeriodImport the import 任意集計期間
	 * @return the dto 任意集計期間
	 */
	public static AnyAggrPeriodDto createFromImport(AnyAggrPeriodImport aggrPeriodImport) {
		if (aggrPeriodImport == null) {
			return null;
		}
		return new AnyAggrPeriodDto(aggrPeriodImport.getCompanyId(),
									aggrPeriodImport.getAggrFrameCode(),
									aggrPeriodImport.getOptionalAggrName(),
									aggrPeriodImport.getPeriod().start(),
									aggrPeriodImport.getPeriod().end());
	}

}
