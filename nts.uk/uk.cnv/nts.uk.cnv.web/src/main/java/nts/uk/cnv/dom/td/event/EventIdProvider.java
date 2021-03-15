package nts.uk.cnv.dom.td.event;

import java.util.Optional;

public class EventIdProvider {
	private static final String OrderPrefix = "O";
	private static final String OrderFormat = "O%7d";
	private static final String DeliveryPrefix = "D";
	private static final String DeliveryFormat = "O%7d";
	private static final String AcceptPrefix = "A";
	private static final String AcceptFormat = "O%7d";

	public static EventId provideOrderId(ProvideOrderIdRequire require) {
		Optional<String> newestId = require.getNewestOrderId();
		int number = newestId.isPresent()
				? Integer.parseInt(newestId.get().replaceAll("^" + OrderPrefix, ""))
				: 0;
		return new EventId(String.format(OrderFormat, number + 1));
	}

	public static EventId provideDeliveryId(ProvideDeliveryIdRequire require) {
		Optional<String> newestId = require.getNewestDeliveryId();
		int number = newestId.isPresent()
				? Integer.parseInt(newestId.get().replaceAll("^" + DeliveryPrefix, ""))
				: 0;
		return new EventId(String.format(DeliveryFormat, number + 1));
	}

	public static EventId provideAcceptId(ProvideAcceptIdRequire require) {
		Optional<String> newestId = require.getNewestAcceptId();
		int number = newestId.isPresent()
				? Integer.parseInt(newestId.get().replaceAll("^" + AcceptPrefix, ""))
				: 0;
		return new EventId(String.format(AcceptFormat, number + 1));
	}

	public interface ProvideOrderIdRequire {
		Optional<String> getNewestOrderId();
	}
	public interface ProvideDeliveryIdRequire {
		Optional<String> getNewestDeliveryId();
	}
	public interface ProvideAcceptIdRequire {
		Optional<String> getNewestAcceptId();
	}

}
