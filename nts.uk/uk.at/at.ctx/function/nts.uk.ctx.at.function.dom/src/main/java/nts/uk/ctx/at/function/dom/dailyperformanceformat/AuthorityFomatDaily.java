package nts.uk.ctx.at.function.dom.dailyperformanceformat;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

/**
 * 
 * @author nampt
 * 日次表示項目シート一覧
 *
 */
@Getter
public class AuthorityFomatDaily extends AggregateRoot {

	private String companyId;

	private DailyPerformanceFormatCode dailyPerformanceFormatCode;

	/**
	 * 表示する項目
	 */
	private int attendanceItemId;

	/**
	 * シートNO
	 */
	private BigDecimal sheetNo;

	/**
	 * 並び順
	 */
	private int displayOrder;

	/**
	 * 表の列幅
	 */
	private BigDecimal columnWidth;

	public AuthorityFomatDaily(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode,
			int attendanceItemId, BigDecimal sheetNo,
			int displayOrder, BigDecimal columnWidth) {
		super();
		this.companyId = companyId;
		this.dailyPerformanceFormatCode = dailyPerformanceFormatCode;
		this.attendanceItemId = attendanceItemId;
		this.sheetNo = sheetNo;
		this.displayOrder = displayOrder;
		this.columnWidth = columnWidth;
	}

	public static AuthorityFomatDaily createFromJavaType(String companyId, String dailyPerformanceFormatCode,
			int attendanceItemId, BigDecimal sheetNo, int displayOrder,
			BigDecimal columnWidth) {
		return new AuthorityFomatDaily(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode),
				attendanceItemId, sheetNo, displayOrder,
				columnWidth);
	}
	
	public AuthorityFomatDaily clone() {
		return new AuthorityFomatDaily(companyId, dailyPerformanceFormatCode,attendanceItemId,sheetNo,displayOrder,columnWidth);
	}

	public void setSheetNo(BigDecimal sheetNo) {
		this.sheetNo = sheetNo;
	}
	
	
}
