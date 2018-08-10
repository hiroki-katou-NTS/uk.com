package nts.uk.shr.pereg.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ItemLog {
	private String itemId;
	private String itemCode;
	private String itemName;
	private Integer type;	
	private String valueBefore;
	private String contentBefore;
	private String valueAfter;
	private String contentAfter;
	
	public static ItemLog createtoItemLog(ItemValue item) {
		String contentOld = item.dViewValue() == null ? item.stringValue(): item.dViewValue();
		String contentNew = (item.viewValue() == null || item.viewValue() == "") ? item.stringValue(): item.viewValue();
		ItemLog itemLog = new ItemLog();
		
		itemLog.setItemId(item.definitionId());
		itemLog.setItemCode(item.itemCode());
		itemLog.setItemName(item.itemName());
		itemLog.setType(new Integer(item.logType()));
		itemLog.setValueBefore(item.dValue());
		itemLog.setValueAfter(item.stringValue());
		itemLog.setContentBefore(formatContent(item.logType(), item.type(), contentOld, item.dValue()));
		itemLog.setContentAfter(formatContent(item.logType(), item.type(), contentNew, item.stringValue()));
		
		return itemLog;
		
	}
	
	public static List<ItemLog> convertItemLog(List<ItemValue> items, List<ItemValue> itemInvisible, String itemCode, DatePeriodSet datePeriod){
		List<ItemLog> itemLogs = new ArrayList<>();
		
		items.stream().forEach( c ->{
			Object oldValue = formatValue(c, c.dValue(), true);
			Object newValue = formatValue(c, c.stringValue(), false);
			
			if(oldValue == null && newValue != null) {
				itemLogs.add(createtoItemLog(c));
			}
			
			if(c.itemCode().equals(itemCode)) {
				itemLogs.add(createtoItemLog(c));
			}
			
			if(datePeriod != null) {
				if (c.itemCode().equals(datePeriod.getStartCode())) {
					itemLogs.add(createtoItemLog(c));
				}
			}
			
			if(oldValue != null) {
				if (!oldValue.equals(newValue)) {
					itemLogs.add(createtoItemLog(c));
				}
			}
			
		});		
		
		itemInvisible.stream().forEach(c -> {
			if (c.itemCode().equals(itemCode)) {
				itemLogs.add(createtoItemLog(c));
			}

		});
		
		return itemLogs;
	}
	
	private static Object formatValue(ItemValue item, Object value,  boolean isOld) {
		
		if (value == null) return null;
		
		switch(item.logType()) {
		case 1:
		case 3:
		case 4:
		case 5:
		case 6: 
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
			return value.toString();
		case 2:	
			return new BigDecimal(value.toString());
		default:
			return value.toString();
		}
		
	}
	
	private static String formatContent(int logType, int type, String viewContent, String value) {
		
		if (viewContent == null || value == null) {
			return null;
		}
		
		switch(logType) {
		case 1:
		case 2: 
		case 3:
		case 6: 
		case 7:
		case 8:
			return viewContent;
		case 4:
			return formatMinutesToTime(Integer.valueOf(value));
		case 5:
			return new TimeWithDayAttr(Integer.valueOf(value)).getFullText();
		default:
			throw new RuntimeException("invalid attribute: " + value);
		}
		
	}
	
	private static String formatMinutesToTime(int valueAsMinutes) {
		
		boolean isMinus = valueAsMinutes < 0;
		int value = Math.abs(valueAsMinutes);
		int minute = value % 60;
		int hour = value / 60;
		
		return String.format("%s%d:%02d", (isMinus ? "-" : ""), hour, minute);
	}
}
