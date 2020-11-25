package nts.uk.ctx.at.schedule.app.command.schedule.employeeinfo.sortsetting;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderListDto2 {
	// 0: Sort_ASC 1: SortDESC
	private int sortOrder;

	private int sortType;

	public OrderListDto2(int sortOrder, int sortType) {
		super();
		this.sortOrder = sortOrder;
		this.sortType = sortType;
	}

	
}


