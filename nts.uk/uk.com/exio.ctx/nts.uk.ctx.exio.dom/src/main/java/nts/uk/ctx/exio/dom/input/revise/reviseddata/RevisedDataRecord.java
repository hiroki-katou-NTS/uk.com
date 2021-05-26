package nts.uk.ctx.exio.dom.input.revise.reviseddata;

import java.util.List;
import java.util.Optional;

import lombok.Value;
import nts.uk.ctx.exio.dom.input.DataItem;

/**
 * 編集済みの1行分のデータ
 */
@Value
public class RevisedDataRecord {
	
	String categoryId;

	List<DataItem> items;
	
	public Optional<DataItem> getItemByNo(int itemNo) {
		return items.stream()
				.filter(item -> item.getItemNo() == itemNo)
				.findFirst();
	}
}
