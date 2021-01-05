package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday;

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

	public SpecialLeaveUseNumber() {
		useDays = new SpecialLeaveUseDays();
		useTimes = Optional.empty();
	}

	/**
	 * コンストラクタ
	 * @param useDays
	 * @param useTimes
	 */
	public SpecialLeaveUseNumber(
			SpecialLeaveUseDays useDays, SpecialLeaveUseTimes useTimes) {
		this(useDays, Optional.ofNullable(useTimes));
	}

	/**
	 * @param useDays
	 * @param useTimes
	 */
	static public SpecialLeaveUseNumber of(
			double useDays_in, Integer useTimes_in) {

		Optional<SpecialLeaveUseTimes> useTimes = Optional.empty();

		if ( useTimes_in != null ) {
			useTimes = Optional.of(new SpecialLeaveUseTimes(new SpecialLeavaRemainTime(useTimes_in)));
		}

		SpecialLeaveUseNumber specialLeaveUseNumber
			= new SpecialLeaveUseNumber(
					SpecialLeaveUseDays.of(new SpecialLeaveRemainDay(useDays_in)),
					useTimes);

		return specialLeaveUseNumber;
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

		cloned.useDays = this.useDays.clone();
		if (this.useTimes.isPresent()){
			cloned.useTimes = Optional.of(this.useTimes.get().clone());
		}
		return cloned;
	}

	/**
	 * 使用数を加算する
	 */
	public void addUsedNumber(SpecialLeaveUseNumber usedNumber){

		this.useDays.addUseDays(usedNumber.getUseDays());

		if ( usedNumber.getUseTimes().isPresent()){
			if (this.useTimes.isPresent()){
				this.useTimes.get().addUseTimes(usedNumber.getUseTimes().get());
			}
		}
	}
}


