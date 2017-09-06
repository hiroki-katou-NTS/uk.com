package nts.uk.ctx.at.record.dom.daily;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.dailyprocess.calc.record.mekestimesheet.OverTimeWorkFrameTimeSheet;

/**
 * 日別実績の残業時間
 * @author keisuke_hoshina
 *
 */
@Value
public class DailyOverTimeWork {
	private OverTimeWorkFrameTimeSheet overTimeWorkFrameTimeSheet;
}
