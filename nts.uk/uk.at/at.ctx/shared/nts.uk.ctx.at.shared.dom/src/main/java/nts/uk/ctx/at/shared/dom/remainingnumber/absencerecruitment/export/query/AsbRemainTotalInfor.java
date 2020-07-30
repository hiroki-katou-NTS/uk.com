package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 残数集計情報
 * @author do_dt
 *
 */
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
	/**	予定発生時間数  */
	private Double scheHours;
	/**	予定使用時間数  */
	private Double scheUseHours;
	/**	実績発生時間数  */
	private Double numberOccurrenceHours;
	/**	実績使用時間数  */
	private Double actualNumberOfHourUsed;
	/**	繰越時間数  */
	private Double numberOfHoursCarriedOver;
	
	/**
	 * 繰越・実績内残日数を取得する
	 * @return
	 */
	public double recordGrantDays() {
		//繰越・実績内残日数を算出する
		//繰越・実績内残日数：繰越日数＋実績発生日数－実績使用日数
		return this.carryForwardDays + this.recordOccurrenceDays - this.getRecordUseDays();
	}
	/**
	 * 	予定内残日数を取得する
	 * @return
	 */
	public double scheGrantDays() {
		//予定内残日数を算出する
		//予定内残日数：予定発生日数－予定使用日数
		return this.scheOccurrenceDays - this.scheUseDays;
	}
}
