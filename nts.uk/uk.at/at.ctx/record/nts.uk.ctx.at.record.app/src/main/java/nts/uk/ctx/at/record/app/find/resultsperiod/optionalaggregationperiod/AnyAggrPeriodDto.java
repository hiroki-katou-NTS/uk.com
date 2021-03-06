package nts.uk.ctx.at.record.app.find.resultsperiod.optionalaggregationperiod;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.AnyAggrPeriod;

/**
 * The class Any aggr period dto.
 *
 * @author nws-minhnb
 */
@Data
@NoArgsConstructor
public class AnyAggrPeriodDto implements AnyAggrPeriod.MementoSetter {

	/** 会社ID */
	private String companyId;

	/** 任意集計枠コード */
	private String aggrFrameCode;

	/** 任意集計名称 */
	private String optionalAggrName;

	/** 対象期間 */
	private GeneralDate startDate;

	/** 対象期間 */
	private GeneralDate endDate;

	/**
	 * Creates from domain.
	 *
	 * @param domain the domain 任意集計期間
	 * @return the dto 任意集計期間
	 */
	public static AnyAggrPeriodDto createFromDomain(AnyAggrPeriod domain) {
		if (domain == null) {
			return null;
		}
		AnyAggrPeriodDto dto = new AnyAggrPeriodDto();
		domain.setMemento(dto);
		return dto;
	}

}
