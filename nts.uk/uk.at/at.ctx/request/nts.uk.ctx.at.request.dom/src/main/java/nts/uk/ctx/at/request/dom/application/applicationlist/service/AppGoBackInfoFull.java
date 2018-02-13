package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.WorkTimeGoBack;
@Getter
@AllArgsConstructor
public class AppGoBackInfoFull {

	private String appID;
	/**
	 * 勤務直行1
	 */
	private int goWorkAtr1;
	/**
	 * 勤務時間開始1
	 */
	private Integer workTimeStart1;
	/**
	 * 勤務直帰1
	 */
	private int backHomeAtr1;
	/**
	 * 勤務時間終了1
	 */
	private Integer workTimeEnd1;
	/**
	 * 勤務直行2
	 */
	private int goWorkAtr2;
	/**
	 * 勤務時間開始2
	 */
	private Integer workTimeStart2;
	/**
	 * 勤務直帰2
	 */
	private int backHomeAtr2;
	/**
	 * 勤務時間終了2
	 */
	private Integer workTimeEnd2;
}
