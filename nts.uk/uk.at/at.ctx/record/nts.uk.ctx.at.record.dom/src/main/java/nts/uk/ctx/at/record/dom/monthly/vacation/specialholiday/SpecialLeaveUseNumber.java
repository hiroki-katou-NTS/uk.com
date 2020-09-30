package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.gul.serialize.binary.SerializableWithOptional;

/**
 * 特別休暇使用数
 * @author do_dt
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpecialLeaveUseNumber extends DomainObject implements Cloneable, SerializableWithOptional {
	
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 使用日数
	 */
	private SpecialLeaveUseDays useDays;
	
	/**
	 * 使用時間
	 */
	private Optional<SpecialLeaveUseTimes> useTimes;
	
	/**
	 * コンストラクタ
	 * @param useDays
	 * @param useTimes
	 */
	public SpecialLeaveUseNumber(
			SpecialLeaveUseDays useDays, SpecialLeaveUseTimes useTimes) {
		this(useDays, Optional.ofNullable(useTimes));
	}
	
//	/**
//	 * 使用日数、使用時間をクリア
//	 */
//	public void clear(){
//		useDays.clear();
//		useTimes = Optional.empty();
//	}
	
	/**
	 * ファクトリー
	 * @param usedDays 使用日数
	 * @param usedTime 使用時間
	 * @return 年休使用数
	 */
	public static SpecialLeaveUseNumber of(
			SpecialLeaveUseDays usedDays,
			Optional<SpecialLeaveUseTimes> usedTime){
		
		SpecialLeaveUseNumber domain = new SpecialLeaveUseNumber();
		domain.useDays = usedDays;
		domain.useTimes = usedTime;
		return domain;
	}
	
	@Override
	public SpecialLeaveUseNumber clone() {
		SpecialLeaveUseNumber cloned = new SpecialLeaveUseNumber();
		try {
			cloned.useDays = this.useDays.clone();
			if (this.useTimes.isPresent()){
				cloned.useTimes = Optional.of(this.useTimes.get().clone());
			}
		}
		catch (Exception e){
			throw new RuntimeException("SpecialLeaveUseNumber clone error.");
		}
		return cloned;
	}
	
	/**
	 * 日数を使用日数に加算する
	 */
	public void addUsedNumber(SpecialLeaveUseNumber usedNumber){
		this.useDays.addUsedDays(usedNumber.getUsedDays().getUsedDayNumber().v());
		if (this.useTimes.isPresent()){
			this.useTimes.get().getUsedTime().addMinutes(
					usedNumber.getUsedTime().get().getUsedTime().v());
		}
	}
}
