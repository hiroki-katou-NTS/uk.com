package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.getfirstreflect;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetFirstReflectOutput {
	// 反映範囲
	private Optional<StampReflectRangeOutput> stampReflectRangeOutput = Optional.empty();

	// 年月日
	private Optional<GeneralDate> ymd = Optional.empty();
}
