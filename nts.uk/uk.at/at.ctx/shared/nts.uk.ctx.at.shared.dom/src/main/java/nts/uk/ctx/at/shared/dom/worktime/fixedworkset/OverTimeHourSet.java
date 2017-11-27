package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;


import lombok.Value;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.CommomSetting.primitive.WorkTimeNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
/**
 * 残業時間の時間帯設定
 * @author keisuke_hoshina
 *
 */
@Value
public class OverTimeHourSet{
	/** 就業時間枠NO */
	private final WorkTimeNo frameNo;
	
	/** 残業枠No*/
	private final OverTimeFrameNo overTimeWorkFrameNo;
	
	/** 時間帯 */
	private final TimeSpanWithRounding timeSpan;
	/**早出残業として扱う**/
	private final boolean treatAsGoEarlyOverTimeWork;
	
	/**拘束時間として扱う**/
	private final boolean treatAsTimeSpentAtWork;
}

