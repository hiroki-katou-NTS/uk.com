package nts.uk.ctx.at.request.dom.application.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.overtime.primitivevalue.WorkClock;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReasonContent;
import nts.uk.ctx.at.shared.dom.worktime.SiftCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * @author loivt
 * 残業申請
 */
@Getter
@Setter
@AllArgsConstructor
public class AppOverTime extends AggregateRoot{
	
	/**
	 * application
	 */
	private Application application;
	/**
	 * 会社ID
	 * companyID
	 */
	private String companyID;
	/**
	 * 申請ID
	 */
	private String appID;
	/**
	 * 残業区分
	 */
	private OverTimeAtr overTimeAtr;
	/**
	 * 残業申請時間設定
	 */
	private OverTimeInput overTimeInput;
	/**
	 * 勤務種類コード
	 */
	private WorkTypeCode workTypeCode;
	/**
	 * 就業時間帯
	 */
	private SiftCode siftCode;
	/**
	 * 勤務時間From1
	 */
	private int workClockFrom1;
	/**
	 * 勤務時間To1
	 */
	private int workClockTo1;
	/**
	 * 勤務時間From2
	 */
	private int workClockFrom2;
	/**
	 * 勤務時間To2
	 */
	private int workClockTo2;
	/**
	 * 乖離定型理由
	 */
	private String divergenceReasonID;
	/**
	 * 乖離理由
	 */
	private DivergenceReasonContent divergenceReasonContent;
	
	/**
	 * 計算残業時間
	 */
	private WorkClock calculationOverTime;
	/**
	 * フレックス超過時間
	 */
	private int flexExessTime;
	/**
	 * 就業時間外深夜時間
	 */
	private int overTimeShiftNight;
	
	
}
