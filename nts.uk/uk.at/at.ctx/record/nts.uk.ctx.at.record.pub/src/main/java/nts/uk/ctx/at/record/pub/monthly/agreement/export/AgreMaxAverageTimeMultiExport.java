package nts.uk.ctx.at.record.pub.monthly.agreement.export;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;

@Data
@Builder
@AllArgsConstructor
public class AgreMaxAverageTimeMultiExport {

	/** 上限時間 */
	private int errorTime;
	private int alarmTime;
	
	/** 平均時間 */
	private List<AgreMaxAverageTimeExport> averageTimes;

	public static AgreMaxAverageTimeMultiExport copy(AgreMaxAverageTimeMulti domain) {
		
		return AgreMaxAverageTimeMultiExport.builder()
				.errorTime(domain.getMaxTime().getError().valueAsMinutes())
				.alarmTime(domain.getMaxTime().getAlarm().valueAsMinutes())
				.averageTimes(domain.getAverageTimes().stream().map(c -> AgreMaxAverageTimeExport.copy(c)).collect(Collectors.toList()))
				.build();
	}
}
