package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.service;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare.ChildCareNurseUsedNumber;

/**
* 暫定子の看護管理データ
* @author yuri_tamakoshi
*/
public class TempChildCareNurseManagement {

	/** 使用数 */
	private ChildCareNurseUsedNumber usedNumber;
	/** 時間休暇種類 */
	private  Optional<Timedigestion> timezoneToUseHourlyHoliday; //一時対応　Timedigestion:時間休暇種類及び時間消化

	/**
	 * コンストラクタ
	 */
	public TempChildCareNurseManagement(){
		this.usedNumber = new ChildCareNurseUsedNumber();
		this.timezoneToUseHourlyHoliday = Optional.empty();
	}
	/**
	 * ファクトリー
	 * @param usedNumber 使用数
	 * @param timezoneToUseHourlyHoliday 時間休暇種類
	 * @return 暫定子の看護管理データ
	 */
	public static TempChildCareNurseManagement of(
			ChildCareNurseUsedNumber usedNumber,
			Optional<Timedigestion>  timezoneToUseHourlyHoliday) {

		TempChildCareNurseManagement domain = new TempChildCareNurseManagement();
		domain.usedNumber = usedNumber;
		domain.timezoneToUseHourlyHoliday = timezoneToUseHourlyHoliday;
		return domain;
	}
}
