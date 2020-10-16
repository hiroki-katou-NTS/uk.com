package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import java.util.Date;
import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseStartdateInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.DayNumberOfUse;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.usenumber.TimeOfUse;

/**
 * 子の看護介護使用数
 * @author yuri_tamakoshi
 */
@Getter
public class ChildCareNurseUsedNumber {

	/** 日数（使用日数） */
	private Double usedDay;
	/** 時間（使用時間） */
	private Optional<Integer> usedTimes;

	/**
	 * コンストラクタ　AnnualLeaveRemainingNumber
	 */
	public ChildCareNurseUsedNumber(){
		this.usedDay = new Double(0.0);
		this.usedTimes = Optional.empty();
	}

	/**
	 * ファクトリー
	 * @param usedDay　子の看護介護休暇（使用日数）
	 * @param usedTimes　子の看護介護休暇（使用時間）
	 * @return 子の看護介護使用数
	*/
	public static ChildCareNurseUsedNumber of(
			Double usedDay,
			Optional<Integer> usedTimes){

		ChildCareNurseUsedNumber domain = new ChildCareNurseUsedNumber();
		domain.usedDay = usedDay;
		domain.usedTimes = usedTimes;
		return domain;
	}
}
