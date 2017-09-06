package nts.uk.ctx.at.shared.dom.worktime.fixedworkset;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.common.time.HasTimeSpanList;

/**
 * 就業時間の時間帯設定のリスト
 * @author keisuke_hoshina
 *
 */
@Value
public class WorkTimeOfTimeSheetSetList implements HasTimeSpanList<WorkTimeOfTimeSheetSet> {

	private final List<WorkTimeOfTimeSheetSet> list;

	@Override
	public List<WorkTimeOfTimeSheetSet> getTimeSpanList() {
		return this.list;
	}

}
