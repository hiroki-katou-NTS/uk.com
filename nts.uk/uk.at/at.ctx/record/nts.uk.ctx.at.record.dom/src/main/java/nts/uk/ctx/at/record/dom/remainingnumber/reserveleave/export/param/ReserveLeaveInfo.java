package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
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
	private List<ReserveLeaveGrantRemainingData> grantRemainingNumberList;
	/** 使用数 */
	private ReserveLeaveUsedDayNumber usedDays;
	/** 付与後フラグ */
	private boolean afterGrantAtr;
	/** 付与情報 */
	private Optional<ReserveLeaveGrantInfo> grantInfo;
	
	/**
	 * コンストラクタ
	 * @param ymd 年月日
	 */
	public ReserveLeaveInfo(GeneralDate ymd){
		
		this.ymd = ymd;
		
		this.remainingNumber = new ReserveLeaveRemainingNumber();
		this.grantRemainingNumberList = new ArrayList<>();
		this.usedDays = new ReserveLeaveUsedDayNumber(0.0);
		this.afterGrantAtr = false;
		this.grantInfo = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param remainingNumber 残数
	 * @param grantRemainingNumberList 付与残数データ
	 * @param usedDays 使用数
	 * @param afterGrantAtr 付与後フラグ
	 * @param grantInfo 付与情報
	 * @return 積立年休情報
	 */
	public static ReserveLeaveInfo of(
			GeneralDate ymd,
			ReserveLeaveRemainingNumber remainingNumber,
			List<ReserveLeaveGrantRemainingData> grantRemainingNumberList,
			ReserveLeaveUsedDayNumber usedDays,
			boolean afterGrantAtr,
			Optional<ReserveLeaveGrantInfo> grantInfo){
	
		ReserveLeaveInfo domain = new ReserveLeaveInfo(ymd);
		domain.remainingNumber = remainingNumber;
		domain.grantRemainingNumberList = grantRemainingNumberList;
		domain.usedDays = usedDays;
		domain.afterGrantAtr = afterGrantAtr;
		domain.grantInfo = grantInfo;
		return domain;
	}
}
