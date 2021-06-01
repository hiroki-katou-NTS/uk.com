package nts.uk.ctx.exio.dom.input;

import java.math.BigDecimal;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.exio.dom.input.revise.ItemType;

/**
 * 1項目分のデータ
 */
@Value
public class DataItem {

	/** 受入項目NO */
	int itemNo;
	
	/** 項目型 */
	ItemType type;
	
	/** 値 */
	Object value;
	
	public static DataItem of(int itemNo, String value) {
		return new DataItem(itemNo, ItemType.STRING, value);
	}
	
	public static DataItem of(int itemNo, long value) {
		return new DataItem(itemNo, ItemType.INT, value);
	}
	
	public static DataItem of(int itemNo, BigDecimal value) {
		return new DataItem(itemNo, ItemType.REAL, value);
	}
	
	public static DataItem of(int itemNo, GeneralDate value) {
		return new DataItem(itemNo, ItemType.DATE, value);
	}
	
	public String getString() {
		checkType(ItemType.STRING);
		return value != null ? (String) value : null;
	}
	
	public Long getInt() {
		checkType(ItemType.INT);
		return value != null ? (long) value : null;
	}
	
	public BigDecimal getReal() {
		checkType(ItemType.REAL);
		return value != null ? (BigDecimal) value : null;
	}
	
	public GeneralDate getDate() {
		checkType(ItemType.DATE);
		return value != null ? (GeneralDate) value : null;
	}
	
	private void checkType(ItemType... validTypes) {
		for (ItemType validType : validTypes) {
			if (type == validType) {
				return;
			}
		}
		
		throw new RuntimeException("typeが合致しないため実行できません：type: " + type);
	}
}
