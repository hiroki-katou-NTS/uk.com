package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;


/**
 * 起算日から子の看護介護休暇情報
 * @author yuri_tamakoshi
 */
@Data
public class ChildCareNurseStartdateInfoImport {
	/** 起算日からの子の看護介護休暇使用数 */
	private ChildCareNurseUsedNumber usedDays;
	/** 子の看護介護休暇残数 */
	private ChildCareNurseRemainingNumberImport remainingNumber ;
	/** 子の看護介護休暇上限日数 */
	private Integer limitDays;

	/**
	 * コンストラクタ　AnnualLeaveRemainingNumber
	 */
	public ChildCareNurseStartdateInfoImport(){
		this.usedDays = new ChildCareNurseUsedNumber();
		this.remainingNumber = new ChildCareNurseRemainingNumberImport();
		this.limitDays = new Integer(0);
	}
	/**
	 * ファクトリー
	 * @param usedDays 起算日からの子の看護介護休暇使用数
	 * @param remainingNumber 子の看護介護休暇残数
	 * @param limitDays 子の看護介護休暇上限日数
	 * @return 起算日から子の看護介護休暇情報
	 */
	public static ChildCareNurseStartdateInfoImport of (
			ChildCareNurseUsedNumber  usedDays,
			ChildCareNurseRemainingNumberImport remainingNumber,
			Integer limitDays) {

		ChildCareNurseStartdateInfoImport domain = new ChildCareNurseStartdateInfoImport();
		domain.usedDays = usedDays;
		domain.remainingNumber = remainingNumber;
		domain.limitDays = limitDays;
		return domain;
	}
}