package nts.uk.ctx.at.request.dom.application.gobackdirectly.service;

import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.WorkTimeGoBack;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 直行直帰遅刻早退有効チェック Check Output Late or Early
 * 
 * @author ducpm
 *
 */
@Setter
public class CheckValidOutput {
	/**
	 * 勤務時間開始1
	 */
	WorkTimeGoBack workTimeStart;
	/**
	 * 勤務時間終了1
	 */
	WorkTimeGoBack workTimeEnd;
	/**
	 * 勤務種類
	 */
	WorkTypeCode workTypeCD;
	/**
	 * 就業時間帯
	 */
	WorkTimeCode siftCd;
	// GoBackDirectly goBackDirectly;
	/**
	 * チェック対象
	 */
	boolean isCheckValid;
}
