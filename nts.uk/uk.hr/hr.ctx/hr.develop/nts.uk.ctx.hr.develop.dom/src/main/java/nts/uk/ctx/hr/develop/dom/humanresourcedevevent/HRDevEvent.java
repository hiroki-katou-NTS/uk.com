package nts.uk.ctx.hr.develop.dom.humanresourcedevevent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

/**人材育成イベント*/
@AllArgsConstructor
@Getter
public class HRDevEvent extends AggregateRoot{
	// イベントID
	private EventId eventId;
	// イベント名
	private EventName eventName;
	// 備考
	private Memo memo;
	// 利用できる
	private AvailableEvent availableEvent;
	
	/**
	 * create domain from java type
	 * @param eventId
	 * @param memo
	 * @param availableEvent
	 * @return
	 */
	public static HRDevEvent createFromJavaType(int eventId, String memo, String eventName, int availableEvent) {
		return new HRDevEvent(EnumAdaptor.valueOf(eventId, EventId.class),
				new EventName(eventName),
				new Memo(memo), 
				EnumAdaptor.valueOf(availableEvent, AvailableEvent.class));
	}
	
}
