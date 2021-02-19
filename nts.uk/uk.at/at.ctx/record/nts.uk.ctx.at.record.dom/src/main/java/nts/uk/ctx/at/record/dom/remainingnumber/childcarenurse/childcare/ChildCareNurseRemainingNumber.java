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

	/** 子の看護休暇使用日数 */
	private  DayNumberOfUse usedDays;
	/** 子の看護休暇使用時間 */
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
	 * @param usedDay　子の看護休暇使用日数
	 * @param usedTimes　子の看護休暇使用時間
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
}
