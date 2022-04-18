package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common;

/**
 * @author thanh_nx
 *
 *         NRWeb照会引数
 */
public enum NRWebQueryArg {

	CONTRACT_CODE("contractCode"),
	
	CNO("cno"),

	VER("ver"),

	TYPE("type"),

	YM("ym"),

	DATE("date"),

	KBN("kbn"),

	JIKBN("jikbn"),

	NDATE("ndate");

	public final String value;

	private NRWebQueryArg(String value) {
		this.value = value;
	}

	private static final NRWebQueryArg[] values = NRWebQueryArg.values();

	public static NRWebQueryArg valueStringOf(String value) {
		if (value == null) {
			return null;
		}

		for (NRWebQueryArg val : NRWebQueryArg.values) {
			if (val.value.equals(value)) {
				return val;
			}
		}

		return null;
	}
}
