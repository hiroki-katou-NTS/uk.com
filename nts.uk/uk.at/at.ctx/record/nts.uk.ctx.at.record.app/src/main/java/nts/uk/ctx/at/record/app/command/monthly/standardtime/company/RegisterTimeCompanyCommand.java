package nts.uk.ctx.at.record.app.command.monthly.standardtime.company;


import lombok.Getter;

/**
 * 会社の目安時間設定を登録する
 */
@Getter
public class RegisterTimeCompanyCommand {

	// 労働制 3
	private int laborSystemAtr;

	/** 1ヶ月 (Month) */

	//エラー時間
	private int errorOneMonth;

	//アラーム時間
	private int alarmOneMonth;

	// 上限時間
	private int limitOneMonth;

	//エラー時間
	private int errorTwoMonths;

	//アラーム時間
	private int alarmTwoMonths;

	// 上限時間
	private int limitTwoMonths;

	/** 1年間 (Year) */

	//エラー時間
	private int errorOneYear;

	//アラーム時間
	private int alarmOneYear;

	// 上限時間
	private int limitOneYear;

	//エラー時間
	private int errorTwoYear;

	//アラーム時間
	private int alarmTwoYear;

	// 上限時間
	private int limitTwoYear;

	/** 超過上限回数  */
	//超過上限回数
	private int overMaxTimes;

}
