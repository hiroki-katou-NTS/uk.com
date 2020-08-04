package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetTime;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定代休管理データ
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class InterimDayOffMng extends AggregateRoot{
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
