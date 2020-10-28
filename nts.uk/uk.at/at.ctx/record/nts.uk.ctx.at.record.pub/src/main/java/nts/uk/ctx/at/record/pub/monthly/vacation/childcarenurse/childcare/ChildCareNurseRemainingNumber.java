package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import java.util.Optional;
import lombok.Getter;

/**
 * 子の看護介護残数
 * @author yuri_tamakoshi
*/
@Getter
public class ChildCareNurseRemainingNumber  implements Cloneable{

	/** 子の看護休暇使用日数 */
	private  Double usedDays;
	/** 子の看護休暇使用時間 */
	private Optional<Integer> usedTime;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseRemainingNumber(){

		this.usedDays = new Double(0.0);
		this.usedTime = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDay　子の看護休暇使用日数
	 * @param usedTimes　子の看護休暇使用時間
	 * @return 子の看護介護残数
	*/
	public static ChildCareNurseRemainingNumber of (
			Double usedDays,
			Optional<Integer> usedTime) {

		ChildCareNurseRemainingNumber domain = new ChildCareNurseRemainingNumber();
		domain.usedDays = usedDays;
		domain.usedTime = usedTime;
		return domain;
	}
}
