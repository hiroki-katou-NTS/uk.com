package nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timezonepeople;

import nts.arc.primitive.TimeDurationPrimitiveValue;
import nts.arc.primitive.constraint.TimeRange;

/**
 * 職場計の開始時刻
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.職場計・個人計.時間帯人数.職場計の開始時刻
 * @author dan_pv
 *
 */
@TimeRange(min = "-12:00", max = "71:00")
public class WorkplaceCounterStartTime extends TimeDurationPrimitiveValue<WorkplaceCounterStartTime> {

	public WorkplaceCounterStartTime(int timeAsMinutes) {
		super(timeAsMinutes);
	}

	private static final long serialVersionUID = -313910965196797184L;

}
