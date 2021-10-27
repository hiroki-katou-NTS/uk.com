package nts.uk.screen.at.app.kdw013.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.daily.ouen.SupportFrameNo;
import nts.uk.ctx.at.record.dom.daily.timegroup.TaskTimeZone;

/**
 * 
 * @author sonnlb
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskTimeZoneCommand {

	/** 時間帯 */
	private TimeSpanForCalcCommand caltimeSpan;

	/** 対象応援勤務枠 */
	private int supNo;

	public static TaskTimeZone toDomain(TaskTimeZoneCommand domain) {
		return new TaskTimeZone(TimeSpanForCalcCommand.toDomain(domain.getCaltimeSpan()),
				new SupportFrameNo(domain.getSupNo()));
	}

}
