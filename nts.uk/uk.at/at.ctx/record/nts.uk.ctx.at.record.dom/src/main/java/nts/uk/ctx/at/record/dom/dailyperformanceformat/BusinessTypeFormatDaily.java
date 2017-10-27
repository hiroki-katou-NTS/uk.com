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
public class BusinessTypeFormatDaily extends AggregateRoot {

	private String companyId;

	/**
	 * padding left "0"
	 */
	private BusinessTypeCode businessTypeCode;

	private int attendanceItemId;

	private BigDecimal sheetNo;

	private int order;

	private BigDecimal columnWidth;

	public BusinessTypeFormatDaily(String companyId, BusinessTypeCode businessTypeCode, int attendanceItemId,
			BigDecimal sheetNo, int order, BigDecimal columnWidth) {
		super();
		this.companyId = companyId;
		this.businessTypeCode = businessTypeCode;
		this.attendanceItemId = attendanceItemId;
		this.sheetNo = sheetNo;
		this.order = order;
		this.columnWidth = columnWidth;
	}

	public static BusinessTypeFormatDaily createFromJavaType(String companyId, String businessTypeCode,
			int attendanceItemId, BigDecimal sheetNo, int order, BigDecimal columnWidth) {
		return new BusinessTypeFormatDaily(companyId, new BusinessTypeCode(businessTypeCode), attendanceItemId, sheetNo,
				order, columnWidth);
	}
}
