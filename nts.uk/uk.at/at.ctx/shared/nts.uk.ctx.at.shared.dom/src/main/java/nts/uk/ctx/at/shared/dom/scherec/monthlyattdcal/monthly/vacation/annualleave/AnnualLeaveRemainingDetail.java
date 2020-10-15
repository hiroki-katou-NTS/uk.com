package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.grantremainingdata.daynumber.AnnualLeaveRemainingTime;

/**
 * 年休残明細
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AnnualLeaveRemainingDetail implements Cloneable {

	/** 付与日 */
	private GeneralDate grantDate;

	/** 日数 */
	private AnnualLeaveRemainingDayNumber days;
	/** 時間 */
	private Optional<AnnualLeaveRemainingTime> time;
	
	/**
	 * コンストラクタ
	 * @param grantDate 付与日
	 */
	public AnnualLeaveRemainingDetail(GeneralDate grantDate){
		
		this.grantDate = grantDate;

		this.days = new AnnualLeaveRemainingDayNumber(0.0);
		this.time = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param grantDate 付与日
	 * @param days 日数
	 * @param time 時間
	 * @return 年休残明細
	 */
	public static AnnualLeaveRemainingDetail of(
			GeneralDate grantDate,
			AnnualLeaveRemainingDayNumber days,
			Optional<AnnualLeaveRemainingTime> time){
		
		AnnualLeaveRemainingDetail domain = new AnnualLeaveRemainingDetail(grantDate);
		domain.days = days;
		domain.time = time;
		return domain;
	}
	
	@Override
	protected AnnualLeaveRemainingDetail clone() {
		AnnualLeaveRemainingDetail cloned = new AnnualLeaveRemainingDetail(this.grantDate);
		try {
			cloned = (AnnualLeaveRemainingDetail)super.clone();
			cloned.days = new AnnualLeaveRemainingDayNumber(this.days.v());
			if (this.time.isPresent()){
				cloned.time = Optional.of(new AnnualLeaveRemainingTime(this.time.get().v()));
			}
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveRemainingDetail clone error.");
		}
		return cloned;
	}
}
