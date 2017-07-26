package nts.uk.ctx.at.record.dom.dailyperformanceformat;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.WorkTypeCode;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class WorkTypeFormatMonthly extends AggregateRoot {

	private String companyId;

	/**
	 * padding left "0"
	 */
	private WorkTypeCode workTypeCode;

	private String attendanceItemId;

	private BigDecimal order;

	private BigDecimal columnWidth;

	public WorkTypeFormatMonthly(String companyId, WorkTypeCode workTypeCode, String attendanceItemId, BigDecimal order,
			BigDecimal columnWidth) {
		super();
		this.companyId = companyId;
		this.workTypeCode = workTypeCode;
		this.attendanceItemId = attendanceItemId;
		this.order = order;
		this.columnWidth = columnWidth;
	}

	public static WorkTypeFormatMonthly createFromJavaType(String companyId, String workTypeCode,
			String attendanceItemId, BigDecimal order, BigDecimal columnWidth) {
		return new WorkTypeFormatMonthly(companyId, new WorkTypeCode(workTypeCode), attendanceItemId, order,
				columnWidth);
	}
}
