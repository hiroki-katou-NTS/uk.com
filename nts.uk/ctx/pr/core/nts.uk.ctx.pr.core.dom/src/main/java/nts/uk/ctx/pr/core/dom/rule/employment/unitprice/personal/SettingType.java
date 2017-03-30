package nts.uk.ctx.pr.core.dom.rule.employment.unitprice.personal;
/**
 * 
 * @author sonnh
 *
 */
public enum SettingType {
	/**
	 * 0 - 会社一律
	 */
	COMPANY(0),
	/**
	 * 1 - 給与契約形態ごとに指定する
	 */
	CONTRACT(1);

	public final int value;

	SettingType(int value) {
		this.value = value;
	}
}
