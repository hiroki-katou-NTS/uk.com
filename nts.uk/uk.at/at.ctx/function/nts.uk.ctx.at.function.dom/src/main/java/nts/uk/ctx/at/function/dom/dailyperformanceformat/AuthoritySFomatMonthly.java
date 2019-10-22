package nts.uk.ctx.at.function.dom.dailyperformanceformat;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 
 * @author anhdt
 * 日次表示項目シート一覧
 *
 */
@Getter
public class AuthoritySFomatMonthly extends AggregateRoot {


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
	private BigDecimal columnWidthTable;

	public AuthoritySFomatMonthly(
			int attendanceItemId, int displayOrder, BigDecimal columnWidth) {
		super();
		this.attendanceItemId = attendanceItemId;
		this.displayOrder = displayOrder;
		this.columnWidthTable = columnWidth;
	}

	public static AuthoritySFomatMonthly createFromJavaType(int attendanceItemId, int displayOrder) {
		return new AuthoritySFomatMonthly(attendanceItemId, displayOrder, null);
		
	}
}
