package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import lombok.AllArgsConstructor;
/**
 * 予定と実績を同じに変更する区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum ScheAndRecordSameChangeFlg {
	/**常に自動変更する */
	ALWAY(0, "常に自動変更する"),
	/**	流動勤務のみ自動変更する */
	FLUIDWORK(1, "流動勤務のみ自動変更する"),
	/**自動変更しない	 */
	NOTAUTO(3, "自動変更しない");
	public final Integer value;
	
	public final String name;
}
