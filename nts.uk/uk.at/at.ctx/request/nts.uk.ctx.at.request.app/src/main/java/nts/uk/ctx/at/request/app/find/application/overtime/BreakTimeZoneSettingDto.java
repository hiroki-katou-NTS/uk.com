package nts.uk.ctx.at.request.app.find.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.workrecord.dailyrecordprocess.dailycreationwork.BreakTimeZoneSetting;

@AllArgsConstructor
@NoArgsConstructor
public class BreakTimeZoneSettingDto {
	// 時間帯
	public List<DeductionTimeDto> timeZones = Collections.emptyList();
	
	public static BreakTimeZoneSettingDto fromDomain(BreakTimeZoneSetting breakTimeZoneSetting) {
		if (breakTimeZoneSetting == null) return null;
		
		return new BreakTimeZoneSettingDto(
				breakTimeZoneSetting.getTimeZones()
					.stream()
					.map(x -> DeductionTimeDto.fromDomain(x))
					.collect(Collectors.toList()));
	}
}
