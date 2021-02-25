package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 経過年数テーブル
 * @author masaaki_jinno
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ElapseYearMonthTbl {

	/** 付与回数 */
	private int grantCnt;
	
	/** 経過年数 */
	private ElapseYearMonth elapseYearMonth;
	
	public static ElapseYearMonthTbl createFromJavaType(
			int grantCnt, 
			int years, int months ) {
		
		return new ElapseYearMonthTbl(
				grantCnt, 
				new ElapseYearMonth(years, months));
	}
}
