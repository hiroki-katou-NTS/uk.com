package nts.uk.ctx.at.request.dom.adapter.monthly.vacation.childcarenurse;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;


/**
 * 子の看護介護エラー情報
 * @author yuri_tamakoshi
 */
@Data
public class ChildCareNurseErrorsImport {
	/** 子の看護介護使用数 */
	private  ChildCareNurseUsedNumber usedNumber;
	/** 子の看護介護上限日数 */
	private Double limitDays;
	/** 子の看護介護エラー対象年月日 */
	private GeneralDate ymd;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseErrorsImport(){

		this.usedNumber = new ChildCareNurseUsedNumber();
		this.limitDays = new Double(0.0);
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
			ChildCareNurseUsedNumber usedNumber,
			Double limitDays,
			GeneralDate ymd) {

		ChildCareNurseErrorsImport domain = new ChildCareNurseErrorsImport();
		domain.usedNumber = usedNumber;
		domain.limitDays = limitDays;
		domain.ymd = ymd;
		return domain;
	}
}