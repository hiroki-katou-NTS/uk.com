package nts.uk.ctx.exio.dom.input;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

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
	
	public DatePeriod getPeriod(int itemNoStartDate, int itemNoEndDate) {
		val start = getItemByNo(itemNoStartDate).get().getDate();
		val end = getItemByNo(itemNoEndDate).get().getDate();
		return new DatePeriod(start, end);
	}
}
