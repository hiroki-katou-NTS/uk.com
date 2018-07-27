package nts.uk.ctx.at.function.dom.dailyperformanceformat;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.dailyperformanceformat.primitivevalue.DailyPerformanceFormatCode;

/**
 * 
 * @author nampt
 * 月次表示項目一覧
 *
 */
@Getter
public class AuthorityFomatMonthly extends AggregateRoot {

	private String companyId;

	/**
	 * 日別実績フォーマットコード
	 */
	private DailyPerformanceFormatCode dailyPerformanceFormatCode;

	/**
	 * 勤怠項目ID
	 */
	private int attendanceItemId;

	/**
	 * 並び順
	 */
	private int displayOrder;

	/**
	 * 表の列幅
	 */
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
