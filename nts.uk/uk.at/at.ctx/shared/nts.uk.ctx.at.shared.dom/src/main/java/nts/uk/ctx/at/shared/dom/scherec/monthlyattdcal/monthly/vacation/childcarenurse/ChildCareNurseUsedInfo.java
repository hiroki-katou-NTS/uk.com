package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.childcarenurse;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata.UsedTimes;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareNurseUsedNumber;

/**
 * 子の看護介護使用数
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseUsedInfo {

	/** 使用数 */
	private ChildCareNurseUsedNumber usedNumber;
	/** 時間休暇使用回数 */
	private UsedTimes usedTimes;
	/** 時間休暇使用日数 */
	private UsedTimes usedDays;

	/**
	 * コンストラクタ　ChildCareNurseUsedNumber
	 */
	public ChildCareNurseUsedInfo(){
		this.usedNumber = new ChildCareNurseUsedNumber();
		this.usedTimes = new UsedTimes(0);
		this.usedDays = new UsedTimes(0);
	}

	/**
	 * コンストラクタ　ChildCareNurseUsedNumber
	 */
	public ChildCareNurseUsedInfo(ChildCareNurseUsedInfo c){
		this.usedNumber = c.usedNumber.clone();
		this.usedTimes = new UsedTimes(c.usedTimes.v());
		this.usedDays = new UsedTimes(c.usedDays.v());
	}

	/**
	 * クローン
	 */
	public ChildCareNurseUsedInfo clone() {
		return new ChildCareNurseUsedInfo(this);
	}

	/**
	 * ファクトリー
	 * @param usedNumber 使用数
	 * @param usedTimes 時間休暇使用回数
	 * @param usedDays 時間休暇使用日数
	 * @return 子の看護介護使用情報
	*/
	public static ChildCareNurseUsedInfo of(
			ChildCareNurseUsedNumber usedNumber,
			UsedTimes usedTimes,
			UsedTimes usedDays){

		ChildCareNurseUsedInfo domain = new ChildCareNurseUsedInfo();
		domain.usedNumber = usedNumber;
		domain.usedTimes = usedTimes;
		domain.usedDays = usedDays;
		return domain;
	}

	public ChildCareNurseUsedInfo add(ChildCareNurseUsedInfo target) {
		ChildCareNurseUsedInfo domain = this.clone();

		domain.getUsedNumber().add(target.getUsedNumber());
		domain.getUsedDays().add(target.getUsedDays());
		domain.getUsedTimes().add(target.getUsedTimes());

		return domain;
	}
}