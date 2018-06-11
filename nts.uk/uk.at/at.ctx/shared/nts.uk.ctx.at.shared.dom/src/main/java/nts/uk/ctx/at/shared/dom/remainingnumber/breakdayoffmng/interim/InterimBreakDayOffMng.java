package nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.SelectedAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseTime;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定休出代休紐付け管理
 * @author do_dt
 *
 */
@Getter
@AllArgsConstructor
public class InterimBreakDayOffMng extends AggregateRoot{
	/**	休出管理データ */
	private String breakManaId;
	/**	休出管理データ区分 */
	private DataManagementAtr breakManaAtr;
	/**	代休管理データ */
	private String dayOffManaId;
	/**	代休管理データ区分 */
	private DataManagementAtr dayOffManaAtr;
	/**	使用時間数 */
	private UseTime useTimes;
	/**	使用日数  */
	private UseDay useDays;
	/**	対象選択区分*/
	private SelectedAtr selectedAtr;
	
}
