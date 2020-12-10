package nts.uk.ctx.at.shared.dom.application.stamp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
//時間帯申請の反映先情報
public class DestinationTimeZoneAppShare {
//	分類
	private TimeZoneStampClassificationShare timeZoneStampClassification;
//	打刻枠No
	private Integer engraveFrameNo;

	public DestinationTimeZoneAppShare(int timeZoneStampClassification, Integer engraveFrameNo) {
		this.timeZoneStampClassification = TimeZoneStampClassificationShare.valueOf(timeZoneStampClassification);
		this.engraveFrameNo = engraveFrameNo;

	}
}
