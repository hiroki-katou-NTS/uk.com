package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule.log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
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

		public ItemInfo toItemInfo() {
			return ItemInfo.createToView(IdentifierUtil.randomUniqueId(), this.itemName,
					DataValueAttribute.of(valueType).format(
							this.before != null ? valueString(valueType, this.before) : null),
					DataValueAttribute.of(valueType).format(
							this.after != null ? valueString(valueType, this.after) : null));
		}
		
		private Object valueString(int valueType, String value) {
			if (valueType == DataValueAttribute.TIME.value) {
				return Integer.parseInt(value);
			} else if (valueType == DataValueAttribute.MONEY.value) {
				return Double.parseDouble(value);
			} else {
				return false;
			}
		}
		
	}

}
