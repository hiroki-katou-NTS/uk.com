package nts.uk.ctx.at.record.dom.dailyperformanceformat;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.SheetName;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.WorkTypeCode;

/**
 * 
 * @author nampt
 *
 */
@Getter
public class WorkTypeFormatDaily extends AggregateRoot {

	private String companyId;

	/**
	 * padding left "0"
	 */
	private WorkTypeCode workTypeCode;

	private String attendanceItemId;

	private SheetName sheetName;

	private BigDecimal sheetNo;

	private BigDecimal order;

	private BigDecimal columnWidth;

	public WorkTypeFormatDaily(String companyId, WorkTypeCode workTypeCode, String attendanceItemId,
			SheetName sheetName, BigDecimal sheetNo, BigDecimal order, BigDecimal columnWidth) {
		super();
		this.companyId = companyId;
		this.workTypeCode = workTypeCode;
		this.attendanceItemId = attendanceItemId;
		this.sheetName = sheetName;
		this.sheetNo = sheetNo;
		this.order = order;
		this.columnWidth = columnWidth;
	}

	public static WorkTypeFormatDaily createFromJavaType(String companyId, String workTypeCode, String attendanceItemId,
			String sheetName, BigDecimal sheetNo, BigDecimal order, BigDecimal columnWidth) {
		return new WorkTypeFormatDaily(companyId, new WorkTypeCode(workTypeCode), attendanceItemId,
				new SheetName(sheetName), sheetNo, order, columnWidth);
	}
}
