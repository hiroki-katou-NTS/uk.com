package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MngHistDataAtr {
	/**	確定済 */
	CONFIRMED(0, "確定済"),
	/**	実績 */
	RECORD(1, "実績"),
	/**予定	 */
	SCHEDULE(2, "予定"),
	/**未反映申請	 */
	NOTREFLECT(3, "未反映申請");
	public final Integer value;
	
	public final String name;
}
