package nts.uk.ctx.at.record.dom;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.dailyprocess.calc.CalculationTimeSheet;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedworkset.timespan.TimeSpanWithRounding;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 深夜時間帯
 * @author keisuke_hoshina
 *
 */
public class MidNightTimeSheet extends CalculationTimeSheet{

	private CompanyId companyId;
//	private TimeWithDayAttr start;
//	private TimeWithDayAttr end;
	private TimeSpanForCalc timeSpan;
	
	public MidNightTimeSheet(TimeSpanWithRounding timeSheet, TimeSpanForCalc calculationTimeSheet) {
		super(timeSheet, calculationTimeSheet);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 計算範囲と深夜時間帯の重複部分取得
	 * @return 重複部分
	 */
	public Optional<TimeSpanForCalc> TimeSpanForCalc () {
		return this.timeSpan.getDuplicatedWith(((CalculationTimeSheet)this).getCalculationTimeSheet());
	}
}
