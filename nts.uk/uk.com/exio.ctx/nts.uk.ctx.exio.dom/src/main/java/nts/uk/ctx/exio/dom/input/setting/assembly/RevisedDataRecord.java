package nts.uk.ctx.exio.dom.input.setting.assembly;

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
		val newItems = items.stream()
				.map(item -> item.getItemNo() == newDataItem.getItemNo() ? newDataItem : item)
				.collect(Collectors.toList());
		return new RevisedDataRecord(this.rowNo, new DataItemList(newItems));
	}
}
