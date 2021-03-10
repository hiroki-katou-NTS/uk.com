package nts.uk.ctx.at.shared.dom.scherec.aggregation.perdaily;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.gul.util.OptionalUtil;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;

/**
 * Test for DailyAttendanceGroupingUtil
 * @author kumiko_otake
 */
public class DailyAttendanceGroupingUtilTest {


	private List<IntegrationOfDaily> getTestData() {

		// 社員ID: EmpId#2 / 勤務種別CD: BTCD#1 / 日付: 3, 6, 14, 16, 31
		// 社員ID: EmpId#3 / 勤務種別CD: BTCD#2 / 日付: 3, 7, 11, 18, 25, 29, 31
		// 社員ID: EmpId#6 / 勤務種別CD: Empty  / 日付: 1, 8, 12, 15, 20, 23, 27
		// 社員ID: EmpId#8 / 勤務種別CD: BTCD#1 / 日付: 3, 7, 12, 14, 22
		// 社員ID: EmpId#9 / 勤務種別CD: BTCD#2 / 日付: 11, 16, 17, 21, 25, 29
		// ※ EmpId#3 -> 1/15以降 勤務種別CD Empty
		// ※ EmpId#8 -> 1/10以降 勤務種別CD Empty

		@SuppressWarnings("serial")
		val clearBusinessTypeCode = new HashMap<Integer, Integer>() {{
			put( 3, 15 );
			put( 6,  1 );
			put( 8, 10 );
		}};

		@SuppressWarnings("serial")
		val dlyAtdList = new HashMap<Integer, IntStream>() {{
			put( 2, IntStream.of( 3, 6, 14, 16, 31 ) );
			put( 3, IntStream.of( 3, 7, 11, 18, 25, 29, 31 ) );
			put( 6, IntStream.of( 1, 8, 12, 15, 20, 23, 27 ) );
			put( 8, IntStream.of( 3, 7, 12, 14, 22 ) );
			put( 9, IntStream.of( 11, 16, 17, 21, 25, 29 ) );
		}}.entrySet().stream()
				.flatMap( entry -> entry.getValue().boxed()
							.map( day -> {
								val dlyAtd = IntegrationOfDailyHelper.createDummy(
														String.format("EmpId#%d", entry.getKey())
													,	GeneralDate.ymd( 2021, 1, day )
												);
								if( !clearBusinessTypeCode.containsKey( entry.getKey() )
										|| clearBusinessTypeCode.get( entry.getKey() ) > day ) {
									dlyAtd.getAffiliationInfor().setBusinessTypeCode(Optional.of(new BusinessTypeCode( String.format("BTCD#%d", entry.getKey() % 2 + 1) )));
								}
								return dlyAtd;
							}) )
				.collect(Collectors.toList());

		return dlyAtdList;

	}



	/**
	 * Target	: byEmployeeId
	 */
	@Test
	public void test_byEmployeeId() {

		// 日別勤怠(Work)リスト
		val list = this.getTestData();

		// Execute
		val result = DailyAttendanceGroupingUtil.byEmployeeId( list );

		// Assertion
		val first = result.entrySet().stream().findFirst().get();
		assertThat( first.getKey() ).isExactlyInstanceOf( EmployeeId.class );
		assertThat( first.getValue().get(0) ).isExactlyInstanceOf( IntegrationOfDaily.class );

		result.entrySet().forEach( r -> {

			assertThat( r.getValue() )
				.containsExactlyInAnyOrderElementsOf(
					list.stream()
						.filter( e -> e.getEmployeeId().equals(r.getKey().v()) )
						.collect(Collectors.toList())
				);

		} );

	}


