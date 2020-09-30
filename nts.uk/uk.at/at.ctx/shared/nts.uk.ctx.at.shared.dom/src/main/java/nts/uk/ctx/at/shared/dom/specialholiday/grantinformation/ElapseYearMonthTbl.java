package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import lombok.Getter;
import lombok.Setter;

/**
 * 経過年数テーブル
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
public class ElapseYearMonthTbl {

	/** 付与回数 */
	private int elapseNo;
	
	/** 経過年数 */
	private ElapseYearMonth elapseYearMonth;
}
