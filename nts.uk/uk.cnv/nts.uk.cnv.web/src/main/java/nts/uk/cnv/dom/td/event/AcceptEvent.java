package nts.uk.cnv.dom.td.event;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.event.EventIdProvider.ProvideAcceptIdRequire;

/**
 * 検収イベント
 * @author ai_muto
 *
 */
@Getter
@AllArgsConstructor
public class AcceptEvent implements Comparable<AcceptEvent> {
	private EventId eventId;
	private GeneralDateTime datetime;
	private EventMetaData meta;
	private List<String> alterationIds;

	public static AcceptEvent create(ProvideAcceptIdRequire require, EventMetaData meta, List<String> alterationIds) {
		EventId id = EventIdProvider.provideAcceptId(require);
		return new AcceptEvent(
				id,
				GeneralDateTime.now(),
				meta,
				alterationIds);
	}

	@RequiredArgsConstructor
	private class RequireImpl implements EventIdProvider.ProvideAcceptIdRequire{
		private final AcceptEventRepository repository;

		@Override
		public Optional<String> getNewestAcceptId() {
			return repository.getNewestAcceptId();
		}
	}

	@Override
	public int compareTo(AcceptEvent o) {
		return this.datetime.compareTo(o.datetime);
	}
}
