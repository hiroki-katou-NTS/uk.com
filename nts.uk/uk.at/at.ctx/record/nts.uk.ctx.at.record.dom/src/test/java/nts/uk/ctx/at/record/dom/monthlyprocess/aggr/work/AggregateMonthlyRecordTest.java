package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work;

/**
 * 月別実績を集計する　（テスト）
 * @author shuichi_ishida
 */
public class AggregateMonthlyRecordTest {

//	private AggregateMonthlyRecordServiceImpl aggregateMonthlyRecord;
//	
//	/**
//	 * アルゴリズム　（テスト）
//	 */
//	@Test
//	public void aggregateTest() {
//		// setup
//		this.aggregateMonthlyRecord = new AggregateMonthlyRecordServiceImpl();
//		String targetCmp = "TESTCMP";
//		String targetEmp = "TESTEMP";
//		val targetYm = new YearMonth(201710);
//		ClosureId targetClosureId = ClosureId.RegularEmployee;
//		val targetClosureDate = new ClosureDate(0, true);
//		val targetPeriod = new DatePeriod(GeneralDate.ymd(2017, 10, 1), GeneralDate.ymd(2017, 10, 31));
//		
//		/* 期間受け取りテスト　2017/10/31 */
//		// exercise
//		AggregateMonthlyRecordValue value = this.aggregateMonthlyRecord.aggregate(
//				targetCmp, targetEmp, targetYm, targetClosureId, targetClosureDate, targetPeriod);
//		String actual = "EMPTY";
//		val actualList = value.getAttendanceTimes();
//		if (!actualList.isEmpty()) {
//			//actual = actualList.get(0).getDatePeriod().start().toString();
//			actual = actualList.get(0).getDatePeriod().end().toString();
//		}
//		// verify
//		assertThat(actual, is("2001/04/01"));
//
//		/* 計算値受け取りテスト　2017/10/31
//		// exercise
//		target.algorithm();
//		val actualList = target.getAttendanceTimes();
//		int actual = 0;
//		if (!actualList.isEmpty()) {
//			actual = actualList.get(0).getMonthlyCalculation().getHolidayUseTime().getAnnualLeave().getAnnualLeaveUseTime().v();
//		}
//		// verify
//		assertThat(actual, is(100));
//		*/
//	}
}
