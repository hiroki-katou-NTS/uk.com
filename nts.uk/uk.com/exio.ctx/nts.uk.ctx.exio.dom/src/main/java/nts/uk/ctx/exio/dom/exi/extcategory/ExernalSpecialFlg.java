package nts.uk.ctx.exio.dom.exi.extcategory;

import lombok.AllArgsConstructor;

/**
 * 特殊区分
 * @author do_dt
 *
 */
@AllArgsConstructor
public enum ExernalSpecialFlg {
	/**	特殊項目ではない */
	NO_SPECIAL(0),
	/**	個人ＩＤ */
	PERSON_ID(1),
	/**	履歴ＩＤ */
	HISTORY_ID(2),
	APPLICATION_ID(3),
	START_DATE(4),
	END_DATE(5),
	UPD_DATE(6),
	UPD_SCD(7),
	UPD_PG(8),
	YMD(9),
	YMDHM(10),
	YMDHMM(11),
	PERSON_HISTORY_ID(12),
	UPD_DATE_STRING(13),
	DISASS_YMD(14),
	DISASS_ITEM(15),
	HIERARCHY_CD(16),
	PERSON_ID_DAY(17),
	SCD(18),
	START_DAY_H(19);
	
	public final Integer value;
}
