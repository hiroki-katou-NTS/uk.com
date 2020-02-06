package nts.uk.ctx.at.record.dom.monthly.calc.actualworkingtime;

import static org.junit.Assert.*;

import org.junit.Test;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 月別実績の通常変形時間　（テスト）
 * @author shuichu_ishida
 */
public class RegularAndIrregularTimeOfMonthlyTest {

	/**
	 * 期間計算方法の確認
	 */
	@Test
	public void calcDays(){
		
		// setup
		DatePeriod datePeriod = new DatePeriod(
				GeneralDate.ymd(2018, 1, 10),
				GeneralDate.ymd(2018, 1, 18));
		
		// exercize
		int actual = datePeriod.start().daysTo(datePeriod.end());
		
		// verify
		assertEquals(8, actual);
	}
}
