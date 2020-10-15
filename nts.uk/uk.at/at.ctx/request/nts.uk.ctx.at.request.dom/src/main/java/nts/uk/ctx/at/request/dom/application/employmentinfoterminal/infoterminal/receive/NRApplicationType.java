package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive;

/**
 * @author ThanhNX
 *
 *         事前事後
 */
public enum NRApplicationType {

	BEFORE(1, "遅刻取消"),

	AFTER(2, "早退取消");

	public final int value;

	public final String nameType;

	private NRApplicationType(int value, String nameType) {
		this.value = value;
		this.nameType = nameType;
	}

	private static final NRApplicationType values[] = NRApplicationType.values();

	public static NRApplicationType valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (NRApplicationType val : NRApplicationType.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
