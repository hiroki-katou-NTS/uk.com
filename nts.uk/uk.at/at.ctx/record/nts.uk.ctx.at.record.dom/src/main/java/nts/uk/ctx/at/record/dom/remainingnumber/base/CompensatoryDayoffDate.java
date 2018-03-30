package nts.uk.ctx.at.record.dom.remainingnumber.base;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

/**
 * 発生消化年月日
 * @author HopNT
 *
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CompensatoryDayoffDate {
	
	// 日付不明
	private boolean unknownDate;
	
	// 年月日
	private Optional<GeneralDate> dayoffDate;
}
