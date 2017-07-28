package nts.uk.ctx.at.record.dom.dailyperformanceformat;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class BusinessFormatSheet {

	private String companyId;

	/**
	 * padding left "0"
	 */
	private BusinessTypeCode businessTypeCode;

	private BigDecimal sheetNo;

	private String sheetName;

	public BusinessFormatSheet(String companyId, BusinessTypeCode businessTypeCode, BigDecimal sheetNo,
			String sheetName) {
		super();
		this.companyId = companyId;
		this.businessTypeCode = businessTypeCode;
		this.sheetNo = sheetNo;
		this.sheetName = sheetName;
	}

	public static BusinessFormatSheet createJavaTye(String companyId, String businessTypeCode, BigDecimal sheetNo,
			String sheetName) {
		return new BusinessFormatSheet(companyId, new BusinessTypeCode(businessTypeCode), sheetNo, sheetName);
	}

}
