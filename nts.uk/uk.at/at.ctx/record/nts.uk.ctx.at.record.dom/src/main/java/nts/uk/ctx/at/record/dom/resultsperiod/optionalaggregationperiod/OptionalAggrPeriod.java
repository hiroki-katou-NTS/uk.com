package nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.primitivevalue.AggrFrameCode;
import nts.uk.ctx.at.record.dom.resultsperiod.optionalaggregationperiod.primitivevalue.OptionalAggrName;

/**
 * 任意集計期間
 * 
 * @author phongtq
 *
 */
@Getter
public class OptionalAggrPeriod extends AggregateRoot {

	/** 会社ID */
	private String companyId;

	/** 任意集計枠コード */
	private AggrFrameCode aggrFrameCode;

	/** 任意集計名称 */
	private OptionalAggrName optionalAggrName;

	/** 対象期間 */
	private GeneralDate startDate;

	/** 対象期間 */
	private GeneralDate endDate;
	

	/**
	 * Contructor
	 * @param companyId
	 * @param aggrFrameCode
	 * @param optionalAggrName
	 * @param startDate
	 * @param endDate
	 * @param peopleNo
	 */
	public OptionalAggrPeriod(String companyId, AggrFrameCode aggrFrameCode, OptionalAggrName optionalAggrName,
			GeneralDate startDate, GeneralDate endDate) {
		super();
		this.companyId = companyId;
		this.aggrFrameCode = aggrFrameCode;
		this.optionalAggrName = optionalAggrName;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * Create From Java Type
	 * @param companyId
	 * @param aggrFrameCode
	 * @param optionalAggrName
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static OptionalAggrPeriod createFromJavaType(String companyId, String aggrFrameCode, String optionalAggrName,
			GeneralDate startDate, GeneralDate endDate) {

		return new OptionalAggrPeriod(companyId, new AggrFrameCode(aggrFrameCode),
				new OptionalAggrName(optionalAggrName), startDate, endDate);
	}
}
