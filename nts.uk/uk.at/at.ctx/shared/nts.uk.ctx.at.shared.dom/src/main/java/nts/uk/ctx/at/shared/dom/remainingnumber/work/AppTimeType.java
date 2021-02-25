package nts.uk.ctx.at.shared.dom.remainingnumber.work;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AppTimeType {
	/**	出勤前 */
	ATWORK(1),
	/**	退勤後 */
	OFFWORK(2),
	/**	出勤前2 */
	ATWORK2(3),
	/**	退勤後2 */
	OFFWORK2(4),
	/**	私用外出 */
	PRIVATE(5),
	/**	組合外出 */
	UNION(6);
	public final Integer value;
}
