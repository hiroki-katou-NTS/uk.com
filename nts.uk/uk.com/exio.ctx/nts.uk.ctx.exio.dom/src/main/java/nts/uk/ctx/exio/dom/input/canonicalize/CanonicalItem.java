package nts.uk.ctx.exio.dom.input.canonicalize;

import java.math.BigDecimal;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.exio.dom.input.DataItem;

/**
 * 正準化済みの項目
 */
@Value
public class CanonicalItem {

	/** 受入項目NO */
	int itemNo;
	
	/** 値 */
	Object value;
	
	public static CanonicalItem of(DataItem dataItem) {
		return new CanonicalItem(dataItem.getItemNo(), dataItem.getValue());
	}

	public String getString() {
		return value != null ? (String) value : null;
	}
	
	public Long getInt() {
		return value != null ? (long) value : null;
	}
	
	public BigDecimal getReal() {
		return value != null ? (BigDecimal) value : null;
	}
	
	public GeneralDate getDate() {
		return value != null ? (GeneralDate) value : null;
	}
	
	public GeneralDateTime getDateTime() {
		return value != null ? (GeneralDateTime) value : null;
	}
	
	public boolean isNull() {
		return value == null;
	}

	public boolean isEmpty() {
		return isNull() || getString().isEmpty();
	}
}
