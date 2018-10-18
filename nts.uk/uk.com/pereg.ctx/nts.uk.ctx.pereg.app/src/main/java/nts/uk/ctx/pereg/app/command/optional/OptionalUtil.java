package nts.uk.ctx.pereg.app.command.optional;

import java.math.BigDecimal;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.personinfoctgdata.item.DataState;
import nts.uk.shr.pereg.app.ItemValue;

public class OptionalUtil {
	
	/** The Constant DATE_FORMAT. */
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	/**
	 * Covert value to string
	 * @param obj
	 * @return
	 */
	public static String convertToString(Object obj){
		return obj == null ? "" : obj.toString();
	}
	/**
	 * Convert value to int
	 * @param obj
	 * @return
	 */
	public static int convertToInt(Object obj){
		try {
			return Integer.parseInt(convertToString(obj));
		} catch (Exception e) {
			return 0;
		}
	}
	/**
	 * Convert value to Date
	 * @param obj
	 * @return
	 */
	public static GeneralDate convertToDate(Object obj){
		try {
			return GeneralDate.fromString(convertToString(obj), DATE_FORMAT);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Convert value to decimal
	 * @param obj
	 * @return
	 */
	public static BigDecimal convertToDecimal(Object obj){
		try {
			return new BigDecimal(convertToString(obj));
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Create data state from item type
	 * @param item
	 * @return
	 */
	public static DataState createDataState(ItemValue item) {
		if(item.value() == null) return null;
		switch (item.saveDataType()) {
		case STRING:
			return DataState.createFromStringValue(convertToString(item.value()));
		case NUMERIC:
			return DataState.createFromNumberValue(convertToDecimal(item.value()));
		case DATE:
			return DataState.createFromDateValue(convertToDate(item.value()));
		default:
			return null;
		}
	}
}
