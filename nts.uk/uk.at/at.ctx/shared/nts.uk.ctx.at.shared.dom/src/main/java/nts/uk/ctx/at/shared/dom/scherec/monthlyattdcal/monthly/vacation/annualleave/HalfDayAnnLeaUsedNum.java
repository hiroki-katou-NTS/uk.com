package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import lombok.Getter;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.HalfdayAnnualLeaveMax;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;

/**
 * 半日年休使用数
 * @author shuichu_ishida
 */
@Getter
public class HalfDayAnnLeaUsedNum implements Cloneable, SerializableWithOptional {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/** 回数 */
	private UsedTimes times;
	/** 回数付与前 */
	private UsedTimes timesBeforeGrant;
	/** 回数付与後 */
	private Optional<UsedTimes> timesAfterGrant;

	/**
	 * コンストラクタ
	 */
	public HalfDayAnnLeaUsedNum(){
		
		this.times = new UsedTimes(0);
		this.timesBeforeGrant = new UsedTimes(0);
		this.timesAfterGrant = Optional.empty();
	}
	
	//[C-1] 作成する
	public  HalfDayAnnLeaUsedNum(UsedTimes timesBeforeGrant, Optional<UsedTimes> timesAfterGrant){
		this.times = totalUsed(timesBeforeGrant, timesAfterGrant);
		this.timesBeforeGrant = timesBeforeGrant;
		this.timesAfterGrant = timesAfterGrant;
	}
	

	/**
	 * ファクトリー
	 * @param times 回数
	 * @param timeBeforeGrant 回数付与前
	 * @param timesAfterGrant 回数付与後
	 * @return 半日年休使用数
	 */
	public static HalfDayAnnLeaUsedNum of(
			UsedTimes times, UsedTimes timeBeforeGrant, Optional<UsedTimes> timesAfterGrant){
		
		HalfDayAnnLeaUsedNum domain = new HalfDayAnnLeaUsedNum();
		domain.times = times;
		domain.timesBeforeGrant = timeBeforeGrant;
		domain.timesAfterGrant = timesAfterGrant;
		return domain;
	}
	
	@Override
	public HalfDayAnnLeaUsedNum clone() {
		HalfDayAnnLeaUsedNum cloned = new HalfDayAnnLeaUsedNum();
		try {
			cloned.times = new UsedTimes(this.times.v());
			cloned.timesBeforeGrant = new UsedTimes(this.timesBeforeGrant.v());
			if (this.timesAfterGrant.isPresent()){
				cloned.timesAfterGrant = Optional.of(new UsedTimes(this.timesAfterGrant.get().v()));
			}
		}
		catch (Exception e){
			throw new RuntimeException("HalfDayAnnLeaUsedNum clone error.");
		}
		return cloned;
	}
	
	
	//[1]更新する
	public HalfDayAnnLeaUsedNum update(HalfdayAnnualLeaveMax maxData, GrantBeforeAfterAtr grantPeriodAtr){
		if(grantPeriodAtr == GrantBeforeAfterAtr.BEFORE_GRANT){
			return new HalfDayAnnLeaUsedNum(maxData.getUsedTimes(),this.timesAfterGrant);
		}else{
			return new HalfDayAnnLeaUsedNum(timesBeforeGrant,Optional.of(maxData.getUsedTimes()));
		}
	}
	
	
	//[2]残数超過分を補正する
	public HalfDayAnnLeaUsedNum correctTheExcess(HalfDayAnnLeaRemainingNum remainingNum){
		UsedTimes beforeGrant = this.timesBeforeGrant.correctTheExcess(remainingNum.getTimesBeforeGrant());
		
		Optional<UsedTimes> afterGrant;
		if(remainingNum.getTimesAfterGrant().isPresent()){
			afterGrant = this.timesAfterGrant
					.map(x -> x.correctTheExcess(remainingNum.getTimesAfterGrant().get()));
		}else{
			afterGrant = this.timesAfterGrant;
		}
		return new HalfDayAnnLeaUsedNum(beforeGrant,afterGrant);
	}
	
	
	private void writeObject(ObjectOutputStream stream){	
		writeObjectWithOptional(stream);
	}	
	private void readObject(ObjectInputStream stream){	
		readObjectWithOptional(stream);
	}	
	
	//[prv-1]合計回数を求める
	private UsedTimes totalUsed(UsedTimes timesBeforeGrant, Optional<UsedTimes> timesAfterGrant){
		if(timesAfterGrant.isPresent()){
			return new UsedTimes(timesBeforeGrant.v() + timesAfterGrant.get().v());
		}else{
			return timesBeforeGrant;
		}
			
	}

}
