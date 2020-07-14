package nts.uk.ctx.at.record.dom.monthly.vacation.annualleave;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.gul.serialize.binary.SerializableWithOptional;

/**
 * 年休未消化数
 * @author shuichu_ishida
 */
@Getter
public class AnnualLeaveUndigestedNumber implements Cloneable, SerializableWithOptional{

	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/** 未消化日数 */
	private UndigestedAnnualLeaveDays undigestedDays;
	/** 未消化時間 */
	@Setter
	private Optional<UndigestedTimeAnnualLeaveTime> undigestedTime;
	
	/**
	 * コンストラクタ
	 */
	public AnnualLeaveUndigestedNumber(){
		
		this.undigestedDays = new UndigestedAnnualLeaveDays();
		this.undigestedTime = Optional.empty();
	}
	
	/**
	 * ファクトリー
	 * @param undigestedDays 未消化日数
	 * @param undigestedTime 未消化時間
	 * @return 年休未消化数
	 */
	public static AnnualLeaveUndigestedNumber of(
			UndigestedAnnualLeaveDays undigestedDays,
			Optional<UndigestedTimeAnnualLeaveTime> undigestedTime){
		
		AnnualLeaveUndigestedNumber domain = new AnnualLeaveUndigestedNumber();
		domain.undigestedDays = undigestedDays;
		domain.undigestedTime = undigestedTime;
		return domain;
	}
	
	@Override
	public AnnualLeaveUndigestedNumber clone() {
		AnnualLeaveUndigestedNumber cloned = new AnnualLeaveUndigestedNumber();
		try {
			cloned.undigestedDays = this.undigestedDays.clone();
			if (this.undigestedTime.isPresent()){
				cloned.undigestedTime = Optional.of(this.undigestedTime.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("AnnualLeaveUndigestedNumber clone error.");
		}
		return cloned;
	}
	
	private void writeObject(ObjectOutputStream stream){	
		writeObjectWithOptional(stream);
	}	
	private void readObject(ObjectInputStream stream){	
		readObjectWithOptional(stream);
	}	

}
