package nts.uk.ctx.at.record.app.command.dailyperform.audittrail;

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
public class DailyCorrectionLogParameter implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	private final List<DailyCorrectionTarget> targets;

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
			return ItemInfo.createToView(IdentifierUtil.randomUniqueId(), this.itemName,
					DataValueAttribute.of(valueType).format(
							valueTimeMoney(valueType, this.before)),
					DataValueAttribute.of(valueType).format(
							valueTimeMoney(valueType, this.after)));
		}
		
		private Object valueTimeMoney(int valueType, String value) {
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
