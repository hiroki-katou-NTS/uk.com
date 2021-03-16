package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;


import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.ChildCareNurseUpperLimit;

/**
 * 子の看護介護エラー情報
  * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseErrors {
	/** 使用数 */
	private  ChildCareNurseUsedNumber usedNumber;
	/** 上限日数 */
	private ChildCareNurseUpperLimit limitDays;
	/** 年月日 */
	private GeneralDate ymd;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseErrors(){

		this.usedNumber = new ChildCareNurseUsedNumber();
		this.limitDays =  new ChildCareNurseUpperLimit(0);
		this.ymd =  GeneralDate.today();
	}
	/**
	 * ファクトリー
	 * @param usedNumber 使用数
	 * @param limitDays 限日数
	 * @param ymd 年月日
	 * @return 子の看護介護エラー情報
	 */
	public static ChildCareNurseErrors of(
			ChildCareNurseUsedNumber usedNumber,
			ChildCareNurseUpperLimit limitDays,
			 GeneralDate ymd) {

		ChildCareNurseErrors domain = new ChildCareNurseErrors();
		domain.usedNumber = usedNumber;
		domain.limitDays = limitDays;
		domain.ymd = ymd;
		return domain;
	}
}