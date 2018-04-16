package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthly.vacation.annualleave.AnnualLeaveGrant;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.AnnualLeaveGrantRemainingData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveUsedDayNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.AnnualLeaveMaxData;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.UsedMinutes;

/**
 * 年休情報
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveInfo {

	/** 年月日 */
	private final GeneralDate ymd;
	
	/** 残数 */
	private AnnualLeaveRemainingNumber remainingNumber;
	/** 付与残数データ */
	private Map<GeneralDate, AnnualLeaveGrantRemainingData> grantRemainingNumbers;
	/** 上限データ */
	private AnnualLeaveMaxData maxData;
	/** 付与情報 */
	private Optional<AnnualLeaveGrant> grantInfo;
	/** 使用日数 */
	private AnnualLeaveUsedDayNumber usedDays;
	/** 使用時間 */
	private UsedMinutes usedTime;
	/** 付与後フラグ */
	private boolean afterGrantAtr;
	
	/**
	 * コンストラクタ
	 * @param ymd 年月日
	 */
	public AnnualLeaveInfo(GeneralDate ymd){
		
		this.ymd = ymd;
		
		this.remainingNumber = new AnnualLeaveRemainingNumber();
		this.grantRemainingNumbers = new HashMap<>();
		this.maxData = new AnnualLeaveMaxData();
		this.grantInfo = Optional.empty();
		this.usedDays = new AnnualLeaveUsedDayNumber(0.0);
		this.usedTime = new UsedMinutes(0);
		this.afterGrantAtr = false;
	}
	
	/**
	 * ファクトリー
	 * @param ymd 年月日
	 * @param remainingNumber 残数
	 * @param grantRemainingNumbers 付与残数データ
	 * @param maxData 上限データ
	 * @param grantInfo 付与情報
	 * @param usedDays 使用日数
	 * @param usedTime 使用時間
	 * @param afterGrantAtr 付与後フラグ
	 * @return 年休情報
	 */
	public static AnnualLeaveInfo of(
			GeneralDate ymd,
			AnnualLeaveRemainingNumber remainingNumber,
			List<AnnualLeaveGrantRemainingData> grantRemainingNumbers,
			AnnualLeaveMaxData maxData,
			Optional<AnnualLeaveGrant> grantInfo,
			AnnualLeaveUsedDayNumber usedDays,
			UsedMinutes usedTime,
			boolean afterGrantAtr){
	
		AnnualLeaveInfo domain = new AnnualLeaveInfo(ymd);
		domain.remainingNumber = remainingNumber;
		for (val grantRemainingNumber : grantRemainingNumbers){
			val grantYmd = grantRemainingNumber.getGrantDate();
			domain.grantRemainingNumbers.putIfAbsent(grantYmd, grantRemainingNumber);
		}
		domain.maxData = maxData;
		domain.grantInfo = grantInfo;
		domain.usedDays = usedDays;
		domain.usedTime = usedTime;
		domain.afterGrantAtr = afterGrantAtr;
		return domain;
	}
}
