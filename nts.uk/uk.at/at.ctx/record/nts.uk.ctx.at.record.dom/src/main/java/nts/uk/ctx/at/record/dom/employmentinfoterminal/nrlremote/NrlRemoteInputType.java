package nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote;

/**
 * @author ThanhNX
 *
 *         入力タイプ
 */
public enum NrlRemoteInputType {

	NUM(0, "num", "入力タイプ"),

	NUM_STR(1, "num_str", "数字例入力（特殊ー固定）"),

	CHAR_NUM(2, "char_num", "数字例入力（特殊）"),

	STRING(3, "string", "英数字入力"),

	TIME(4, "time", "時刻入力"),

	IP(5, "ip", "IPアドレス入力"),

	HEX(6, "hex", "HEX入力"),

	SINGLE(7, "single", "単選駅"),

	SHORT_SEL(8, "short_sel", "単選駅（特殊）"),

	MULTI(9, "multi", "複数選駅");

	public final int value;

	public final String inputType;

	public final String name;

	private static final NrlRemoteInputType[] values = NrlRemoteInputType.values();

	private NrlRemoteInputType(int value, String inputType, String name) {
		this.value = value;
		this.inputType = inputType;
		this.name = name;
	}

	public static NrlRemoteInputType valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (NrlRemoteInputType val : NrlRemoteInputType.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}

	public static NrlRemoteInputType valueInputTypeOf(String inputType) {
		if (inputType == null) {
			return null;
		}

		for (NrlRemoteInputType val : NrlRemoteInputType.values) {
			if (val.inputType.equals(inputType)) {
				return val;
			}
		}

		return null;
	}

}
