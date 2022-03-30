package nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;


/**
 * 消化後付与残数データ
 * @author hayata_maekawa
 *
 */

@Getter
@AllArgsConstructor
public class GrantRemainingDataAfterDigestion {

	/** 付与残数データ*/
	private LeaveGrantRemainingData grantRemainingData;
	
	/** 消化できなかった使用数*/
	private LeaveUsedNumber unUsedNumber;
	
}
