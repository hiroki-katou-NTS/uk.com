package nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive;

import lombok.AllArgsConstructor;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.MngDataStatus;
/**
 * 作成元区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum CreateAtr {
	/**	予定 */
	SCHEDULE(0, "予定"),
	/**	実績 */
	RECORD(1, "実績"),
	/**	申請(事前) */
	APPBEFORE(2,"申請(事前)"),
	/**	申請(事後) */
	APPAFTER(3, "申請(事後)"),
	/**フレックス補填	 */
	FLEXCOMPEN(4, "フレックス補填");
	public final Integer value;
	
	public final String name;
	
	//[1] 管理データ状態区分に変換する
	public MngDataStatus  convertToMngData(boolean confirmWhether) {
		if (confirmWhether) {
			return MngDataStatus.CONFIRMED;
		} else {
			switch (this) {
			case SCHEDULE:
				return MngDataStatus.SCHEDULE;
			case RECORD:
			case FLEXCOMPEN:
				return MngDataStatus.RECORD;
			case APPBEFORE:
			case APPAFTER:
				return MngDataStatus.NOTREFLECTAPP;
			default:
				return null;
			}
		}
	}

}
