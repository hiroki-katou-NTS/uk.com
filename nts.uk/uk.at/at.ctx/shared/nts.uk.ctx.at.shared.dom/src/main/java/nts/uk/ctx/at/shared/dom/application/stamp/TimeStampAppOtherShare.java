package nts.uk.ctx.at.shared.dom.application.stamp;
//打刻申請時間帯

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.time.TimeZone;
@AllArgsConstructor
@Getter
//打刻申請時間帯
public class TimeStampAppOtherShare {
//	反映先
	private DestinationTimeZoneAppShare destinationTimeZoneApp;
//	時間帯
	private TimeZone timeZone;
}
