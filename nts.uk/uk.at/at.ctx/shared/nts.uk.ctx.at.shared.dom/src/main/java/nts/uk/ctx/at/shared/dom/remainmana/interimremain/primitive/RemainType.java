package nts.uk.ctx.at.shared.dom.remainmana.interimremain.primitive;

import lombok.AllArgsConstructor;

/**
 * 残数種類
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum RemainType {
	/**	年休 */
	ANNUAL(0,"年休"),
	/**	積立年休 */
	FUNDINGANNUAL(1,"積立年休"),
	/**	特休 */
	SPECIAL(2,"特休"),
	/**	振出振休 */
	COMPLEMENTLEAVE(3,"振出振休"),
	/**	休出代休 */
	BREAKSUBS(4,"休出代休"),
	/**	時間年休 */
	TIMEANNUAL(5, "時間年休");
	
	public final Integer value;
	
	public final String name;

}
