package nts.uk.ctx.at.request.dom.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.UseAtr;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.WorkTimeGoBack;
import nts.uk.ctx.at.shared.dom.worktime.SiftCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * 直行直帰申請
 * 
 * @author ducpm
 *
 */
@AllArgsConstructor
@Value
public class GoBackDirectly {
	/**
	 * 会社ID
	 */
	private String companyID;
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 勤務種類
	 */
	private WorkTypeCode workTypeCD;
	/**
	 * 就業時間帯
	 */
	private SiftCode siftCd;
	/**
	 * 勤務を変更する
	 */
	private UseAtr workChangeAtr;
	/**
	 * 勤務直行1
	 */
	private UseAtr goWorkAtr1;
	/**
	 * 勤務直帰1
	 */
	private UseAtr backHomeAtr1;
	/**
	 * 勤務時間開始1
	 */
	private WorkTimeGoBack workTimeStart1;
	/**
	 * 勤務時間終了1
	 */
	private WorkTimeGoBack workTimeEnd1;
	/**
	 * 勤務場所選択1
	 */
	private String workLocationCD1;
	/**
	 * 勤務直行2
	 */
	private UseAtr goWorkAtr2;
	/**
	 * 勤務直帰2
	 */
	private UseAtr backHomeAtr2;
	/**
	 * 勤務時間開始2
	 */
	private WorkTimeGoBack workTimeStart2;
	/**
	 * 勤務時間終了2
	 */
	private WorkTimeGoBack workTimeEnd2;
	/**
	 * 勤務場所選択２
	 */
	private String workLocationCD2;
}
