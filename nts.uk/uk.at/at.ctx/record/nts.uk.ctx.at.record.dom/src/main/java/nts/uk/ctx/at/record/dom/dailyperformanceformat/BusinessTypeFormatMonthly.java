package nts.uk.ctx.at.record.dom.dailyperformanceformat;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class BusinessTypeFormatMonthly extends AggregateRoot {

	private String companyId;

	/**
	 * padding left "0"
	 */
	private BusinessTypeCode businessTypeCode;

	private int attendanceItemId;

	private int order;

	private BigDecimal columnWidth;

	public BusinessTypeFormatMonthly(String companyId, BusinessTypeCode businessTypeCode, int attendanceItemId, int order,
			BigDecimal columnWidth) {
		super();
		this.companyId = companyId;
		this.businessTypeCode = businessTypeCode;
		this.attendanceItemId = attendanceItemId;
		this.order = order;
		this.columnWidth = columnWidth;
	}

	public static BusinessTypeFormatMonthly createFromJavaType(String companyId, String businessTypeCode,
			int attendanceItemId, int order, BigDecimal columnWidth) {
		return new BusinessTypeFormatMonthly(companyId, new BusinessTypeCode(businessTypeCode), attendanceItemId, order,
				columnWidth);
	}
}
