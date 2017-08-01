package nts.uk.ctx.bs.person.dom.person.info.singleitem;

import java.math.BigDecimal;

import nts.uk.ctx.bs.person.dom.person.info.dateitem.DateItem;
import nts.uk.ctx.bs.person.dom.person.info.numericitem.NumericItem;
import nts.uk.ctx.bs.person.dom.person.info.stringitem.StringItem;
import nts.uk.ctx.bs.person.dom.person.info.timeitem.TimeItem;
import nts.uk.ctx.bs.person.dom.person.info.timepointitem.TimePointItem;

public class DataTypeObject {

	protected DataTypeState dataTypeState;

	public static DataTypeObject createTimeItem(long max, long min) {
		return new TimeItem(max, min);
	}

	public static DataTypeObject createStringItem(int stringItemLeng, int stringItemType, int stringItemDataType) {
		return new StringItem(stringItemLeng, stringItemType, stringItemDataType);
	}

	public static DataTypeObject createTimePointItem(int dayTypeMin, long timePointMin, int dayTypeMax,
			long timePointMax) {
		return new TimePointItem(dayTypeMin, timePointMin, dayTypeMax, timePointMax);
	}

	public static DataTypeObject createTimeDateItem(int dateItemType) {
		return new DateItem(dateItemType);
	}

	public static DataTypeObject createNumericItem(int numericItemMinus, int numericItemAmount, int integerPart,
			int decimalPart, BigDecimal numericItemMin, BigDecimal numericItemMax) {
		return new NumericItem(numericItemMinus, numericItemAmount, integerPart, decimalPart, numericItemMin,
				numericItemMax);
	}

}
