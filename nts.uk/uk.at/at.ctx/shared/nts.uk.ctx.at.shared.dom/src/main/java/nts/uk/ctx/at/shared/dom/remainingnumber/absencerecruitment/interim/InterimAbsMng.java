package nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimMngCommon;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RequiredDay;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UnOffsetDay;
/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.残数管理.暫定残数管理
 * 暫定振休管理データ
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class InterimAbsMng extends InterimRemain implements InterimMngCommon {
	/**	必要日数 */
	private RequiredDay requeiredDays;
	/**	未相殺日数 */
	private UnOffsetDay unOffsetDays;
	@Override
	public String getId() {
		return this.getRemainManaID();
	}
	public InterimAbsMng(String remainManaID, String sID, GeneralDate ymd, CreateAtr creatorAtr, RemainType remainType,
			RequiredDay requeiredDays, UnOffsetDay unOffsetDays) {
		super(remainManaID, sID, ymd, creatorAtr, remainType);
		this.requeiredDays = requeiredDays;
		this.unOffsetDays = unOffsetDays;
	}
	
	
	
}
