package nts.uk.ctx.exio.dom.input.setting.assembly.revise.reviseddata;

import java.util.Optional;

import lombok.Value;
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
}
