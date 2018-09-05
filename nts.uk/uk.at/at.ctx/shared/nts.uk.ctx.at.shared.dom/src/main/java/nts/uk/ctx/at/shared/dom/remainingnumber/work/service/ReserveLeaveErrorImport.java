package nts.uk.ctx.at.shared.dom.remainingnumber.work.service;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReserveLeaveErrorImport {
	/** 積立年休不足エラー（付与前） */
	SHORTAGE_RSVLEA_BEFORE_GRANT(0),
	/** 積立年休不足エラー（付与後） */
	SHORTAGE_RSVLEA_AFTER_GRANT(1);

	public final int value;
}
