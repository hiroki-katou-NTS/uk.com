package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.残数管理.残数管理.積立年休管理.暫定データ.暫定積立年休管理データ
 * @author do_dt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TmpResereLeaveMng extends AggregateRoot{
	/**
	 * 暫定積立年休管理データID
	 */
	private String resereId;
	/**
	 * 使用日数
	 */
	private UseDay useDays;
}
