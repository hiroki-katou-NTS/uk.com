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
	 * 勤務種別コード
	 */
	private BusinessTypeCode businessTypeCode;

	/**
	 * 並び順
	 */
	private BigDecimal sheetNo;

	/**
	 * 日別実績のフォーマットのシート名
	 */
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
	
	public BusinessFormatSheet clone() {
		return new BusinessFormatSheet(companyId, businessTypeCode,sheetNo,sheetName);
	}

	public void setSheetNo(BigDecimal sheetNo) {
		this.sheetNo = sheetNo;
	}
	

	
}
