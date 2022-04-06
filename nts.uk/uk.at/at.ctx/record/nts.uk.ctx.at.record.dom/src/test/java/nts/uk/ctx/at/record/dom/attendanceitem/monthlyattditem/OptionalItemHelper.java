package nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem;

import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemUsageAtr;

public class OptionalItemHelper {
	
	public static OptionalItem createOptionalItemByNoAndUseAtr(int optionalItemNo, OptionalItemUsageAtr usageAtr) {
		return new OptionalItem(new OptionalItemNo(optionalItemNo), usageAtr);
	}

}
