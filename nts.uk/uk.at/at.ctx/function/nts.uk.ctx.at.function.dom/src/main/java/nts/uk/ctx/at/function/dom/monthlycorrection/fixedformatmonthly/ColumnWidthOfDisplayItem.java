package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
/**
 * 表示項目の列幅
 * @author tutk
 *
 */
@Getter
public class ColumnWidthOfDisplayItem extends DomainObject {
	/**勤怠項目ID*/
	private int attdanceItemID;
	/**列幅*/
	private int columnWidthTable;
	public ColumnWidthOfDisplayItem(int attdanceItemID, int columnWidthTable) {
		super();
		this.attdanceItemID = attdanceItemID;
		this.columnWidthTable = columnWidthTable;
	}

}
