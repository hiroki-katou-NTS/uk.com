package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

/**
 * @author ThanhNX
 *
 *         就業情報端末の機種類
 */
public enum ModelEmpInfoTer {

	NRL_1(0),

	NRL_2(1),

	NRL_M(2);

	public final int value;

	private static final ModelEmpInfoTer[] values = ModelEmpInfoTer.values();

	private ModelEmpInfoTer(int value) {
		this.value = value;
	}

	public static ModelEmpInfoTer valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (ModelEmpInfoTer val : ModelEmpInfoTer.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}

}
