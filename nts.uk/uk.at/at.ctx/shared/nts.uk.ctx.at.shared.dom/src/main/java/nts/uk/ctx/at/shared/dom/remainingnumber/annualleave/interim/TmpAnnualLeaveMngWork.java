package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;

/**
 * 暫定年休管理データWORK
 * @author shuichi_ishida
 */
@Getter
public class TmpAnnualLeaveMngWork {

	/** 残数管理データID */
	private String manageId;
	/** 対象日 */
	private GeneralDate ymd;
	/** 勤務種類コード */
	private String workTypeCode;
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
	 * @param workTypeCode 勤務種類コード
	 * @param useDays 使用日数
	 * @param creatorAtr 作成元区分
	 * @param remainAtr 残数分類
	 * @return 暫定年休管理データWORK
	 */
	public static TmpAnnualLeaveMngWork of(
			String manageId,
			GeneralDate ymd,
			String workTypeCode,
			UseDay useDays,
			CreateAtr creatorAtr,
			RemainAtr remainAtr){
		
		TmpAnnualLeaveMngWork domain = new TmpAnnualLeaveMngWork();
		domain.manageId = manageId;
		domain.ymd = ymd;
		domain.workTypeCode = workTypeCode;
		domain.useDays = useDays;
		domain.creatorAtr = creatorAtr;
		domain.remainAtr = remainAtr;
		return domain;
	}
	
	/**
	 * ファクトリー
	 * @param interimRemain 暫定残数管理データ
	 * @param tmpAnnLeaMng 暫定年休管理データ
	 * @return 暫定年休管理データWORK
	 */
	public static TmpAnnualLeaveMngWork of(
			InterimRemain interimRemain,
			TmpAnnualHolidayMng tmpAnnLeaMng){
		
		TmpAnnualLeaveMngWork domain = new TmpAnnualLeaveMngWork();
		domain.manageId = interimRemain.getRemainManaID();
		domain.ymd = interimRemain.getYmd();
		domain.workTypeCode = tmpAnnLeaMng.getWorkTypeCode();
		domain.useDays = tmpAnnLeaMng.getUseDays();
		domain.creatorAtr = interimRemain.getCreatorAtr();
		domain.remainAtr = interimRemain.getRemainAtr();
		return domain;
	}
	
	/**
	 * 等しいかどうか
	 * @param target 暫定年休管理データWORK
	 * @return true：等しい、false：等しくない
	 */
	public boolean equals(TmpAnnualLeaveMngWork target){
		if (!this.ymd.equals(target.ymd)) return false;
		if (this.remainAtr != target.remainAtr) return false;
		if (this.creatorAtr == CreateAtr.FLEXCOMPEN){
			if (target.creatorAtr != CreateAtr.FLEXCOMPEN) return false;
		}
		else {
			if (target.creatorAtr == CreateAtr.FLEXCOMPEN) return false;
		}
		return true;
	}
}
