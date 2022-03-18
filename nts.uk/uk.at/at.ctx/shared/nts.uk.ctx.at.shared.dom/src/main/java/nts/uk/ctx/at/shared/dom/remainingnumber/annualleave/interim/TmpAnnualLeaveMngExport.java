package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.CreateAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.UseDay;

/**
 * 暫定年休管理データ
 * @author shuichi_ishida
 */
@Getter
public class TmpAnnualLeaveMngExport {

	/** 社員ID */
	private String employeeId;
	/** 残数管理データID */
	private String manageId;
	/** 対象日 */
	private GeneralDate ymd;
	/** 作成元区分 */
	private CreateAtr creatorAtr;
	/** 残数種類 */
	private RemainType remainType;
//	/** 残数分類 */
//	private RemainAtr remainAtr;
	/** 勤務種類コード */
	private String workTypeCode;
	/** 使用日数 */
	private UseDay useDays;

	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param manageId 残数管理データID
	 * @param ymd 対象日
	 * @param creatorAtr 作成元区分
	 * @param remainType 残数種類
	 * @param remainAtr 残数分類
	 * @param workTypeCode 勤務種類コード
	 * @param useDays 使用日数
	 * @return 暫定年休管理データ
	 */
	public static TmpAnnualLeaveMngExport of(
			String employeeId,
			String manageId,
			GeneralDate ymd,
			CreateAtr creatorAtr,
			RemainType remainType,
//			RemainAtr remainAtr,
			String workTypeCode,
			UseDay useDays){

		TmpAnnualLeaveMngExport domain = new TmpAnnualLeaveMngExport();
		domain.employeeId = employeeId;
		domain.manageId = manageId;
		domain.ymd = ymd;
		domain.creatorAtr = creatorAtr;
		domain.remainType = remainType;
//		domain.remainAtr = remainAtr;
		domain.workTypeCode = workTypeCode;
		domain.useDays = useDays;
		return domain;
	}

	/**
	 * ファクトリー
	 * @param interimRemain 暫定残数管理データ
	 * @param tmpAnnLeaMng 暫定年休管理データ
	 * @return 暫定年休管理データ
	 */
	public static TmpAnnualLeaveMngExport of(
			InterimRemain interimRemain,
			TempAnnualLeaveMngs tmpAnnLeaMng){

		TmpAnnualLeaveMngExport domain = new TmpAnnualLeaveMngExport();
		domain.employeeId = interimRemain.getSID();
		domain.manageId = interimRemain.getRemainManaID();
		domain.ymd = interimRemain.getYmd();
		domain.creatorAtr = interimRemain.getCreatorAtr();
		domain.remainType = interimRemain.getRemainType();
//		domain.remainAtr = interimRemain.getRemainAtr();
		domain.workTypeCode = tmpAnnLeaMng.getWorkTypeCode().v();
		/** TODO: tmpAnnLeaMng.getUseNumber().getUsedDays > 1 -> error */
		domain.useDays = tmpAnnLeaMng.getUsedNumber().getUsedDayNumber().isPresent() ? new UseDay(tmpAnnLeaMng.getUsedNumber().getUsedDayNumber().get().v()): new UseDay(0d);
		return domain;
	}
}
