package nts.uk.ctx.at.schedule.app.export.horitotalcategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author Hoi1102
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class HoriTotalExel {
	private String code;
	private String name;
	private String memo;
	private List<ItemTotalExcel> listItemTotals;
	public HoriTotalExel() {
		super();
	}

	public void putItemTotalExcel(ItemTotalExcel itemTotalExcel) {
		if (listItemTotals == null) {
			listItemTotals = new ArrayList<>();
			listItemTotals.add(itemTotalExcel);
		}
		else {
			boolean isInList = false;
			for (ItemTotalExcel item : listItemTotals) {
				if (item.getItemNo() == itemTotalExcel.getItemNo()) {
					item = itemTotalExcel;
					isInList = true;
					break;
				}
			}
			if (!isInList) {
				listItemTotals.add(itemTotalExcel);
			}
		}
	}
	
	public Optional<ItemTotalExcel> getItemTotalExcelByItemNo(int itemNo) {
		return listItemTotals.stream().filter(x -> x.getItemNo() == itemNo).findFirst();
	}

	public HoriTotalExel(String code, String name, String memo) {
		super();
		this.code = code;
		this.name = name;
		this.memo = memo;
	}
	
	
	
}
