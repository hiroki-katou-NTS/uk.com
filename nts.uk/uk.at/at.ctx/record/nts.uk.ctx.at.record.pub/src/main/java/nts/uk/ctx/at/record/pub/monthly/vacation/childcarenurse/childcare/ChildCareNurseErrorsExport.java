package nts.uk.ctx.at.record.pub.monthly.vacation.childcarenurse.childcare;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.ChildCareNurseUsedNumber;


/**
 * 子の看護介護エラー情報
 * @author yuri_tamakoshi
 */
@Data
public class ChildCareNurseErrorsExport {
	/** 子の看護介護使用数 */
	private  ChildCareNurseUsedNumber usedNumber;
	/** 子の看護介護上限日数 */
	private Double limitDays;
	/** 子の看護介護エラー対象年月日 */
	private GeneralDate ymd;

	/**
	 * コンストラクタ
	 */
	public ChildCareNurseErrorsExport(){

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
	public static ChildCareNurseErrorsExport of(
			ChildCareNurseUsedNumber usedNumber,
			Double limitDays,
			GeneralDate ymd) {

		ChildCareNurseErrorsExport domain = new ChildCareNurseErrorsExport();
		domain.usedNumber = usedNumber;
		domain.limitDays = limitDays;
		domain.ymd = ymd;
		return domain;
	}
}