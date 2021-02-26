package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.specialholiday.periodinformation.SpecialVacationMonths;

/**
 * テーブル以降の付与周期
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class GrantCycleAfterTbl {

	/**
	 * 付与周期
	 */
	private ElapseYearMonth elapseYearMonth;
	
	/**
	 * コンストラクタ
	 */
	public GrantCycleAfterTbl(){
		elapseYearMonth = new ElapseYearMonth();
	}
	
	public static GrantCycleAfterTbl createFromJavaType(int year, int month){
		return new GrantCycleAfterTbl(ElapseYearMonth.createFromJavaType(year, month) );
	}
	
}
