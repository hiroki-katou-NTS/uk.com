package nts.uk.ctx.at.request.app.find.application.stamp.dto;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.dom.application.stamp.AppStamp;
@AllArgsConstructor
@NoArgsConstructor
//打刻申請
public class AppStampDto extends ApplicationDto {	
//	時刻
	public List<TimeStampAppDto> listTimeStampApp;	
//	時刻の取消
	public List<DestinationTimeAppDto> listDestinationTimeApp;
//	時間帯
	public List<TimeStampAppOtherDto> listTimeStampAppOther;
//	時間帯の取消
	public List<DestinationTimeZoneAppDto> listDestinationTimeZoneApp;

	
	public static AppStampDto fromDomain(AppStamp appStamp) {
		return new AppStampDto(
				!CollectionUtil.isEmpty(appStamp.getListTimeStampApp()) ? appStamp.getListTimeStampApp().stream()
						.map(x -> TimeStampAppDto.fromDomain(x)).collect(Collectors.toList()) : Collections.emptyList(),

				!CollectionUtil.isEmpty(appStamp.getListDestinationTimeApp()) ? appStamp.getListDestinationTimeApp()
						.stream().map(x -> DestinationTimeAppDto.fromDomain(x)).collect(Collectors.toList())
						: Collections.emptyList(),

				!CollectionUtil.isEmpty(appStamp.getListTimeStampAppOther()) ? appStamp.getListTimeStampAppOther()
						.stream().map(x -> TimeStampAppOtherDto.fromDomain(x)).collect(Collectors.toList())
						: Collections.emptyList(),

				!CollectionUtil.isEmpty(appStamp.getListDestinationTimeZoneApp())
						? appStamp.getListDestinationTimeZoneApp().stream()
								.map(x -> DestinationTimeZoneAppDto.fromDomain(x)).collect(Collectors.toList())
						: Collections.emptyList());
	}
	
	
	public AppStamp toDomain() {
		return new AppStamp(
				!CollectionUtil.isEmpty(listTimeStampApp)
						? listTimeStampApp.stream().map(x -> x.toDomain()).collect(Collectors.toList())
						: Collections.emptyList(),

				!CollectionUtil.isEmpty(listDestinationTimeApp)
						? listDestinationTimeApp.stream().map(x -> x.toDomain()).collect(Collectors.toList())
						: Collections.emptyList(),

				!CollectionUtil.isEmpty(listTimeStampAppOther)
						? listTimeStampAppOther.stream().map(x -> x.toDomain()).collect(Collectors.toList())
						: Collections.emptyList(),

				!CollectionUtil.isEmpty(listDestinationTimeZoneApp)
						? listDestinationTimeZoneApp.stream().map(x -> x.toDomain()).collect(Collectors.toList())
						: Collections.emptyList());
	}

}
