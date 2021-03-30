package nts.uk.cnv.dom.td.event.accept;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.event.Event;
import nts.uk.cnv.dom.td.event.EventDetail;
import nts.uk.cnv.dom.td.event.EventId;
import nts.uk.cnv.dom.td.event.EventIdProvider;

/**	
 * 検収イベント
 * @author ai_muto
 *
 */
@Getter
@AllArgsConstructor
public class AcceptEvent implements Event {
	private EventId eventId;
	private EventDetail detail;

	public static AcceptEvent create(Require require, String eventName, String userName, List<String> alterationIds) {
		EventId id = EventIdProvider.provideAcceptId(require);
		return new AcceptEvent(
				id,
				new EventDetail(
						eventName,
						GeneralDateTime.now(),
						userName,
						alterationIds));
	}

	public interface Require extends EventIdProvider.ProvideAcceptIdRequire{
	}
}
