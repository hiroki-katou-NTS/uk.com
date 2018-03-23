package nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;

/**
 * 介護休暇基本情報
 * @author xuan vinh
 *
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NursingCareLeaveRemainingInfo extends AggregateRoot{
	private String sId;
	private LeaveType leaveType;
	private UpperLimitSetting upperlimitSetting;
	private boolean useClassification;
	private Optional<Integer> maxDayForThisFiscalYear;
	private Optional<Integer> maxDayForNextFiscalYear;
}
