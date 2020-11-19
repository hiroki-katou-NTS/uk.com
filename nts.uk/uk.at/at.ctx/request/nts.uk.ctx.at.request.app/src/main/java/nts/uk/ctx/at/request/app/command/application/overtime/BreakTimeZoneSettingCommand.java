package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.workrecord.dailyrecordprocess.dailycreationwork.BreakTimeZoneSetting;

@AllArgsConstructor
@NoArgsConstructor
public class BreakTimeZoneSettingCommand {
	// 時間帯
	public List<DeductionTimeCommand> timeZones;
	
	public BreakTimeZoneSetting toDomain() {
		return new BreakTimeZoneSetting(CollectionUtil.isEmpty(timeZones) ? 
				Collections.emptyList() : 
				timeZones.stream()
						 .map(x -> x.toDomain())
						 .collect(Collectors.toList()));
	}
}
