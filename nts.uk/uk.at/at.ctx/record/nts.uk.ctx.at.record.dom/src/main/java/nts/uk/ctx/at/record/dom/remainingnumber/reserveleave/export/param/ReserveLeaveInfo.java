package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.ReserveLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveUsedDayNumber;

/**
 * 積立年休情報
 * @author shuichu_ishida
 */
@Getter
public class ReserveLeaveInfo {

	/** 年月日 */
	private final GeneralDate ymd;
	
	/** 残数 */
	private ReserveLeaveRemainingNumber remainingNumber;
	/** 付与残数データ */
	private Map<GeneralDate, ReserveLeaveGrantRemainingData> grantRemainingNumbers;
	/** 使用数 */
	private ReserveLeaveUsedDayNumber usedDays;
	/** 付与後フラグ */
	private boolean afterGrantAtr;
	
	/**
	 * コンストラクタ
	 * @param ymd 年月日
	 */
	public ReserveLeaveInfo(GeneralDate ymd){
		
		this.ymd = ymd;
		
		this.remainingNumber = new ReserveLeaveRemainingNumber();
		this.grantRemainingNumbers = new HashMap<>();
		this.usedDays = new ReserveLeaveUsedDayNumber(0.0);
		this.afterGrantAtr = false;
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param remainingNumber 残数
	 * @param grantRemainingNumbers 付与残数データ
	 * @param usedDays 使用数
	 * @param afterGrantAtr 付与後フラグ
	 * @return 積立年休情報
	 */
	public static ReserveLeaveInfo of(
			GeneralDate ymd,
			ReserveLeaveRemainingNumber remainingNumber,
			List<ReserveLeaveGrantRemainingData> grantRemainingNumbers,
			ReserveLeaveUsedDayNumber usedDays,
			boolean afterGrantAtr){
	
		ReserveLeaveInfo domain = new ReserveLeaveInfo(ymd);
		domain.remainingNumber = remainingNumber;
		for (val grantRemainingNumber : grantRemainingNumbers){
			val grantYmd = grantRemainingNumber.getGrantDate();
			domain.grantRemainingNumbers.putIfAbsent(grantYmd, grantRemainingNumber);
		}
		domain.usedDays = usedDays;
		domain.afterGrantAtr = afterGrantAtr;
		return domain;
	}
}
