package nts.uk.ctx.at.shared.dom.application.stamp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.application.common.ApplicationShare;

/**
 * @author thanh_nx
 * 
 *         打刻申請
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
//打刻申請
public class AppStampShare extends ApplicationShare {
//	時刻
	private List<TimeStampAppShare> listTimeStampApp;

//	時刻の取消
	private List<DestinationTimeAppShare> listDestinationTimeApp;
//	時間帯
	private List<TimeStampAppOtherShare> listTimeStampAppOther;
//	時間帯の取消
	private List<DestinationTimeZoneAppShare> listDestinationTimeZoneApp;

//	申請内容
	public String getAppContent() {
		return null;
	}

	public AppStampShare(ApplicationShare application) {
		super(application);
	}

	public AppStampShare(List<TimeStampAppShare> listTimeStampApp, List<DestinationTimeAppShare> listDestinationTimeApp,
			List<TimeStampAppOtherShare> listTimeStampAppOther,
			List<DestinationTimeZoneAppShare> listDestinationTimeZoneApp, ApplicationShare application) {
		super(application);
		this.listTimeStampApp = listTimeStampApp;
		this.listDestinationTimeApp = listDestinationTimeApp;
		this.listTimeStampAppOther = listTimeStampAppOther;
		this.listDestinationTimeZoneApp = listDestinationTimeZoneApp;
	}

}
