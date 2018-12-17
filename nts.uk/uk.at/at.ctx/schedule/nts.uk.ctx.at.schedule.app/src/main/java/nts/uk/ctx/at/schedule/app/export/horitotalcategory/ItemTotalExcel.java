package nts.uk.ctx.at.schedule.app.export.horitotalcategory;

import java.util.ArrayList;
import java.util.List;

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
public class ItemTotalExcel {
	private String nameItemTotal;
	private int itemNo;
	private List<ItemCNTSetExcel> itemSets;
	
	public ItemTotalExcel(){
	}
	
	
	
	
	public void putItemTotalExcel(ItemCNTSetExcel itemCNTSetExcel) {
		if (itemSets == null) {
			itemSets = new ArrayList<>();
			itemSets.add(itemCNTSetExcel);
		}
		else {
			boolean isInList = false;
			for (ItemCNTSetExcel item : itemSets) {
				if (item.getCodeItemSet() == itemCNTSetExcel.getCodeItemSet()) {
					item = itemCNTSetExcel;
					break;
				}
			}
			
			if (!isInList) {
				itemSets.add(itemCNTSetExcel);
			}
		}
	}




	public ItemTotalExcel(String nameItemTotal, int itemNo) {
		super();
		this.nameItemTotal = nameItemTotal;
		this.itemNo = itemNo;
	}
}
