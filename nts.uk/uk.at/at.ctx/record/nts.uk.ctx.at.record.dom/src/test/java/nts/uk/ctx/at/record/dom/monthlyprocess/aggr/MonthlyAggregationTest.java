package nts.uk.ctx.at.record.dom.monthlyprocess.aggr;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 社員の月別実績を集計する　（テスト）
 * @author shuichi_ishida
 */
public class MonthlyAggregationTest {

	/**
	 * 社員の月別実績を集計する　（テスト）
	 */
	@Test
	public void managerTest() {
		
		// setup
		MonthlyAggregation monthlyAggregation = new MonthlyAggregation();
		String targetCmp = "TESTCMP";
		List<String> targetEmps = new ArrayList<>();
		targetEmps.add("TESTEMP");
		val targetYm = new YearMonth(201710);
		val targetPeriod = new DatePeriod(GeneralDate.ymd(2017, 10, 1), GeneralDate.ymd(2017, 10, 31));
		
		// exercise
		int actual = monthlyAggregation.manager(targetCmp, targetEmps, targetYm, targetPeriod);
		
		// verify
		assertThat(actual, is(0));
	}
	
	/**
	 * 社員の月別実績を集計する　（テスト）
	 */
	@Test
	public void aggregateByEmployeeTest(){
		
		// setup
		MonthlyAggregation monthlyAggregation = new MonthlyAggregation();
		String targetCmp = "TESTCMP";
		String targetEmp = "TESTEMP";
		val targetYm = new YearMonth(201710);
		val targetPeriod = new DatePeriod(GeneralDate.ymd(2017, 10, 1), GeneralDate.ymd(2017, 10, 31));
		
		// exercise
		int actual = monthlyAggregation.aggregateByEmployee(targetCmp, targetEmp, targetYm, targetPeriod);
		
		// verify
		assertThat(actual, is(0));
	}
}
