package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;


import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;

/**
 * 起算日から子の看護介護休暇情報
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseStartdateInfo {
	/** 起算日からの子の看護介護休暇使用数 */
	private ChildCareNurseUsedNumber usedDays;
	/** 子の看護介護休暇残数 */
	private ChildCareNurseRemainingNumber remainingNumber ;
	/** 子の看護介護休暇上限日数 */
	private ChildCareNurseUpperLimit limitDays;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseStartdateInfo(){
		this.usedDays = new ChildCareNurseUsedNumber();
		this.remainingNumber = new ChildCareNurseRemainingNumber();
		this.limitDays = new ChildCareNurseUpperLimit(0.0);
	}
	/**
	 * ファクトリー
	 * @param usedDays 起算日からの子の看護介護休暇使用数
	 * @param remainingNumber 子の看護介護休暇残数
	 * @param limitDays 子の看護介護休暇上限日数
	 * @return 起算日から子の看護介護休暇情報
	 */
	public static ChildCareNurseStartdateInfo of (
			ChildCareNurseUsedNumber usedDays,
			ChildCareNurseRemainingNumber remainingNumber,
			ChildCareNurseUpperLimit limitDays) {

		ChildCareNurseStartdateInfo domain = new ChildCareNurseStartdateInfo();
		domain.usedDays = usedDays;
		domain.remainingNumber = remainingNumber;
		domain.limitDays = limitDays;
		return domain;
	}

}