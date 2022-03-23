package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消化後休暇数情報
 * @author hayata_maekawa
 *
 */
@AllArgsConstructor
@Getter
public class LeaveNumberInfoAfterDigestion {

	/** 休暇数情報 */
	private LeaveNumberInfo leaveNumberInfo;
	
	/** 消化できなかった使用数*/
	private LeaveUsedNumber unUsedNumber;
	
}
