package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareData;

@Getter
@AllArgsConstructor
public class CareLeaveDataInfo {
	private CareLeaveRemainingInfo careInfo;
	private LeaveForCareData careData;
	private ChildCareLeaveRemainingInfo childCareLeaveRemainingInfo;
	private ChildCareLeaveRemainingData childCareLeaveRemainingData;
}
