package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
/**
 * 月度別代休残数集計
 * @author do_dt
 *
 */
@Setter
@Getter
@AllArgsConstructor
public class InterimRemainAggregateOutputData {
	/**	年月 
	 * ※年月＝当月の場合のみ出力
	 * */
	private YearMonth ym;
	/**月初残数	 */
	private double monthStartRemain;
	/**	月度内発生数 */
	private double monthOccurrence;
	/** 月度内使用数 */
	private double monthUse;
	/** 月度内消滅数 */
	private double monthExtinction;
	/**	月末残数 */
	private double monthEndRemain;
}