	/**
	 * Target	: byEmployeeIdWithAnyItem
	 */
	@Test
	public void test_byEmployeeIdWithAnyItem() {

		// 日別勤怠(Work)リスト
		val list = this.getTestData();

		// Execute
		val result = DailyAttendanceGroupingUtil
				.byEmployeeIdWithAnyItem( list, e -> e.getAffiliationInfor().getWplID() );

		// Assertion
		val first = result.entrySet().stream().findFirst().get();
		assertThat( first.getKey() ).isExactlyInstanceOf( EmployeeId.class );
		assertThat( first.getValue().get(0) ).isExactlyInstanceOf( String.class );

		result.entrySet().forEach( r -> {

			assertThat( r.getValue() )
				.containsExactlyInAnyOrderElementsOf(
					list.stream()
						.filter( e -> e.getEmployeeId().equals(r.getKey().v()) )
						.map( e -> e.getAffiliationInfor().getWplID() )
						.collect(Collectors.toList())
				);

		} );

	}


	/**
	 * Target	: byEmployeeIdWithoutEmpty
	 */
	@Test
	public void test_byEmployeeIdWithoutEmpty() {

		// 日別勤怠(Work)リスト
		val list = this.getTestData();

		// Execute
		val result = DailyAttendanceGroupingUtil
				.byEmployeeIdWithoutEmpty( list, e -> e.getAffiliationInfor().getBusinessTypeCode() );

		// Assertion
		val first = result.entrySet().stream().findFirst().get();
		assertThat( first.getKey() ).isExactlyInstanceOf( EmployeeId.class );
		assertThat( first.getValue().get(0) ).isExactlyInstanceOf( BusinessTypeCode.class );

		result.entrySet().forEach( r -> {

			assertThat( r.getValue() )
				.containsExactlyInAnyOrderElementsOf(
					list.stream()
						.filter( e -> e.getEmployeeId().equals(r.getKey().v()) )
						.map( e -> e.getAffiliationInfor().getBusinessTypeCode() )
						.flatMap(OptionalUtil::stream)
						.collect(Collectors.toList())
				);

		} );

	}



	/**
	 * Target	: byDate( dlyAtdList )
	 */
	@Test
	public void test_byDate() {

		// 日別勤怠(Work)リスト
		val list = this.getTestData();

		// Execute
		val result = DailyAttendanceGroupingUtil.byDate( list );

		// Assertion
		val first = result.entrySet().stream().findFirst().get();
		assertThat( first.getKey() ).isExactlyInstanceOf( GeneralDate.class );
		assertThat( first.getValue().get(0) ).isExactlyInstanceOf( IntegrationOfDaily.class );

		result.entrySet().forEach( r -> {

			assertThat( r.getValue() )
				.containsExactlyInAnyOrderElementsOf(
					list.stream()
						.filter( e -> e.getYmd().equals(r.getKey()) )
						.collect(Collectors.toList())
				);

		} );

	}


	/**
	 * Target	: byDateWithAnyItem
	 */
	@Test
	public void test_byDateWithAnyItem() {

		// 日別勤怠(Work)リスト
		val list = this.getTestData();

		// Execute
		val result = DailyAttendanceGroupingUtil
				.byDateWithAnyItem( list, e -> e.getAffiliationInfor().getWplID() );

		// Assertion
		val first = result.entrySet().stream().findFirst().get();
		assertThat( first.getKey() ).isExactlyInstanceOf( GeneralDate.class );
		assertThat( first.getValue().get(0) ).isExactlyInstanceOf( String.class );

		result.entrySet().forEach( r -> {

			assertThat( r.getValue() )
				.containsExactlyInAnyOrderElementsOf(
					list.stream()
						.filter( e -> e.getYmd().equals(r.getKey()) )
						.map( e -> e.getAffiliationInfor().getWplID() )
						.collect(Collectors.toList())
				);

		} );

	}


