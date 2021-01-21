package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.stamp.TimeStampAppOther;
import nts.uk.ctx.at.shared.app.find.worktime.predset.dto.TimeZone_NewDto;

@AllArgsConstructor
@NoArgsConstructor
//打刻申請時間帯
public class TimeStampAppOtherDto {
//	反映先
	public DestinationTimeZoneAppDto destinationTimeZoneApp;
//	時間帯
	public TimeZone_NewDto timeZone;
	
	public static TimeStampAppOtherDto fromDomain(TimeStampAppOther timeStampAppOther) {
		return new TimeStampAppOtherDto(
				DestinationTimeZoneAppDto.fromDomain(timeStampAppOther.getDestinationTimeZoneApp()),
				TimeZone_NewDto.fromDomain(timeStampAppOther.getTimeZone()));
	}
	
	public TimeStampAppOther toDomain() {
		return new TimeStampAppOther(
				destinationTimeZoneApp.toDomain(),
				timeZone.toDomain());
	}
}
