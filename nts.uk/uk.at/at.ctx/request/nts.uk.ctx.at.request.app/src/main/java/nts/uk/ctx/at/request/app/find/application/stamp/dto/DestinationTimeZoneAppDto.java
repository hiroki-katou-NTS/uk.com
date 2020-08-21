package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.stamp.DestinationTimeZoneApp;
import nts.uk.ctx.at.request.dom.application.stamp.TimeZoneStampClassification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
//時間帯申請の反映先情報
public class DestinationTimeZoneAppDto {
//	分類
	public Integer timeZoneStampClassification;
//	打刻枠No
	public Integer engraveFrameNo;
	
	public static DestinationTimeZoneAppDto fromDomain(DestinationTimeZoneApp destinationTimeZoneApp) {
		return new DestinationTimeZoneAppDto(
				destinationTimeZoneApp.getTimeZoneStampClassification().value,
				destinationTimeZoneApp.getEngraveFrameNo());
	}
	
	public DestinationTimeZoneApp toDomain() {
		return new DestinationTimeZoneApp(
				EnumAdaptor.valueOf(timeZoneStampClassification, TimeZoneStampClassification.class),
				engraveFrameNo);
	}
	
}
