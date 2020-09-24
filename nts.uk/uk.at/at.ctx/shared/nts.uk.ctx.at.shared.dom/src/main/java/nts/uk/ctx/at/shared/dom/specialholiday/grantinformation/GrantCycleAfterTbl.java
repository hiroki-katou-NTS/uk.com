package nts.uk.ctx.at.shared.dom.specialholiday.grantinformation;

import lombok.Getter;
import lombok.Setter;

/**
 * テーブル以降の付与周期
 * @author masaaki_jinno
 *
 */
@Getter
@Setter
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
	
}
