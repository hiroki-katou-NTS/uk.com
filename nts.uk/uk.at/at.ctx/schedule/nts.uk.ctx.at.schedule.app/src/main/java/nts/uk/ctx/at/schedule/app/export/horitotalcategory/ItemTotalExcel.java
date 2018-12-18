package nts.uk.ctx.at.schedule.app.export.horitotalcategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.gul.collection.CollectionUtil;

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
	private List<String> itemDaySets;
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
	public String toStringItemSet(){
		String listString = "";
		if(CollectionUtil.isEmpty(itemSets)){
			return listString;
		}
		List<String> B = itemSets.stream()
		        .map(developer -> new String(developer.getNameItemSet()))
		        .collect(Collectors.toList());
		listString = String.join(",", B);
		return listString;
	}

	public String toStringListDaySet(){
		String result ="";
		if(CollectionUtil.isEmpty(itemDaySets)){
			result = String.join(",", itemDaySets);
			return result;
		}
		return result;
	}

	
}
