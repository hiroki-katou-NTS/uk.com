package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
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
public class TmpResereLeaveMng extends InterimRemain {
	/**
	 * 使用日数
	 */
	private UseDay useDays;

	public TmpResereLeaveMng(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr,
			RemainType remainType, UseDay useDays) {
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.useDays = useDays;
	}

}
