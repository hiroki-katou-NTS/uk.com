package nts.uk.ctx.at.function.app.find.resultsperiod.optionalaggregationperiod;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;

/**
 * The class AnyAggrPeriodDto.
 * 任意集計期間
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnyAggrPeriodDto implements AnyAggrPeriod.MementoSetter {

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

	public static AnyAggrPeriodDto createFromDomain(AnyAggrPeriod domain) {
		AnyAggrPeriodDto dto = new AnyAggrPeriodDto();
		domain.setMemento(dto);
		return dto;
	}
}
