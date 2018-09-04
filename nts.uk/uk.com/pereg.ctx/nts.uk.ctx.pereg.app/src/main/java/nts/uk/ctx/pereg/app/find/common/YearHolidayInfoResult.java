package nts.uk.ctx.pereg.app.find.common;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class YearHolidayInfoResult {
	
	// 次回年休付与日
	private GeneralDate nextGrantDate;

	// 次回年休付与日数
	private Double nextGrantDay;

	// 次回時間年休付与上限
	private Optional<Integer> nextMaxTime;
}
