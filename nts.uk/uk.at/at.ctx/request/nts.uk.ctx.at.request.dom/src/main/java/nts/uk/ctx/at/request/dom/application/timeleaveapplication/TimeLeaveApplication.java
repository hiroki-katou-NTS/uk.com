package nts.uk.ctx.at.request.dom.application.timeleaveapplication;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;

import java.util.List;

/**
 * AggregateRoot : 時間休暇申請
 */
@Getter
@Setter
@NoArgsConstructor
public class TimeLeaveApplication extends Application {

	/**
	 * 詳細
	 */
	private List<TimeLeaveApplicationDetail> leaveApplicationDetails;

	public TimeLeaveApplication(Application application, List<TimeLeaveApplicationDetail> leaveApplicationDetails) {
		super(application);
		this.leaveApplicationDetails = leaveApplicationDetails;
	}

	public TimeLeaveApplication(Application application) {
		super(application);
	}

}
