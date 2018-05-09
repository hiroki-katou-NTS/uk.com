package nts.uk.ctx.at.function.app.command.monthlycorrection.fixedformatmonthly;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.DisplayTimeItem;

@Getter
@Setter
@NoArgsConstructor
public class DisplayTimeItemCmd {
	/**並び順*/
	private int displayOrder;
	/**表示する項目*/
	private int itemDaily;
	/**列幅*/
	private Integer columnWidthTable;
	public DisplayTimeItemCmd(int displayOrder, int itemDaily, Integer columnWidthTable) {
		super();
		this.displayOrder = displayOrder;
		this.itemDaily = itemDaily;
		this.columnWidthTable = columnWidthTable;
	}
	
	public static DisplayTimeItem fromCommand(DisplayTimeItemCmd command) {
		return new DisplayTimeItem(
				command.getDisplayOrder(),
				command.getItemDaily(),
				command.getColumnWidthTable()
				);
	}

}
