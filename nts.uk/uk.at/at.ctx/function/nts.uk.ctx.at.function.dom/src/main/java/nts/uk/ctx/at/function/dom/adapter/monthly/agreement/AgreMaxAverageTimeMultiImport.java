package nts.uk.ctx.at.function.dom.adapter.monthly.agreement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AgreMaxAverageTimeMultiImport {
	/** 上限時間 */
	private int errorTime;
	private int alarmTime;
	
	/** 平均時間 */
	private List<AgreMaxAverageTimeImport> averageTimes;
}
