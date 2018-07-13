package nts.uk.ctx.at.record.app.command.dailyperform.audittrail;

import java.io.Serializable;
import java.util.List;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
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
		private final List<DailyCorrectedItem> correctedItems;
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

		public ItemInfo toItemInfo() {
			return ItemInfo.create(IdentifierUtil.randomUniqueId(), this.itemName, DataValueAttribute.COUNT,
					this.before, this.after);
		}
	}

}
