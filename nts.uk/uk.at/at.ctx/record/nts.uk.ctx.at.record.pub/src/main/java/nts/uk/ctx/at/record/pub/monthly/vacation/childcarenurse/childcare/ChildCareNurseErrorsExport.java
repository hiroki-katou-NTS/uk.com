package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;


/**
 * 子の看護介護エラー情報
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseErrorsExport {

	/** 使用数 */
	private  ChildCareNurseUsedNumberExport usedNumber;
	/** 上限日数 */
	private Integer limitDays;
	/** 年月日 */
	private GeneralDate ymd;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseErrorsExport(){

		this.usedNumber = new ChildCareNurseUsedNumberExport();
		this.limitDays = new Integer(0);
		this.ymd = GeneralDate.today();
	}

	/**
	 * ファクトリー
	 * @param usedNumber 子の看護介護使用数
	 * @param limitDays 子の看護介護上限日数
	 * @param ymd 子の看護介護エラー対象年月日
	 * @return 子の看護介護エラー情報
	 */
	public static ChildCareNurseErrorsExport of(
			ChildCareNurseUsedNumberExport usedNumber,
			Integer limitDays,
			GeneralDate ymd) {

		ChildCareNurseErrorsExport exp = new ChildCareNurseErrorsExport();
		exp.usedNumber = usedNumber;
		exp.limitDays = limitDays;
		exp.ymd = ymd;
		return exp;
	}
}