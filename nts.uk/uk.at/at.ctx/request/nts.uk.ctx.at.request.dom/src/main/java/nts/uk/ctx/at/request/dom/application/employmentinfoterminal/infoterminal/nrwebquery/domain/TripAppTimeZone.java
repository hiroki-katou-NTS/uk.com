package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.nrwebquery.domain;

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
public class TripAppTimeZone {

	// 開始時刻
	private Optional<TimeWithDayAttr> startTime;

	// 終了時刻
	private Optional<TimeWithDayAttr> endTime;
}
