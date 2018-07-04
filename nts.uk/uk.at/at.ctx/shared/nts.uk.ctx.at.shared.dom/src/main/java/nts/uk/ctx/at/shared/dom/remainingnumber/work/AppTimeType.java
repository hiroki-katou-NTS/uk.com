package nts.uk.ctx.at.shared.dom.remainingnumber.work;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AppTimeType {
	/**	出勤前 */
	ATWORK(0),
	/**	退勤後 */
	OFFWORK(1),
	/**	出勤前2 */
	ATWORK2(2),
	/**	退勤後2 */
	OFFWORK2(3),
	/**	私用外出 */
	PRIVATE(4),
	/**	組合外出 */
	UNION(5);
	public final Integer value;
}
