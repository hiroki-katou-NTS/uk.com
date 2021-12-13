package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.annualleave;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import lombok.Getter;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.HalfdayAnnualLeaveMax;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.RemainingTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;

/**
 * 半日年休残数
 * @author shuichu_ishida
 */
@Getter
public class HalfDayAnnLeaRemainingNum implements Cloneable, SerializableWithOptional {

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/** 回数付与前 */
	private RemainingTimes timesBeforeGrant;
	/** 回数付与後 */
	private Optional<RemainingTimes> timesAfterGrant;
	
	/**
	 * コンストラクタ
	 */
	public HalfDayAnnLeaRemainingNum(){
		
		this.timesBeforeGrant = new RemainingTimes(0);
		this.timesAfterGrant = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param timesBeforeGrant 回数付与前
	 * @param timesAfterGrant 回数付与後
	 * @return 半日年休残数
	 */
	public static HalfDayAnnLeaRemainingNum of(
			 RemainingTimes timesBeforeGrant, Optional<RemainingTimes> timesAfterGrant){
		
		HalfDayAnnLeaRemainingNum domain = new HalfDayAnnLeaRemainingNum();
		domain.timesBeforeGrant = timesBeforeGrant;
		domain.timesAfterGrant = timesAfterGrant;
		return domain;
	}
	
	@Override
	public HalfDayAnnLeaRemainingNum clone() {
		HalfDayAnnLeaRemainingNum cloned = new HalfDayAnnLeaRemainingNum();
		try {
			cloned.timesBeforeGrant = new RemainingTimes(this.timesBeforeGrant.v());
			if (this.timesAfterGrant.isPresent()){
				cloned.timesAfterGrant = Optional.of(new RemainingTimes(this.timesAfterGrant.get().v()));
			}
		}
		catch (Exception e){
			throw new RuntimeException("HalfDayAnnLeaRemainingNum clone error.");
		}
		return cloned;
	}
	
	//[1]更新する
	public HalfDayAnnLeaRemainingNum update(HalfdayAnnualLeaveMax maxData, GrantBeforeAfterAtr grantPeriodAtr){
		if(grantPeriodAtr == GrantBeforeAfterAtr.BEFORE_GRANT){
			return HalfDayAnnLeaRemainingNum.of(maxData.getRemainingTimes(),timesAfterGrant);
		}else{
			return  HalfDayAnnLeaRemainingNum.of(timesBeforeGrant,Optional.of(maxData.getRemainingTimes()));
		}
	}
	//[2]残数超過分を補正する
	public HalfDayAnnLeaRemainingNum correctTheExcess(){
		RemainingTimes timesBeforeGrant = this.timesBeforeGrant.correctTheExcess();
		Optional<RemainingTimes> timesAfterGrant = this.getTimesAfterGrant()
				.map(x->x.correctTheExcess());
		
		return HalfDayAnnLeaRemainingNum.of(timesBeforeGrant, timesAfterGrant) ;
	}
	
	
	private void writeObject(ObjectOutputStream stream){	
		writeObjectWithOptional(stream);
	}	
	private void readObject(ObjectInputStream stream){	
		readObjectWithOptional(stream);
	}	

}
