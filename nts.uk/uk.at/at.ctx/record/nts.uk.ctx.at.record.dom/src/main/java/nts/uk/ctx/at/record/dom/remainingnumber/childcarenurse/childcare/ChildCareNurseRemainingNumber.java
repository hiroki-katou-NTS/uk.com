package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;


/**
 * 子の看護介護残数
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseRemainingNumber  implements Cloneable{

	/** 日数 */
	private  DayNumberOfUse usedDays;
	/** 時間 */
	private Optional<TimeOfUse> usedTime;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseRemainingNumber(){

		this.usedDays = new DayNumberOfUse(0.0);
		this.usedTime = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDay　日数
	 * @param usedTimes　時間
	 * @return 子の看護介護残数
	*/
	public static ChildCareNurseRemainingNumber of (
			DayNumberOfUse usedDays,
			Optional<TimeOfUse> usedTime) {

		ChildCareNurseRemainingNumber domain = new ChildCareNurseRemainingNumber();
		domain.usedDays = usedDays;
		domain.usedTime = usedTime;
		return domain;
	}

	/**
	 * 残数を使い過ぎていないか
	 * @return 残数を使い過ぎていないか（ture or false）
	 */
	public boolean checkOverUpperLimit() {
		// 残数を使い過ぎていないか
		// ===子の看護介護残数．日 >=0 and 子の看護介護残数．時間 >= 0
		if(usedDays.v() >= 0 && getUsedTime().map(x -> x.v()).orElse(0) >= 0) {
			return true;
		}else {
			return false;
		}
	}

	/** 子の看護介護残数を引算 */
	public void sub(ChildCareNurseRemainingNumber usedNumber) {
		usedDays = new DayNumberOfUse(usedDays.v() - usedNumber.getUsedDays().v());
		if (usedTime.isPresent()) {
			usedTime = usedTime.map(c -> c.minusMinutes(usedNumber.getUsedTime().map(x -> x.v()).orElse(0)));
		} else {
			usedTime = usedNumber.getUsedTime();
		}
	}
}
