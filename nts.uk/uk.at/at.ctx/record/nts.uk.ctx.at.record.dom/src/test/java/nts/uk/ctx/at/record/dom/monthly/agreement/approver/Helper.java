package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import nts.arc.time.GeneralDate;

/**
 * @author khai.dh
 */
public class Helper {
	public static GeneralDate createDate(String strDate){
		return GeneralDate.fromString(strDate, "yyyy/MM/dd");
	}
}
