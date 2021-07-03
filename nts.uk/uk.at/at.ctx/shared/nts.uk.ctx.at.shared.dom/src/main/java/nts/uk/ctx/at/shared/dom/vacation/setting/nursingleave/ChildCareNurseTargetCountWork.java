package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import lombok.Getter;
import lombok.Setter;

/**
 * 本年と次回の対象人数
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseTargetCountWork {
	/** 次回対象人数 */
	private NumberOfCaregivers nextYearTargetCount;
	/** 本年対象人数 */
	private NumberOfCaregivers thisYearTargetCount;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseTargetCountWork(){
		this.nextYearTargetCount = new NumberOfCaregivers(0);
		this.thisYearTargetCount = new NumberOfCaregivers(0);
	}

	/**
	 * ファクトリー
	 * @param nextYearTargetCount 次回対象人数
	 * @param thisYearTargetCount 本年対象人数
	 * @return 本年と次回の対象人数
	*/
	public static ChildCareNurseTargetCountWork of(
			NumberOfCaregivers nextYearTargetCount,
			NumberOfCaregivers thisYearTargetCount){

		ChildCareNurseTargetCountWork domain = new ChildCareNurseTargetCountWork();
		domain.nextYearTargetCount = nextYearTargetCount;
		domain.thisYearTargetCount = thisYearTargetCount;
		return domain;
	}

	/** 本年対象人数を１人追加する */
	public void addThisYearOnePerson() {
		this.thisYearTargetCount = new NumberOfCaregivers(this.thisYearTargetCount.v() + 1);
	}

	/** 次回対象人数を１人追加する */
	public void addNextYearOnePerson() {
		this.nextYearTargetCount = new NumberOfCaregivers(this.nextYearTargetCount.v() + 1);
	}
}
