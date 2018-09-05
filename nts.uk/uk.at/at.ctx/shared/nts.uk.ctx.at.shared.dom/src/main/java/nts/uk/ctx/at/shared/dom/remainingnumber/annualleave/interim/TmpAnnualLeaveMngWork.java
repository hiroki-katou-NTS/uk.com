package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim;

import lombok.Getter;
import nts.arc.time.GeneralDate;
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
	
	/**
	 * ファクトリー
	 * @param manageId 残数管理データID
	 * @param ymd 対象日
	 * @param workTypeCode 勤務種類コード
	 * @param useDays 使用日数
	 * @return 暫定積立年休管理データWORK
	 */
	public static TmpAnnualLeaveMngWork of(
			String manageId,
			GeneralDate ymd,
			String workTypeCode,
			UseDay useDays){
		
		TmpAnnualLeaveMngWork domain = new TmpAnnualLeaveMngWork();
		domain.manageId = manageId;
		domain.ymd = ymd;
		domain.workTypeCode = workTypeCode;
		domain.useDays = useDays;
		return domain;
	}
}
