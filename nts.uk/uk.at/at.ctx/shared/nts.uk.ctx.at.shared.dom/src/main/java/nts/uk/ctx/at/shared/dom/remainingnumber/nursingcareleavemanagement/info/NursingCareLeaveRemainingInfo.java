package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.basic.MaxDayForFiscalYear;

/**
 * 子の看護・介護休暇基本情報
 * 
 * @author xuan vinh
 *
 */

@Getter
@Setter
@NoArgsConstructor
public abstract class NursingCareLeaveRemainingInfo extends AggregateRoot {

	// 社員ID
	private String sId;

	private LeaveType leaveType;

	// 使用区分
	private boolean useClassification;

	// 上限設定
	private UpperLimitSetting upperlimitSetting;

	// 本年度上限日数
	private Optional<MaxDayForFiscalYear> maxDayForThisFiscalYear;

	// 次年度上限日数
	private Optional<MaxDayForFiscalYear> maxDayForNextFiscalYear;
	
	public NursingCareLeaveRemainingInfo(String sId, LeaveType leaveType, boolean useClassification,
			UpperLimitSetting upperlimitSetting, Optional<MaxDayForFiscalYear> maxDayForThisFiscalYear,
			Optional<MaxDayForFiscalYear> maxDayForNextFiscalYear) {
		super();
		this.sId = sId;
		this.leaveType = leaveType;
		this.useClassification = useClassification;
		this.upperlimitSetting = upperlimitSetting;
		this.maxDayForThisFiscalYear = maxDayForThisFiscalYear;
		this.maxDayForNextFiscalYear = maxDayForNextFiscalYear;
	}

	public NursingCareLeaveRemainingInfo(String sId, Optional<MaxDayForFiscalYear> maxDayForThisFiscalYear,
			Optional<MaxDayForFiscalYear> maxDayForNextFiscalYear) {
		super();
		this.sId = sId;
		this.leaveType = EnumAdaptor.valueOf(2, LeaveType.class);
		this.useClassification = false;
		this.upperlimitSetting = UpperLimitSetting.FAMILY_INFO;
		this.maxDayForThisFiscalYear = maxDayForThisFiscalYear;
		this.maxDayForNextFiscalYear = maxDayForNextFiscalYear;
	}

}
