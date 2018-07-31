package nts.uk.shr.com.security.audittrail.correction.processor.pereg;

import java.io.Serializable;
import java.util.List;
import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.security.audittrail.correction.content.DataValueAttribute;
import nts.uk.shr.com.security.audittrail.correction.content.ItemInfo;

@Value
public class PeregCorrectionLogParameter implements Serializable{
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private final List<PeregCorrectionTarget> targets;
	
	@Value
	public static class PeregCorrectionTarget implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;
		
		private final String employeeId;
		private final GeneralDate date;
		private final List<PeregCorrectedItem> correctedItems;
	}
	
	@Value
	public static class PeregCorrectedItem implements Serializable {

		/** serialVersionUID */
		private static final long serialVersionUID = 1L;
		
		private final String itemName;
		private final int itemNo;
		private final int before;
		private final int after;
		
		public ItemInfo toItemInfo() {
			return ItemInfo.create(
					IdentifierUtil.randomUniqueId(),
					this.itemName,
					DataValueAttribute.COUNT,
					this.before,
					this.after);
		}
	}

}
