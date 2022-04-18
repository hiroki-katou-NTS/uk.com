package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.application;

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
public class TripAppTimeZoneImport {

	// 開始時刻
	private Optional<TimeWithDayAttr> startTime;

	// 終了時刻
	private Optional<TimeWithDayAttr> endTime;
}
