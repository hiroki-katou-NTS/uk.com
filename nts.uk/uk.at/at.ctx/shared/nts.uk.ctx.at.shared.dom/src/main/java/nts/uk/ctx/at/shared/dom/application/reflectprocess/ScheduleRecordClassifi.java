package nts.uk.ctx.at.shared.dom.application.reflectprocess;

import lombok.AllArgsConstructor;

/**
 * @author thanh_nx
 *
 *         予定実績区分
 */
@AllArgsConstructor
public enum ScheduleRecordClassifi {

	/** 予定 */
	SCHEDULE(0),
	/** 実績 */
	RECORD(1);

	public final int value;
}
