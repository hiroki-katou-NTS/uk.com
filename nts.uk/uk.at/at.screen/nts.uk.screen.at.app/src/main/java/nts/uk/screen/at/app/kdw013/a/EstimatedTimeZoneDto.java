package nts.uk.screen.at.app.kdw013.a;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.BreakTimeSheetDto;
import nts.uk.ctx.at.record.app.find.dailyperform.dto.TimeSpanForCalcDto;
import nts.uk.screen.at.app.kdw013.query.EstimatedTimeZone;
import nts.uk.screen.at.app.kmk.kmk017.worktimeworkplace.TimezoneUseDto;

/**
 * 
 * @author sonnlb
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EstimatedTimeZoneDto {
	// 年月日
	private GeneralDate ymd;

	// 開始時刻
	private int startTime;

	// 終了時刻
	private int endTime;

	// 休憩時間帯

	private List<BreakTimeSheetDto> breakTimeSheets;

	// List<計算用時間帯>

	private List<TimezoneUseDto> timezones;

	// List<時間帯>

	private List<TimeSpanForCalcDto> itemSpans;

	public static EstimatedTimeZoneDto fromDomain(EstimatedTimeZone et) {

		return new EstimatedTimeZoneDto(
						et.getYmd(), 
						et.getStartTime().v(), 
						et.getEndTime().v(),
						et.getBreakTimeSheets().stream().map(bt -> new BreakTimeSheetDto(bt.getStartTime().v(), bt.getEndTime().v(),bt.getBreakTime().v(), bt.getBreakFrameNo().v())).collect(Collectors.toList()),
						et.getTimezones().stream().map(tz -> TimezoneUseDto.fromDomain(tz)).collect(Collectors.toList()),
						et.getItemSpans().stream().map(ip-> new TimeSpanForCalcDto(ip.getStart().v(), ip.getEnd().v())).collect(Collectors.toList())
						);
	}
}
