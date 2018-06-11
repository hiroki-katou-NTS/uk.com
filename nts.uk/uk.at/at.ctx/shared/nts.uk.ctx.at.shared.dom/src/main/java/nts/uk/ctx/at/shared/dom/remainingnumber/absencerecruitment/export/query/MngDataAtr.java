package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MngDataAtr {
	/**	予定 */
	SCHEDULE(0, "予定"),
	/**	実績 */
	RECORD(1, "実績"),
	/**	申請(事前) */
	APPBEFORE(2,"申請(事前)"),
	/**	申請(事後) */
	APPAFTER(3, "申請(事後)"),
	/**フレックス補填	 */
	FLEXCOMPEN(4, "フレックス補填"),
	/**	確定済 */
	CONFIRMED(5, "確定済");
	public final Integer value;
	
	public final String name;
}
