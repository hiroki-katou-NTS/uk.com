package nts.uk.ctx.at.function.dom.adapter.vacation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;


@Getter
@Setter
public class CurrentHolidayImported {

	/**	年月 
	 * ※年月＝当月の場合のみ出力
	 * */
	private YearMonth ym;
	/**月初残数 = 月初残日数	 */
	private Double monthStartRemain;
	/**	月度内発生数 = 発生日数 */
	private Double monthOccurrence;
	/** 月度内使用数 = 使用日数 */
	private Double monthUse;
	/** 月度内消滅数  = 未消化日数 */
	private Double monthExtinction;
	/**	月末残数  = 残数日数*/
	private Double monthEndRemain;
	public CurrentHolidayImported(YearMonth ym, Double monthStartRemain, Double monthOccurrence,
			Double monthUse, Double monthExtinction, Double monthEndRemain) {
		this.ym = ym;
		this.monthStartRemain = monthStartRemain;
		this.monthOccurrence = monthOccurrence;
		this.monthUse = monthUse;
		this.monthExtinction = monthExtinction;
		this.monthEndRemain = monthEndRemain;
	}
	
	
	
}
