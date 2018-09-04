package nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim;

import lombok.Getter;
import nts.arc.time.GeneralDate;
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
	
	/**
	 * ファクトリー
	 * @param manageId 残数管理データID
	 * @param ymd 対象日
	 * @param useDays 使用日数
	 * @return 暫定積立年休管理データWORK
	 */
	public static TmpReserveLeaveMngWork of(
			String manageId,
			GeneralDate ymd,
			UseDay useDays){
		
		TmpReserveLeaveMngWork domain = new TmpReserveLeaveMngWork();
		domain.manageId = manageId;
		domain.ymd = ymd;
		domain.useDays = useDays;
		return domain;
	}
}
