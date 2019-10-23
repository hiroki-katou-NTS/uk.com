package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic.MaxDayForFiscalYear;

/**
 * 子の看護休暇基本情報
 * @author danpv
 *
 */
public class ChildCareLeaveRemainingInfo extends NursingCareLeaveRemainingInfo{
	
	public ChildCareLeaveRemainingInfo() {
		
	}
	
	public ChildCareLeaveRemainingInfo(String sId, LeaveType leaveType, boolean useClassification,
			UpperLimitSetting upperlimitSetting, Optional<MaxDayForFiscalYear> maxDayForThisFiscalYear,
			Optional<MaxDayForFiscalYear> maxDayForNextFiscalYear) {
		super(sId, leaveType, useClassification, upperlimitSetting, maxDayForThisFiscalYear, maxDayForNextFiscalYear);
	}
	
	public static ChildCareLeaveRemainingInfo createChildCareLeaveInfo(String sId, int useClassification,
			int upperlimitSetting, Double maxDayForThisFiscalYear, Double maxDayForNextFiscalYear) {
		return new ChildCareLeaveRemainingInfo(sId, EnumAdaptor.valueOf(2, LeaveType.class), useClassification == 1,
				EnumAdaptor.valueOf(upperlimitSetting, UpperLimitSetting.class),
				maxDayForThisFiscalYear != null ? Optional.of(new MaxDayForFiscalYear(maxDayForThisFiscalYear))
						: Optional.empty(),
				maxDayForNextFiscalYear != null ? Optional.of(new MaxDayForFiscalYear(maxDayForNextFiscalYear))
						: Optional.empty());
	}

}
