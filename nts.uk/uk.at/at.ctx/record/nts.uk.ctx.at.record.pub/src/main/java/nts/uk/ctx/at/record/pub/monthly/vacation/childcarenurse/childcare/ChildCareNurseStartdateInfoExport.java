package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import lombok.Data;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;

/**
 * 起算日から子の看護介護休暇情報
 * @author yuri_tamakoshi
 */
@Data
public class ChildCareNurseStartdateInfoExport {
	/** 起算日からの子の看護介護休暇使用数 */
	private ChildCareNurseUsedNumber usedDays;
	/** 子の看護介護休暇残数 */
	private ChildCareNurseRemainingNumberExport remainingNumber ;
	/** 子の看護介護休暇上限日数 */
	private Double limitDays;

	/**
	 * コンストラクタ　AnnualLeaveRemainingNumber
	 */
	public ChildCareNurseStartdateInfoExport(){
		this.usedDays = new ChildCareNurseUsedNumber();
		this.remainingNumber = new ChildCareNurseRemainingNumberExport();
		this.limitDays = new Double(0.0);
	}
	/**
	 * ファクトリー
	 * @param usedDays 起算日からの子の看護介護休暇使用数
	 * @param remainingNumber 子の看護介護休暇残数
	 * @param limitDays 子の看護介護休暇上限日数
	 * @return 起算日から子の看護介護休暇情報
	 */
	public static ChildCareNurseStartdateInfoExport of (
			ChildCareNurseUsedNumber usedDays,
			ChildCareNurseRemainingNumberExport remainingNumber,
			Double limitDays) {

		ChildCareNurseStartdateInfoExport domain = new ChildCareNurseStartdateInfoExport();
		domain.usedDays = usedDays;
		domain.remainingNumber = remainingNumber;
		domain.limitDays = limitDays;
		return domain;
	}
}