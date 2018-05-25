package nts.uk.ctx.at.shared.dom.remainmana.breakdayoffmana.interim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainmana.interimremain.primitive.UnOffsetTime;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定代休管理データ
 * @author do_dt
 *
 */
@Getter
@AllArgsConstructor
public class InterimDayOffMana extends AggregateRoot{
	/**	暫定代休管理データID */
	private String dayOffManaId;
	/**	必要時間数 */
	private RequiredTime requiredTime;
	/**	必要日数 */
	private RequiredDay requiredDay;
	/**	未相殺時間数 */
	private UnOffsetTime unOffsetTimes;
	/**	未相殺日数 */
	private UnOffsetDay unOffsetDay;
}
