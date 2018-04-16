package nts.uk.ctx.at.record.dom.monthly.vacation.reserveleave;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.empinfo.grantremainingdata.daynumber.ReserveLeaveRemainingDayNumber;

/**
 * 積立年休残数
 * @author shuichu_ishida
 */
@Getter
public class ReserveLeaveRemainingNumber {

	/** 合計残日数 */
	@Setter
	private ReserveLeaveRemainingDayNumber totalRemainingDays;
	/** 明細 */
	private List<ReserveLeaveRemainingDetail> details;
	
	/**
	 * コンストラクタ
	 */
	public ReserveLeaveRemainingNumber(){
		
		this.totalRemainingDays = new ReserveLeaveRemainingDayNumber(0.0);
		this.details = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param totalRemainingDays 合計残日数
	 * @param details 明細
	 * @return 積立年休残数
	 */
	public static ReserveLeaveRemainingNumber of(
			ReserveLeaveRemainingDayNumber totalRemainingDays,
			List<ReserveLeaveRemainingDetail> details){
		
		ReserveLeaveRemainingNumber domain = new ReserveLeaveRemainingNumber();
		domain.totalRemainingDays = totalRemainingDays;
		domain.details = details;
		return domain;
	}
}
