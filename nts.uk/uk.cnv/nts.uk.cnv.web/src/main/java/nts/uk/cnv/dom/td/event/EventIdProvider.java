package nts.uk.cnv.dom.td.event;

public class EventIdProvider {
	private static final String OrderPrefix = "O";
	private static final String OrderFormat = "O%7d";
	private static final String DeliveryPrefix = "D";
	private static final String DeliveryFormat = "O%7d";
	private static final String AcceptPrefix = "A";
	private static final String AcceptFormat = "O%7d";

	public static EventId provideOrderId(ProvideOrderIdRequire require) {
		String newestId = require.getNewestOrderId();
		int number = Integer.parseInt(newestId.replaceAll("^" + OrderPrefix, ""));
		return new EventId(String.format(OrderFormat, number + 1));
	}

	public static EventId provideDeliveryId(ProvideDeliveryIdRequire require) {
		String newestId = require.getNewestDeliveryId();
		int number = Integer.parseInt(newestId.replaceAll("^" + DeliveryPrefix, ""));
		return new EventId(String.format(DeliveryFormat, number + 1));
	}

	public static EventId provideAcceptId(ProvideAcceptIdRequire require) {
		String newestId = require.getNewestAcceptId();
		int number = Integer.parseInt(newestId.replaceAll("^" + AcceptPrefix, ""));
		return new EventId(String.format(AcceptFormat, number + 1));
	}

	public interface ProvideOrderIdRequire {
		String getNewestOrderId();
	}
	public interface ProvideDeliveryIdRequire {
		String getNewestDeliveryId();
	}
	public interface ProvideAcceptIdRequire {
		String getNewestAcceptId();
	}

}
