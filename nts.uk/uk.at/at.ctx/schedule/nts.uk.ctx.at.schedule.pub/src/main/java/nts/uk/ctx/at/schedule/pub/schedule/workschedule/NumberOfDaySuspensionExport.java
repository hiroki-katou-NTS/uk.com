package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NumberOfDaySuspensionExport {
	// 振休振出日数
	private Double days;

	// 振休振出区分
	private int classifiction;
}
