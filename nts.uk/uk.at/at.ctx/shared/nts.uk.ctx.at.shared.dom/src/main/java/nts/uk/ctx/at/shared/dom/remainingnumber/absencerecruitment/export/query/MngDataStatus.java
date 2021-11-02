package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;

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

	public CreateAtr getCreateAtr() {
		switch (this) {
		case CONFIRMED:
			return CreateAtr.SCHEDULE;
		case RECORD:
			return CreateAtr.RECORD;
		case SCHEDULE:
			return CreateAtr.SCHEDULE;
		case NOTREFLECTAPP:
			return CreateAtr.APPAFTER;
		}
		return CreateAtr.RECORD;
	}
}
