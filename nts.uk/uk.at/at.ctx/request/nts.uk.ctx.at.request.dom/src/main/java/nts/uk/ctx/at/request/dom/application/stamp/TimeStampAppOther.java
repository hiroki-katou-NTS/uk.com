package nts.uk.ctx.at.request.dom.application.stamp;
//打刻申請時間帯

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.time.TimeZone;
@AllArgsConstructor
@Getter
//打刻申請時間帯
public class TimeStampAppOther {
//	反映先
	private DestinationTimeZoneApp destinationTimeZoneApp;
//	時間帯
	private TimeZone timeZone;
}
