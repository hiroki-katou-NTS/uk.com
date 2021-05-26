package nts.uk.ctx.exio.dom.input;

import static nts.uk.ctx.exio.dom.input.DataItem.Type.*;

import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 1項目分のデータ
 */
@Value
public class DataItem {

	/** 項目No */
	int itemNo;
	
	/** 型 */
	Type type;
	
	/** 値 */
	Object value;
	
	public static DataItem of(int itemNo, String value) {
		return new DataItem(itemNo, STRING, value);
	}
	
	public static DataItem of(int itemNo, int value) {
		return new DataItem(itemNo, INT, value);
	}
	
	public static DataItem of(int itemNo, double value) {
		return new DataItem(itemNo, REAL, value);
	}
	
	public static DataItem of(int itemNo, GeneralDate value) {
		return new DataItem(itemNo, DATE, value);
	}
	
	public String getString() {
		checkType(STRING);
		return value != null ? (String) value : null;
	}
	
	public Integer getInt() {
		checkType(INT);
		return value != null ? (int) value : null;
	}
	
	public Double getReal() {
		checkType(REAL);
		return value != null ? (double) value : null;
	}
	
	public GeneralDate getDate() {
		checkType(DATE);
		return value != null ? (GeneralDate) value : null;
	}
	
	private void checkType(Type... validTypes) {
		for (Type validType : validTypes) {
			if (type == validType) {
				return;
			}
		}
		
		throw new RuntimeException("typeが合致しないため実行できません：type: " + type);
	}

	public enum Type {
		STRING,
		INT,
		REAL,
		DATE,
	}
}
