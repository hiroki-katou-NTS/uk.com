package nts.uk.ctx.exio.dom.input;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;

/**
 * DataItemのリストにやらせたいことが色々あるのでクラス化したやつ
 */
@SuppressWarnings("serial")
public class DataItemList extends ArrayList<DataItem> {
	
	public DataItemList() {
		super();
	}
	
	public DataItemList(Collection<DataItem> items) {
		super(items);
	}
	
	public Optional<DataItem> getItemByNo(int itemNo) {
		return stream()
				.filter(item -> item.getItemNo() == itemNo)
				.findFirst();
	}
	
	public DataItemList add(int itemNo, String value) {
		return addItem(DataItem.of(itemNo, value));
	}
	
	public DataItemList add(int itemNo, long value) {
		return addItem(DataItem.of(itemNo, value));
	}
	
	public DataItemList add(int itemNo, BigDecimal value) {
		return addItem(DataItem.of(itemNo, value));
	}
	
	public DataItemList add(int itemNo, GeneralDate value) {
		return addItem(DataItem.of(itemNo, value));
	}
	
	private DataItemList addItem(DataItem item) {
		this.add(item);
		return this;
	}
	
	/**
	 * 自身の内容をtargetItemNosに指定されたものとそうでないもの仕分ける
	 * @param targetList
	 * @param notTargetList
	 * @param targetItemNos
	 */
	public void separate(
			DataItemList targetList,
			DataItemList notTargetList,
			Integer... targetItemNos) {
		
		val targetItemNoSet = new HashSet<>(Arrays.asList(targetItemNos));
		
		for (DataItem item : this) {
			if (targetItemNoSet.contains(item.getItemNo())) {
				targetList.add(item);
			} else {
				notTargetList.add(item);
			}
		}
	}
}
