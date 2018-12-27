package nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMng;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@AllArgsConstructor
@Setter
@Getter
public class SpecialHolidayDataParam {
	/**	会社ID */
	private String cid;
	/**	社員ID */
	private String sid;
	/**	・集計開始日・集計終了日 */
	private DatePeriod dateData;
	/**特別休暇コード	 */
	private int speCode;
	/**	モード */
	private boolean mode;
	/**	上書きフラグ */
	private boolean overwriteFlg;
	/**	上書き用の暫定管理データ */
	private List<InterimRemain> remainData;
	/**	上書き用の 特別休暇暫定データ*/
	private List<InterimSpecialHolidayMng> interimSpecialData;
}
