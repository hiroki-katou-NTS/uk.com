package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AsbRemainTotalInfor {
	/**繰越日数	 */
	private double carryForwardDays;
	/**	実績使用日数 */
	private double recordUseDays;
	/**	実績発生日数 */
	private double recordOccurrenceDays;
	/**	予定使用日数 */
	private double scheUseDays;
	/**	予定発生日数 */
	private double scheOccurrenceDays;
	
}
