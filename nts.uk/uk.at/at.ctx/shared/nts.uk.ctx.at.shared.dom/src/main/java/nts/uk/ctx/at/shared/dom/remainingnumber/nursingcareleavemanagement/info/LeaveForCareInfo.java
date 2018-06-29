package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.Optional;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic.MaxDayForFiscalYear;

/**
 * 介護休暇基本情報
 * 
 * @author xuan vinh
 *
 */
public class LeaveForCareInfo extends NursingCareLeaveRemainingInfo {

	public LeaveForCareInfo() {
	}

	public LeaveForCareInfo(String sId, LeaveType leaveType, boolean useClassification,
			UpperLimitSetting upperlimitSetting, Optional<MaxDayForFiscalYear> maxDayForThisFiscalYear,
			Optional<MaxDayForFiscalYear> maxDayForNextFiscalYear) {
		super(sId, leaveType, useClassification, upperlimitSetting, maxDayForThisFiscalYear, maxDayForNextFiscalYear);
	}

	public static LeaveForCareInfo createCareLeaveInfo(String sId, int useClassification, int upperlimitSetting,
			Double maxDayForThisFiscalYear, Double maxDayForNextFiscalYear) {
		return new LeaveForCareInfo(sId, EnumAdaptor.valueOf(1, LeaveType.class), useClassification == 1,
				EnumAdaptor.valueOf(upperlimitSetting, UpperLimitSetting.class),
				maxDayForThisFiscalYear != null ? Optional.of(new MaxDayForFiscalYear(maxDayForThisFiscalYear))
						: Optional.empty(),
				maxDayForNextFiscalYear != null ? Optional.of(new MaxDayForFiscalYear(maxDayForNextFiscalYear))
						: Optional.empty());
	}

}
