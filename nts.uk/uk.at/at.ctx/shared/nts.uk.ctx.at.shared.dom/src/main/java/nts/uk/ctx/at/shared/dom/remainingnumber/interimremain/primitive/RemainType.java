package nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive;

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
	/**	振休 */
	PAUSE(3,"振休"),
	/**	代休 */
	SUBHOLIDAY(4,"代休"),
	/** 振出*/
	PICKINGUP(5,"振出"),
	/** 休出 */
	BREAK(6,"休出"),
	/**60H超休*/
	SIXTYHOUR(7,"60H超休"),
	/** 公休*/
	PUBLICHOLIDAY(8,"公休"),
	/**	子の看護 */
	CHILDCARE(9,"子の看護"),
	/**	介護 */
	CARE(10, "介護");


	public final Integer value;

	public final String name;

}
