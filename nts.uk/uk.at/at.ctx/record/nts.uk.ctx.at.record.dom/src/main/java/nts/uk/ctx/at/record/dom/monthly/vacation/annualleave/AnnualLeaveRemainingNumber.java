package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;

/**
 * 年休残数
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveRemainingNumber {

	/** 合計残日数 */
	private AnnualLeaveRemainingDayNumber totalRemainingDays;
	/** 合計残時間 */
	private Optional<RemainingMinutes> totalRemainingTime;
	/** 明細 */
	private List<AnnualLeaveRemainingDetail> details;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveRemainingNumber(){
		
		this.totalRemainingDays = new AnnualLeaveRemainingDayNumber(0.0);
		this.totalRemainingTime = Optional.empty();
		this.details = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param totalRemainingDays 合計残日数
	 * @param totalRemainingTime 合計残時間
	 * @param details 明細
	 * @return 年休残数
	 */
	public static AnnualLeaveRemainingNumber of(
			AnnualLeaveRemainingDayNumber totalRemainingDays,
			Optional<RemainingMinutes> totalRemainingTime,
			List<AnnualLeaveRemainingDetail> details){
		
		AnnualLeaveRemainingNumber domain = new AnnualLeaveRemainingNumber();
		domain.totalRemainingDays = totalRemainingDays;
		domain.totalRemainingTime = totalRemainingTime;
		domain.details = details;
		return domain;
	}
}
