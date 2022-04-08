package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.nrweb.common;

public enum NRWebQueryError {

	NO1("NRWEB1", "引数に誤り"),

	NO2("NRWEB2", "カード番号の桁数"),

	NO3("NRWEB3", "該当者が存在しません"),

	NO4("NRWEB4", "カード番号が未登録です"),

	NO5("NRWEB5", "パラメータのパターン以外");

	public final String value;

	public final String name;

	private NRWebQueryError(String value, String name) {
		this.value = value;
		this.name = name;
	}

	private static final NRWebQueryError[] values = NRWebQueryError.values();

	public static NRWebQueryError valueStringOf(String value) {
		if (value == null) {
			return null;
		}

		for (NRWebQueryError val : NRWebQueryError.values) {
			if (val.value.equals(value)) {
				return val;
			}
		}

		return null;
	}
}
