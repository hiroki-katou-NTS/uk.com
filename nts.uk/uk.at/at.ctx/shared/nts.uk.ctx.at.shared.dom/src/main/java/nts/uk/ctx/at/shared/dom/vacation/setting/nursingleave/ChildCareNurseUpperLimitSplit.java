package nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 上限日数分割日
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseUpperLimitSplit {

	/** 上限日数 */
	private ChildCareNurseUpperLimit limitDays;
	/** 年月日 */
	private GeneralDate ymd;


	/**
	 * コンストラクタ
	 */
	public ChildCareNurseUpperLimitSplit(){

		this.limitDays =  new ChildCareNurseUpperLimit(0);
		this.ymd =  GeneralDate.today();
	}
	/**
	 * ファクトリー
	 * @param limitDays 上限日数
	 * @param ymd 年月日
	 * @return 上限日数分割日
	 */
	public static ChildCareNurseUpperLimitSplit of(
			 ChildCareNurseUpperLimit limitDays,
			 GeneralDate ymd) {

		ChildCareNurseUpperLimitSplit domain = new ChildCareNurseUpperLimitSplit();
		domain.limitDays = limitDays;
		domain.ymd = ymd;
		return domain;
	}
}
