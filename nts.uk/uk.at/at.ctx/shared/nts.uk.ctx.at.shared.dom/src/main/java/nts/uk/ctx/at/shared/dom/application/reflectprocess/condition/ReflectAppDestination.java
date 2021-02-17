package nts.uk.ctx.at.shared.dom.application.reflectprocess.condition;

import lombok.AllArgsConstructor;

/**
 * @author thanh_nx
 *
 *         申請の反映先
 */
@AllArgsConstructor
public enum ReflectAppDestination {

	/** 予定 */
	SCHEDULE(0),
	/** 実績 */
	RECORD(1);

	public final int value;
}
