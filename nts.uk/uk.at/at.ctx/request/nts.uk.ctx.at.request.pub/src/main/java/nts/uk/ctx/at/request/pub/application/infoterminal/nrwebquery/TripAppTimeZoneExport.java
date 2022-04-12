package nts.uk.ctx.at.request.pub.application.infoterminal.nrwebquery;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * @author thanh_nx
 *
 *         出張申請時間
 */
@AllArgsConstructor
@Getter
public class TripAppTimeZoneExport {

	// 開始時刻
	private Optional<TimeWithDayAttr> startTime;

	// 終了時刻
	private Optional<TimeWithDayAttr> endTime;
}
