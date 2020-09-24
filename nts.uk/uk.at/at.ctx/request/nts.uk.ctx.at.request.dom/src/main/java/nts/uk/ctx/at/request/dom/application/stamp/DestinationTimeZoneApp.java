package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
//時間帯申請の反映先情報
public class DestinationTimeZoneApp {
//	分類
	private TimeZoneStampClassification timeZoneStampClassification;
//	打刻枠No
	private Integer engraveFrameNo;
}
