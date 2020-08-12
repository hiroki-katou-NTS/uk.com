package nts.uk.ctx.at.request.dom.application.stamp;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.Application;
/**
 * Refactor4
 * @author hoangnd
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.打刻申請
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
//打刻申請
public class AppStamp extends Application {
//	時刻
	private List<TimeStampApp> listTimeStampApp;
	
//	時刻の取消
	private List<DestinationTimeApp> listDestinationTimeApp;
//	時間帯
	private List<TimeStampAppOther> listTimeStampAppOther;
//	時間帯の取消
	private List<DestinationTimeZoneApp> listDestinationTimeZoneApp;
//	申請内容
	public String getAppContent() {
		return null;
	}

	public AppStamp(Application application) {
		super(application);
	}
	
}
