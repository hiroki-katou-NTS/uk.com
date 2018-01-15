package nts.uk.ctx.at.function.dom.dailyperformanceformat;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class AuthorityFomatDaily extends AggregateRoot {

	private String companyId;

	private DailyPerformanceFormatCode dailyPerformanceFormatCode;

	private int attendanceItemId;

	private BigDecimal sheetNo;

	private int displayOrder;

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
}
