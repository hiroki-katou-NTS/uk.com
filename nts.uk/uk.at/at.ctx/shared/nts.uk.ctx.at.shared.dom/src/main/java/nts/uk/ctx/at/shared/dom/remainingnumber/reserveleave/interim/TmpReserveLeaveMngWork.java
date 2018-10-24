package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;

/**
 * 暫定積立年休管理データWORK
 * @author shuichu_ishida
 */
@Getter
public class TmpReserveLeaveMngWork {

	/** 残数管理データID */
	private String manageId;
	/** 対象日 */
	private GeneralDate ymd;
	/** 使用日数 */
	private UseDay useDays;
	/** 作成元区分 */
	private CreateAtr creatorAtr;
	/** 残数分類 */
	private RemainAtr remainAtr;
	
	/**
	 * ファクトリー
	 * @param manageId 残数管理データID
	 * @param ymd 対象日
	 * @param useDays 使用日数
	 * @param creatorAtr 作成元区分
	 * @param remainAtr 残数分類
	 * @return 暫定積立年休管理データWORK
	 */
	public static TmpReserveLeaveMngWork of(
			String manageId,
			GeneralDate ymd,
			UseDay useDays,
			CreateAtr creatorAtr,
			RemainAtr remainAtr){
		
		TmpReserveLeaveMngWork domain = new TmpReserveLeaveMngWork();
		domain.manageId = manageId;
		domain.ymd = ymd;
		domain.useDays = useDays;
		domain.creatorAtr = creatorAtr;
		domain.remainAtr = remainAtr;
		return domain;
	}
	
	/**
	 * ファクトリー
	 * @param interimRemain 暫定残数管理データ
	 * @param tmpRsvLeaMng 暫定積立年休管理データ
	 * @return 暫定積立年休管理データWORK
	 */
	public static TmpReserveLeaveMngWork of(
			InterimRemain interimRemain,
			TmpResereLeaveMng tmpRsvLeaMng){
		
		TmpReserveLeaveMngWork domain = new TmpReserveLeaveMngWork();
		domain.manageId = interimRemain.getRemainManaID();
		domain.ymd = interimRemain.getYmd();
		domain.useDays = tmpRsvLeaMng.getUseDays();
		domain.creatorAtr = interimRemain.getCreatorAtr();
		domain.remainAtr = interimRemain.getRemainAtr();
		return domain;
	}
	
	/**
	 * 等しいかどうか
	 * @param target 暫定積立年休管理データWORK
	 * @return true：等しい、false：等しくない
	 */
	public boolean equals(TmpReserveLeaveMngWork target){
		if (!this.ymd.equals(target.ymd)) return false;
		if (this.remainAtr != target.remainAtr) return false;
		return true;
	}
}
