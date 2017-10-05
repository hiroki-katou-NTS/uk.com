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
public class AuthorityFomatMonthly extends AggregateRoot {

	private String companyId;

	private DailyPerformanceFormatCode dailyPerformanceFormatCode;

	private int attendanceItemId;

	private int displayOrder;

	private BigDecimal columnWidth;

	public AuthorityFomatMonthly(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode,
			int attendanceItemId, int displayOrder,
			BigDecimal columnWidth) {
		super();
		this.companyId = companyId;
		this.dailyPerformanceFormatCode = dailyPerformanceFormatCode;
		this.attendanceItemId = attendanceItemId;
		this.displayOrder = displayOrder;
		this.columnWidth = columnWidth;
	}

	public static AuthorityFomatMonthly createFromJavaType(String companyId, String dailyPerformanceFormatCode,
			int attendanceItemId, int displayOrder, BigDecimal columnWidth) {
		return new AuthorityFomatMonthly(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode),
				attendanceItemId, displayOrder, columnWidth);
	}
}
