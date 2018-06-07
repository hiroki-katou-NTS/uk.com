package nts.uk.ctx.at.function.dom.adapter.holidaysremaining;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;


@Setter
@Getter
@AllArgsConstructor
public class AbsenceReruitmentManaImported {
	/**	年月 
	 * ※年月＝当月の場合のみ出力
	 * */
	private YearMonth ym;
	/**月初残数	 */
	private Double monthStartRemain;
	/**	月度内発生数 */
	private Double monthOccurrence;
	/** 月度内使用数 */
	private Double monthUse;
	/** 月度内消滅数 */
	private Double monthExtinction;
	/**	月末残数 */
	private Double monthEndRemain;
}
