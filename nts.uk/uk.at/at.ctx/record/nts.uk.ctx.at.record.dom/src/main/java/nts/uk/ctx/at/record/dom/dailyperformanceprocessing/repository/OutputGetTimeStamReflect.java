package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;

@Getter
@Setter
@NoArgsConstructor
public class OutputGetTimeStamReflect {

	private StampReflectRangeOutput stampReflectRangeOutput;
	
	private List<ErrorMessageInfo> error = new ArrayList<>();

	public OutputGetTimeStamReflect(StampReflectRangeOutput stampReflectRangeOutput, List<ErrorMessageInfo> error) {
		super();
		this.stampReflectRangeOutput = stampReflectRangeOutput;
		this.error = error;
	}
	
	
}
