package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.temp;

import lombok.AllArgsConstructor;
/**
 * 作成元区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum CreateAtrExport {
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

}
