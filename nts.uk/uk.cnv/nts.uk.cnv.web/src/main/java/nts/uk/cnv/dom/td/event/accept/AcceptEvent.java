package nts.uk.cnv.dom.td.event.accept;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.event.EventDetail;
import nts.uk.cnv.dom.td.event.EventId;
import nts.uk.cnv.dom.td.event.EventIdProvider;
import nts.uk.cnv.dom.td.event.EventIdProvider.ProvideAcceptIdRequire;

/**
 * 検収イベント
 * @author ai_muto
 *
 */
@Getter
@AllArgsConstructor
public class AcceptEvent {
	private EventId eventId;
	private EventDetail detail;

	public static AcceptEvent create(ProvideAcceptIdRequire require, String eventName, String userName, List<String> alterationIds) {
		EventId id = EventIdProvider.provideAcceptId(require);
		return new AcceptEvent(
				id,
				new EventDetail(
						eventName,
						GeneralDateTime.now(),
						userName,
						alterationIds));
	}

	@RequiredArgsConstructor
	private class RequireImpl implements EventIdProvider.ProvideAcceptIdRequire{
		private final AcceptEventRepository repository;

		@Override
		public Optional<String> getNewestAcceptId() {
			return repository.getNewestAcceptId();
		}
	}
}
