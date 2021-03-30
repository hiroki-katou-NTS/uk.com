package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;


/**
 * 子の看護介護エラー情報
 * @author yuri_tamakoshi
 */
@Getter
@Setter
public class ChildCareNurseErrorsImport {

	/** 使用数 */
	private  ChildCareNurseUsedNumberImport usedNumber;
	/** 上限日数 */
	private Integer limitDays;
	/** 年月日 */
	private GeneralDate ymd;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseErrorsImport(){

		this.usedNumber = new ChildCareNurseUsedNumberImport();
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
	public static ChildCareNurseErrorsImport of(
			ChildCareNurseUsedNumberImport usedNumber,
			Integer limitDays,
			GeneralDate ymd) {

		ChildCareNurseErrorsImport exp = new ChildCareNurseErrorsImport();
		exp.usedNumber = usedNumber;
		exp.limitDays = limitDays;
		exp.ymd = ymd;
		return exp;
	}
}