package nts.uk.ctx.exio.dom.input.revise.reviseddata;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.DataItemList;

/**
 * 編集済みの1行分のデータ
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RevisedDataRecord {

	/** 編集済みの項目 */
	DataItemList items;
	
	public Optional<DataItem> getItemByNo(int itemNo) {
		return items.getItemByNo(itemNo);
	}
	
	public void addItemList(DataItemList itemList) {
		this.items.addItemList(itemList);
	}
}
