package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingMinutes;

/**
 * 年休上限残時間
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AnnualLeaveMaxRemainingTime implements Cloneable {

	/** 時間 */
	private RemainingMinutes time;
	/** 時間付与前 */
	private RemainingMinutes timeBeforeGrant;
	/** 時間付与後 */
	private Optional<RemainingMinutes> timeAfterGrant;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveMaxRemainingTime(){
		
		this.time = new RemainingMinutes(0);
		this.timeBeforeGrant = new RemainingMinutes(0);
		this.timeAfterGrant = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param time 時間
	 * @param timeBeforeGrant 時間付与前
	 * @param timeAfterGrant 時間付与後
	 * @return 年休上限残時間
	 */
	public static AnnualLeaveMaxRemainingTime of(
			RemainingMinutes time,
			RemainingMinutes timeBeforeGrant,
			Optional<RemainingMinutes> timeAfterGrant){
		
		AnnualLeaveMaxRemainingTime domain = new AnnualLeaveMaxRemainingTime();
		domain.time = time;
		domain.timeBeforeGrant = timeBeforeGrant;
		domain.timeAfterGrant = timeAfterGrant;
		return domain;
	}
	
	@Override
	public AnnualLeaveMaxRemainingTime clone() {
		AnnualLeaveMaxRemainingTime cloned = new AnnualLeaveMaxRemainingTime();
		try {
			cloned.time = new RemainingMinutes(this.time.v());
			cloned.timeBeforeGrant = new RemainingMinutes(this.timeBeforeGrant.v());
			if (this.timeAfterGrant.isPresent()){
				cloned.timeAfterGrant = Optional.of(new RemainingMinutes(this.timeAfterGrant.get().v()));
			}
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveMaxRemainingTime clone error.");
		}
		return cloned;
	}
}
