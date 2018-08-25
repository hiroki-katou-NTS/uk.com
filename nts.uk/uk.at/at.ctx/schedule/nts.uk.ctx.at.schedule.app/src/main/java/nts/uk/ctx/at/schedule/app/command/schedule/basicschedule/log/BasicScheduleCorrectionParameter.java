package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;

@Value
public class BasicScheduleCorrectionParameter implements Serializable{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private final List<ScheduleCorrectionTarget> targets;

	@Value
	public static class ScheduleCorrectionTarget implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;

		private final String employeeId;
		private final GeneralDate date;
		private final List<ScheduleCorrectedItem> correctedItems = new ArrayList<>();

		public ScheduleCorrectionTarget(String employeeId, GeneralDate date){
			this.employeeId = employeeId;
			this.date = date;
		}
	}
	
	@Value
	public static class ScheduleCorrectedItem implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;

		private final String itemName;
		private final Integer itemNo;
		private final String before;
		private final String after;
		private final Integer valueType;
		private final String remark;
		private final CorrectionAttr attr; 

		public ItemInfo toItemInfo(String viewValueBef, String viewValueAft) {
			return ItemInfo.createWithViewValue(String.valueOf(this.itemNo), this.itemName,
					DataValueAttribute.of(valueType), valueTimeMoney(valueType, before),
					valueTimeMoney(valueType, after), valueTimeMoney(valueType, viewValueBef),
					valueTimeMoney(valueType, viewValueAft));
		}
		
		private Object valueTimeMoney(int valueType, String value) {
			if(value == null){
				return null;
			} else if (valueType == DataValueAttribute.TIME.value || valueType == DataValueAttribute.CLOCK.value) {
				return Integer.parseInt(value);
			} else if (valueType == DataValueAttribute.MONEY.value) {
				return Double.parseDouble(value);
			} else {
				return value;
			}
		}
	}

}
