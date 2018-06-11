package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import lombok.AllArgsConstructor;

/**
 * 管理データ状態区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum MngDataStatus {
	/**
	 * 確定済
	 */
	CONFIRMED(0),
	/**
	 * 実績
	 */
	RECORD(1),
	/**
	 * スケジュール
	 */
	SCHEDULE(2),
	/**
	 * 未反映申請
	 */
	NOTREFLECTAPP(3);
	public final Integer value;
}
