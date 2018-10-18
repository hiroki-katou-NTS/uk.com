package nts.uk.ctx.at.record.app.command.dailyperform.audittrail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.security.audittrail.correction.content.CorrectionAttr;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;

@Value
public class DailyCorrectionLogParameter implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private final List<DailyCorrectionTarget> targets;
	
	private Map<Integer, DPAttendanceItemRC> lstAttendanceItem;

	@Value
	public static class DailyCorrectionTarget implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;

		private final String employeeId;
		private final GeneralDate date;
		private final List<DailyCorrectedItem> correctedItems = new ArrayList<>();
		
		public DailyCorrectionTarget(String employeeId, GeneralDate date){
			this.employeeId = employeeId;
			this.date = date;
		}
	}

	@Value
	public static class DailyCorrectedItem implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;

		private final String itemName;
		private final Integer itemNo;
		private final String before;
		private final String after;
		private final Integer valueType; 
		private final CorrectionAttr attr; 

		public ItemInfo toItemInfo() {
			return ItemInfo.create(String.valueOf(this.itemNo), itemName, DataValueAttribute.of(valueType),
					before == null ? null : valueTimeMoney(valueType, before),
					after == null ? null : valueTimeMoney(valueType, after));
		}
		
		private Object valueTimeMoney(int valueType, String value) {
			if (valueType == DataValueAttribute.TIME.value || valueType == DataValueAttribute.CLOCK.value) {
				return Integer.parseInt(value);
			} else if (valueType == DataValueAttribute.MONEY.value) {
				return Double.parseDouble(value);
			} else {
				return value;
			}
		}
		
	}

}
