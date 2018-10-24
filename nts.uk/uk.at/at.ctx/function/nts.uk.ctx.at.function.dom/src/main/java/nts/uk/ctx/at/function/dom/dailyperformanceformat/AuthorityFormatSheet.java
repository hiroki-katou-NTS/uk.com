package nts.uk.ctx.at.function.dom.dailyperformanceformat;

import java.math.BigDecimal;

import lombok.Getter;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AuthorityFormatSheet {

	private String companyId;

	/**
	 * 日別実績フォーマットコード
	 */
	private DailyPerformanceFormatCode dailyPerformanceFormatCode;

	/**
	 * シートNO
	 */
	private BigDecimal sheetNo;

	/**
	 * 日別実績のフォーマットのシート名
	 */
	private String sheetName;

	public AuthorityFormatSheet(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode,
			BigDecimal sheetNo, String sheetName) {
		super();
		this.companyId = companyId;
		this.dailyPerformanceFormatCode = dailyPerformanceFormatCode;
		this.sheetNo = sheetNo;
		this.sheetName = sheetName;
	}

	public static AuthorityFormatSheet createJavaTye(String companyId, String dailyPerformanceFormatCode,
			BigDecimal sheetNo, String sheetName) {
		return new AuthorityFormatSheet(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode), sheetNo,
				sheetName);
	}
	
	public AuthorityFormatSheet clone() {
		return new AuthorityFormatSheet(companyId, dailyPerformanceFormatCode,sheetNo,sheetName);
	}

	public void setSheetNo(BigDecimal sheetNo) {
		this.sheetNo = sheetNo;
	}
	

}
