package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimMngCommon;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.OccurrenceDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnUsedDay;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定振出管理データ
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class InterimRecMng extends InterimRemain implements InterimMngCommon {

	/**	使用期限日 */
	private GeneralDate expirationDate;
	/**	発生日数 */
	private OccurrenceDay occurrenceDays;
	/**	未使用日数 */
	private UnUsedDay unUsedDays;
	@Override
	public String getId() {
		return this.getRemainManaID();
	}
	public InterimRecMng(String remainManaID, String sid, GeneralDate ymd, CreateAtr creatorAtr, RemainType remainType,
			GeneralDate useDate, OccurrenceDay occurrenceDay, UnUsedDay unUsedDay) {
		super(remainManaID, sid, ymd, creatorAtr, remainType);

		this.expirationDate = useDate;
		this.occurrenceDays = occurrenceDay;
		this.unUsedDays = unUsedDay;
	}



}
