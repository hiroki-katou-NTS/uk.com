package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive;

/**
 * @author ThanhNX
 * 
 *         カード区分 NR
 *
 */
public enum CardCategory {

	ID_INPUT("A", "ＩＤ入力"),

	MAGNETIC_CARD("B", "磁気カード"),

	IC_CARD("C", "ＩＣカード"),

	FINGER("D", "指紋・静脈");

	public final String value;

	public final String nameType;

	private static final CardCategory[] values = CardCategory.values();

	private CardCategory(String value, String nameType) {
		this.value = value;
		this.nameType = nameType;
	}

	public static CardCategory valueStringOf(String value) {
		if (value == null) {
			return null;
		}

		for (CardCategory val : CardCategory.values) {
			if (val.value.equals(value)) {
				return val;
			}
		}

		return null;
	}
}
