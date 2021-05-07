package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import lombok.Getter;
import lombok.Setter;

/**
 * 起算日からの子の看護介護休暇情報
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseStartdateInfo {
	/** 起算日からの使用数 */
	private ChildCareNurseUsedNumberExport usedDays;
	/** 残数 */
	private ChildCareNurseRemainingNumber remainingNumber ;
	/** 上限日数 */
	private Integer limitDays;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseStartdateInfo(){
		this.usedDays = new ChildCareNurseUsedNumberExport();
		this.remainingNumber = new ChildCareNurseRemainingNumber();
		this.limitDays = new Integer(0);
	}
	/**
	 * ファクトリー
	 * @param usedDays 起算日からの使用数
	 * @param remainingNumber 残数
	 * @param limitDays 上限日数
	 * @return 起算日からの子の看護介護休暇情報
	 */
	public static ChildCareNurseStartdateInfo of (
			ChildCareNurseUsedNumberExport usedDays,
			ChildCareNurseRemainingNumber remainingNumber,
			Integer limitDays) {

		ChildCareNurseStartdateInfo domain = new ChildCareNurseStartdateInfo();
		domain.usedDays = usedDays;
		domain.remainingNumber = remainingNumber;
		domain.limitDays = limitDays;
		return domain;
	}
}