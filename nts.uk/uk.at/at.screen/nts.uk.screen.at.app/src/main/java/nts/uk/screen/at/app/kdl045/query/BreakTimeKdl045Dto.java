package nts.uk.screen.at.app.kdl045.query;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.ctx.at.shared.dom.workrule.BreakTimeZone;

/**
 * Temporary : 休憩時間
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class BreakTimeKdl045Dto {
	/**
	 * 休憩時間帯を固定にする
	 */
	private boolean fixBreakTime;
	
	/**
	 * 休憩時間帯
	 */
	private List<TimeSpanForCalcDto> timeZoneList;

	public BreakTimeKdl045Dto(boolean fixBreakTime, List<TimeSpanForCalcDto> timeZoneList) {
		super();
		this.fixBreakTime = fixBreakTime;
		this.timeZoneList = timeZoneList;
	}
	
	public static BreakTimeKdl045Dto convertToBreakTimeZone( BreakTimeZone domain) {
		List<TimeSpanForCalcDto> timeZoneList = domain.getBreakTimes().stream().map(c-> new TimeSpanForCalcDto(c.getStart().v(), c.getEnd().v())).collect(Collectors.toList());
		timeZoneList = timeZoneList.stream().sorted((first,second) -> second.getStart().compareTo(first.getStart())).collect(Collectors.toList());
		return new BreakTimeKdl045Dto(
				domain.isFixed(),
				timeZoneList
				);
	}
}
