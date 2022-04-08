package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common;

public enum NRType {

	XML("nr"),

	OTHER("other");

	public final String value;

	private NRType(String value) {
		this.value = value;
	}

	private static final NRType[] values = NRType.values();

	public static NRType valueStringOf(String value) {
		if (value == null) {
			return null;
		}

		for (NRType val : NRType.values) {
			if (val.value.equals(value)) {
				return val;
			}
		}

		return null;
	}
}
