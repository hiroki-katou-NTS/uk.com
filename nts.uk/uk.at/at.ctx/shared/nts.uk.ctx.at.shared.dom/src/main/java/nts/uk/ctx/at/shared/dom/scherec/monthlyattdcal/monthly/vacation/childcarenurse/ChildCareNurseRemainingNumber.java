package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.DayNumberOfRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.remainingnumber.TimeOfRemain;

/**
 * 子の看護介護残数
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class ChildCareNurseRemainingNumber {

	/** 日数 */
	private DayNumberOfRemain remainDay;
	/** 時間 */
	private Optional<TimeOfRemain>remainTimes;

	/**
	 * コンストラクタ　ChildCareNurseUsedNumber
	 */
	public ChildCareNurseRemainingNumber(){
		this.remainDay = new DayNumberOfRemain(0.0);
		this.remainTimes = Optional.empty();
	}

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseRemainingNumber(ChildCareNurseRemainingNumber c) {
		this.remainDay = new DayNumberOfRemain(c.remainDay.v());
		this.remainTimes = c.remainTimes.map(mapper->new TimeOfRemain(mapper.v()));
	}

	/**
	 * クローン
	 */
	public ChildCareNurseRemainingNumber clone() {
		return new ChildCareNurseRemainingNumber(this);
	}

	/**
	 * ファクトリー
	 * @param remainDay 日数
	 * @param remainTimes 時間
	 * @return 子の看護介護残数
	*/
	public static ChildCareNurseRemainingNumber of(
			DayNumberOfRemain remainDay,
			Optional<TimeOfRemain> remainTimes){

		ChildCareNurseRemainingNumber domain = new ChildCareNurseRemainingNumber();
		domain.remainDay = remainDay;
		domain.remainTimes = remainTimes;
		return domain;
	}

	/**
	 * 残数を使い過ぎていないか
	 * @return 残数を使い過ぎていないか（ture or false）
	 */
	public boolean checkOverUpperLimit() {
		// 残数を使い過ぎていないか
		// ===子の看護介護残数．日 >=0 and 子の看護介護残数．時間 >= 0
		if(remainDay.v() >= 0 && remainTimes.map(x -> x.v()).orElse(0) >= 0) {
			return true;
		}else {
			return false;
		}
	}
}