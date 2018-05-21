package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.DisplayTimeItem;

@Getter
@Setter
@NoArgsConstructor
public class DisplayTimeItemDto {
	/**並び順*/
	private int displayOrder;
	/**表示する項目*/
	private int itemDaily;
	/**列幅*/
	private Integer columnWidthTable;
	public DisplayTimeItemDto(int displayOrder, int itemDaily, Integer columnWidthTable) {
		super();
		this.displayOrder = displayOrder;
		this.itemDaily = itemDaily;
		this.columnWidthTable = columnWidthTable;
	}
	
	public static DisplayTimeItemDto fromDomain(DisplayTimeItem domain) {
		return new DisplayTimeItemDto(
				domain.getDisplayOrder(),
				domain.getItemDaily(),
				!domain.getColumnWidthTable().isPresent()?null:domain.getColumnWidthTable().get()
				);
	}

}