	/**
	 * Target	: byDateWithoutEmpty
	 */
	@Test
	public void test_byDateIdWithoutEmpty() {

		// 日別勤怠(Work)リスト
		val list = this.getTestData();

		// Execute
		val result = DailyAttendanceGroupingUtil
				.byDateWithoutEmpty( list, e -> e.getAffiliationInfor().getBusinessTypeCode() );

		// Assertion
		val first = result.entrySet().stream().findFirst().get();
		assertThat( first.getKey() ).isExactlyInstanceOf( GeneralDate.class );
		assertThat( first.getValue().get(0) ).isExactlyInstanceOf( BusinessTypeCode.class );

		result.entrySet().forEach( r -> {

			assertThat( r.getValue() )
				.containsExactlyInAnyOrderElementsOf(
					list.stream()
						.filter( e -> e.getYmd().equals(r.getKey()) )
						.map( e -> e.getAffiliationInfor().getBusinessTypeCode() )
						.flatMap(OptionalUtil::stream)
						.collect(Collectors.toList())
				);

		} );

	}



	/**
	 * Target	: [private] groupingBy
	 */
	@Test
	public void test_private_groupingBy() {

		// Execute
		Map<String, List<Integer>> result = NtsAssert.Invoke.privateMethod(
						new DailyAttendanceGroupingUtil()
					,	"groupingBy"
						,	this.getTestData()
						,	new Function<IntegrationOfDaily, String>() {
								@Override public String apply(IntegrationOfDaily arg){ return arg.getEmployeeId(); }
							}
						,	new Function<IntegrationOfDaily, Integer>() {
								@Override public Integer apply(IntegrationOfDaily arg){ return arg.getYmd().day(); }
							}
				);

		// Assertion
		assertThat( result ).containsOnlyKeys( "EmpId#2", "EmpId#3", "EmpId#6", "EmpId#8", "EmpId#9" );
		assertThat( result.get("EmpId#2") ).containsExactlyInAnyOrder( 3, 6, 14, 16, 31 );
		assertThat( result.get("EmpId#3") ).containsExactlyInAnyOrder( 7, 3, 11, 18, 25, 29, 31 );
		assertThat( result.get("EmpId#6") ).containsExactlyInAnyOrder( 1, 8, 12, 15, 20, 23, 27 );
		assertThat( result.get("EmpId#8") ).containsExactlyInAnyOrder( 3, 7, 12, 14, 22 );
		assertThat( result.get("EmpId#9") ).containsExactlyInAnyOrder( 11, 16, 17, 21, 25, 29 );

	}

	/**
	 * Target	: [private] groupingAndTruncate
	 */
	@Test
	public void test_private_groupingAndTruncate() {

		// Execute
		Map<Integer, List<String>> result = NtsAssert.Invoke.privateMethod(
						new DailyAttendanceGroupingUtil()
					,	"groupingAndTruncate"
						,	this.getTestData()
						,	new Function<IntegrationOfDaily, Integer>() {
								@Override public Integer apply(IntegrationOfDaily arg){ return arg.getYmd().day(); }
							}
						,	new Function<IntegrationOfDaily, Optional<String>>() {
							@Override public Optional<String> apply(IntegrationOfDaily arg){
								return arg.getAffiliationInfor().getBusinessTypeCode().map(BusinessTypeCode::v);
							}
						}
				);

		// Assertion
		assertThat( result.keySet() )
				.containsExactlyInAnyOrder( 1, 3, 6, 7, 8, 11, 12, 14, 15, 16, 17, 18, 20, 21, 22, 23, 25, 27, 29, 31 );

		assertThat( result.get( 3) ).containsExactlyInAnyOrder( "BTCD#1", "BTCD#1", "BTCD#2" );
		assertThat( result.get( 6) ).containsExactlyInAnyOrder( "BTCD#1" );
		assertThat( result.get(11) ).containsExactlyInAnyOrder( "BTCD#2", "BTCD#2" );
		assertThat( result.get(12) ).isEmpty();
		assertThat( result.get(25) ).containsExactlyInAnyOrder( "BTCD#2" );

	}

}
