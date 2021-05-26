package nts.uk.ctx.at.shared.dom.scherec.application.timeleaveapplication;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationShare;

/**
 * @author thanh_nx
 *
 *         時間休暇申請(反映用)
 */
@Getter
@Setter
@NoArgsConstructor
public class TimeLeaveApplicationShare extends ApplicationShare {

	/**
	 * 詳細
	 */
	private List<TimeLeaveApplicationDetailShare> leaveApplicationDetails;

	public TimeLeaveApplicationShare(ApplicationShare application,
			List<TimeLeaveApplicationDetailShare> leaveApplicationDetails) {
		super(application);
		this.leaveApplicationDetails = leaveApplicationDetails;
	}

	public TimeLeaveApplicationShare(ApplicationShare application) {
		super(application);
	}

}
