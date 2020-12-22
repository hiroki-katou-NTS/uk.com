package nts.uk.ctx.at.schedule.dom.shift.management.schedulecounter.timezonepeople;

import java.util.HashSet;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.error.BusinessException;
import nts.arc.layer.dom.objecttype.DomainAggregate;

/**
 *	AggregateRoot : 職場計の時間帯人数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.シフト管理.シフト勤務.スケジュール集計.時間帯人数.職場計の時間帯人数
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
