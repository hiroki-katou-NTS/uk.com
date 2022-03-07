package nts.uk.ctx.exio.dom.input.setting.assembly;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.DataItemList;

/**
 * 編集済みの1行分のデータ
 */
@Value
public class RevisedDataRecord {
	
	/** CSV行番号 */
	int rowNo;

	/** 編集済みの項目 */
	DataItemList items;
	
	public Optional<DataItem> getItemByNo(int itemNo) {
		return items.getItemByNo(itemNo);
	}
	
	public void addItemList(DataItemList itemList) {
		this.items.addAll(itemList);
	}
	
	public RevisedDataRecord replace(DataItem newDataItem) {
		val removedTarget = remove(newDataItem.getItemNo());
		removedTarget.add(newDataItem);
		return new RevisedDataRecord(this.rowNo, new DataItemList(removedTarget)); 
	}
	
	private List<DataItem> remove(int itemNo) {
		return this.items.stream()
								.filter(item -> !(item.getItemNo() == itemNo))
								.collect(Collectors.toList());
	}
}
