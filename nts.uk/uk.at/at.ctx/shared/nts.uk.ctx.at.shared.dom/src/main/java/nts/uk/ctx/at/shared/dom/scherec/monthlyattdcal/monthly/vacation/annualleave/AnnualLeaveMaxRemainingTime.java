package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.TimeAnnualLeaveMax;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;

/**
 * 年休上限残時間
 * @author shuichu_ishida
 */
@Getter
@Setter
public class AnnualLeaveMaxRemainingTime implements Cloneable, SerializableWithOptional {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/** 時間付与前 */
	private LeaveRemainingTime timeBeforeGrant;
	/** 時間付与後 */
	private Optional<LeaveRemainingTime> timeAfterGrant;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveMaxRemainingTime(){
		
		this.timeBeforeGrant = new LeaveRemainingTime(0);
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
			LeaveRemainingTime timeBeforeGrant,
			Optional<LeaveRemainingTime> timeAfterGrant){
		
		AnnualLeaveMaxRemainingTime domain = new AnnualLeaveMaxRemainingTime();
		domain.timeBeforeGrant = timeBeforeGrant;
		domain.timeAfterGrant = timeAfterGrant;
		return domain;
	}
	
	@Override
	public AnnualLeaveMaxRemainingTime clone() {
		AnnualLeaveMaxRemainingTime cloned = new AnnualLeaveMaxRemainingTime();
		try {
			cloned.timeBeforeGrant = new LeaveRemainingTime(this.timeBeforeGrant.v());
			if (this.timeAfterGrant.isPresent()){
				cloned.timeAfterGrant = Optional.of(new LeaveRemainingTime(this.timeAfterGrant.get().v()));
			}
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveMaxRemainingTime clone error.");
		}
		return cloned;
	}
	//[1]更新する
	public AnnualLeaveMaxRemainingTime update(TimeAnnualLeaveMax maxData, GrantBeforeAfterAtr grantPeriodAtr){
		if(grantPeriodAtr == GrantBeforeAfterAtr.BEFORE_GRANT){
			return AnnualLeaveMaxRemainingTime.of(maxData.getRemainingMinutes(),this.timeAfterGrant);
		}else{
			return AnnualLeaveMaxRemainingTime.of(this.timeBeforeGrant, Optional.of(maxData.getRemainingMinutes()));
		}
	}
	
	//[2]残数超過分を補正する
	public AnnualLeaveMaxRemainingTime correctTheExcess(){
		LeaveRemainingTime beforeGrant = this.timeBeforeGrant.correctTheExcess();
		Optional<LeaveRemainingTime> afterGrant = this.timeAfterGrant.map(x -> x.correctTheExcess());
		
		return AnnualLeaveMaxRemainingTime.of(beforeGrant, afterGrant);
	}
	
	private void writeObject(ObjectOutputStream stream){	
		writeObjectWithOptional(stream);
	}	
	private void readObject(ObjectInputStream stream){	
		readObjectWithOptional(stream);
	}	

}
