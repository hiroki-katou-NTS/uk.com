package nts.uk.ctx.at.record.dom.daily.breaktimegoout;


import java.util.List;

import lombok.Value;

/**
 * 日別実績の休憩時間帯
 * @author keisuke_hoshina
 *
 */
@Value
public class BreakTimeSheetOfDaily {
	private BreakCategory breakClassification;
	
	private List<BreakTimeSheet> breakTimeSheet;
	
	/**
	 * 休憩種類の取得
	 * @return
	 */
	public BreakCategory getcategory() {
		return this.breakClassification.is();
	}

}
