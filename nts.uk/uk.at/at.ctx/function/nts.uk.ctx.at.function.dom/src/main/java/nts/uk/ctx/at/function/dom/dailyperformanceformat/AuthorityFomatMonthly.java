package nts.uk.ctx.at.function.dom.dailyperformanceformat;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatName;

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

	private DailyPerformanceFormatName dailyPerformanceFormatName;

	private int displayOrder;

	private BigDecimal columnWidth;

	public AuthorityFomatMonthly(String companyId, DailyPerformanceFormatCode dailyPerformanceFormatCode,
			int attendanceItemId, DailyPerformanceFormatName dailyPerformanceFormatName, int displayOrder,
			BigDecimal columnWidth) {
		super();
		this.companyId = companyId;
		this.dailyPerformanceFormatCode = dailyPerformanceFormatCode;
		this.attendanceItemId = attendanceItemId;
		this.dailyPerformanceFormatName = dailyPerformanceFormatName;
		this.displayOrder = displayOrder;
		this.columnWidth = columnWidth;
	}

	public static AuthorityFomatMonthly createFromJavaType(String companyId, String dailyPerformanceFormatCode,
			int attendanceItemId, String dailyPerformanceFormatName, int displayOrder, BigDecimal columnWidth) {
		return new AuthorityFomatMonthly(companyId, new DailyPerformanceFormatCode(dailyPerformanceFormatCode),
				attendanceItemId, new DailyPerformanceFormatName(dailyPerformanceFormatName), displayOrder,
				columnWidth);
	}
}
