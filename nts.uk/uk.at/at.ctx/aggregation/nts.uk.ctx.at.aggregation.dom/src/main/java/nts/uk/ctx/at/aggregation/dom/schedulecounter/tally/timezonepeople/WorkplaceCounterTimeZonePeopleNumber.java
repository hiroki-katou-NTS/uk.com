package nts.uk.ctx.at.aggregation.dom.schedulecounter.tally.timezonepeople;

import java.util.HashSet;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 * 職場計の時間帯人数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.予実集計.スケジュール集計.職場計・個人計.時間帯人数.職場計の時間帯人数
 * @author dan_pv
 *
 */
@AllArgsConstructor
public class WorkplaceCounterTimeZonePeopleNumber implements DomainAggregate {

	/**
	 * 時間帯リスト
	 */
	@Getter
	private List<WorkplaceCounterStartTime> timeZoneList;

	/**
	 *
	 * @param timeZoneList
	 * @return
	 */
	public static WorkplaceCounterTimeZonePeopleNumber create(List<WorkplaceCounterStartTime> timeZoneList) {

		if ( timeZoneList.isEmpty() || timeZoneList.size() > 24 ) {
			throw new BusinessException("Msg_1819");
		}

		if ( timeZoneList.size() != new HashSet<>(timeZoneList).size() ) {
			throw new BusinessException("Msg_1820");
		}

		return new WorkplaceCounterTimeZonePeopleNumber(timeZoneList);
	}

}
