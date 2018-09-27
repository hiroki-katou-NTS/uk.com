package nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * 表示する勤怠項目
 * @author tutk
 *
 */
@Getter
public class DisplayTimeItem extends DomainObject  {
	/**並び順*/
	private int displayOrder;
	/**表示する項目*/
	private int itemDaily;
	/**列幅*/
	private Optional<Integer> columnWidthTable;

	public DisplayTimeItem(int displayOrder, int itemDaily, Integer columnWidthTable) {
		super();
		this.displayOrder = displayOrder;
		this.itemDaily = itemDaily;
		this.columnWidthTable = Optional.ofNullable(columnWidthTable);
	}
	
	public DisplayTimeItem clone() {
		return new DisplayTimeItem(displayOrder,itemDaily,columnWidthTable.isPresent()?columnWidthTable.get():null);
	}
	
}
