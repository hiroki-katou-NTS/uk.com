package nts.uk.ctx.at.shared.dom.supportmanagement.supportableemployee;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.MaximumNumberOfSupport;
import nts.uk.ctx.at.shared.dom.supportmanagement.supportoperationsetting.SupportOperationSetting;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class SupportableEmployeeCheckServiceTest {

	@Injectable SupportableEmployeeCheckService.Require require;



	/**
	 * Target	: isRegistable
	 * Pattern	:
	 * 	- 応援形式	-> 終日応援
	 * 	- 既存データ-> なし
	 * Expected	: 登録可能
	 */
	@Test
	public void test_isRegistable_allday_existingDataIsEmpty() {

		new Expectations() {{
			require.getByPeriod( (EmployeeId)any, (DatePeriod)any );
		}};


		// 実行
		val result = SupportableEmployeeCheckService.isRegistrable(
				require
			,	Helper.createDummyAsAllday( DatePeriod.years( 1, GeneralDate.ymd( 2021, 10, 13 ) ) )
		);


		// 検証
		assertThat( result ).isEqualTo( SupportableEmployeeCheckService.CheckResult.REGISTABLE );

	}

	/**
	 * Target	: isRegistable
	 * Pattern	:
	 * 	- 応援形式	-> 終日応援
	 * 	- 既存データ-> あり(IDが同じもののみ)
	 * Expected	: 登録可能
	 */
	@Test
	public void test_isRegistable_allday_existingDataIsOnlyMyself() {

		val existing = Helper.createDummyAsAllday( DatePeriod.years( 1, GeneralDate.ymd( 2021, 10, 31 ) ) );
		val target = existing.changePeriod( DatePeriod.years( 1, GeneralDate.ymd( 2021, 10,  9 ) ) );

		new Expectations() {{
			require.getByPeriod( (EmployeeId)any, (DatePeriod)any );
			result = Arrays.asList( existing );
		}};


		// 実行
		val result = SupportableEmployeeCheckService.isRegistrable( require, target );


		// 検証
		assertThat( result ).isEqualTo( SupportableEmployeeCheckService.CheckResult.REGISTABLE );

	}

	/**
	 * Target	: isRegistable
	 * Pattern	:
	 * 	- 応援形式	-> 終日応援
	 * 	- 既存データ-> あり(IDが異なるものを含む)
	 * Expected	: 登録不可(期間が重複している)
	 */
	@Test
	public void test_isRegistable_allday_existingDataIsNotOnlyMyself() {

		val existing = Helper.createDummyAsAllday( DatePeriod.years( 1, GeneralDate.ymd( 2021, 10, 31 ) ) );
		val dummy = Helper.createDummyAsTimezone( GeneralDate.ymd( 2021, 10,  9 ), Helper.createTimespan( 15, 30, 21, 45 ) );

		new Expectations() {{
			require.getByPeriod( (EmployeeId)any, (DatePeriod)any );
			result = Arrays.asList( existing, dummy );
		}};


		// 実行
		val result = SupportableEmployeeCheckService.isRegistrable(
								require
							,	existing.changePeriod( DatePeriod.years( 1, GeneralDate.ymd( 2021, 10,  9 ) ) )
						);

		// 検証
		assertThat( result ).isEqualTo( SupportableEmployeeCheckService.CheckResult.DUPLICATED_PERIOD );

	}


	/**
	 * Target	: isRegistable
	 * Pattern	:
	 * 	- 応援形式	-> 時間帯応援
	 * 	- 既存データ-> 終日応援
	 * Expected	: 登録不可(期間が重複している)
	 */
	@Test
	public void test_isRegistable_timezone_existingDataIsAllday() {

		val existing = Helper.createDummyAsAllday( DatePeriod.years( 1, GeneralDate.ymd( 2021, 10,  9 ) ) );

		new Expectations() {{
			require.getByPeriod( (EmployeeId)any, (DatePeriod)any );
			result = Arrays.asList( existing );
		}};


		// 実行
		val result = SupportableEmployeeCheckService.isRegistrable(
				require
			,	Helper.createDummyAsTimezone( GeneralDate.ymd( 2021, 10, 31 ), Helper.createTimespan( 10,  0, 15,  0 ) )
		);


		// 検証
		assertThat( result ).isEqualTo( SupportableEmployeeCheckService.CheckResult.DUPLICATED_PERIOD );

	}

	/**
	 * Target	: isRegistable
	 * Pattern	:
	 * 	- 応援形式	-> 時間帯応援
	 * 	- 既存データ-> 時間帯応援 && 時間帯が重複している
	 * Expected	: 登録不可(期間が重複している)
	 */
	@Test
	public void test_isRegistable_timezone_existingDataIsTimezone_timespanIsDuplicated() {

		val target = Helper.createDummyAsTimezone( GeneralDate.ymd( 2021, 10, 31 ), Helper.createTimespan( 13,  0, 20,  0 ) );
		val duplicated = Helper.createDummyAsTimezone( target.getPeriod().start(), Helper.createTimespan( 12,  0, 18,  0 ) );
		val dummy = Helper.createDummyAsTimezone( target.getPeriod().start(), Helper.createTimespan(  8,  0, 10,  0 ) );

		new Expectations() {{
			require.getByPeriod( (EmployeeId)any, (DatePeriod)any );
			result = Arrays.asList( dummy, duplicated );
		}};


		// 実行
		val result = SupportableEmployeeCheckService.isRegistrable( require, target );


		// 検証
		assertThat( result ).isEqualTo( SupportableEmployeeCheckService.CheckResult.DUPLICATED_TIMEZONE );

	}

	/**
	 * Target	: isRegistable
	 * Pattern	:
	 * 	- 応援形式	-> 時間帯応援
	 * 	- 既存データ-> 時間帯応援 && 時間帯が重複していない && 応援回数の上限に達している
	 * Expected	: 登録不可(応援可能な回数が上限に達している)
	 */
	@Test
	public void test_isRegistable_timezone_supportCountIsOverUpperLimit(
			@Injectable SupportOperationSetting setting
	) {

		val target = Helper.createDummyAsTimezone( GeneralDate.ymd( 2021, 10, 31 ), Helper.createTimespan( 16,  0, 18,  0 ) );
		val dummies = Arrays.asList(
					Helper.createDummyAsTimezone( target.getPeriod().start(), Helper.createTimespan(  8,  0, 10,  0 ) )
				,	Helper.createDummyAsTimezone( target.getPeriod().start(), Helper.createTimespan( 12,  0, 14,  0 ) )
				,	Helper.createDummyAsTimezone( target.getPeriod().start(), Helper.createTimespan( 20,  0, 22,  0 ) )
			);

		new Expectations( setting ) {{
			require.getByPeriod( (EmployeeId)any, (DatePeriod)any );
			result = dummies;

			require.getSetting();
			result = setting;

			setting.getMaxNumberOfSupportOfDay();
			result = new MaximumNumberOfSupport( dummies.size() );
		}};


		// 実行
		val result = SupportableEmployeeCheckService.isRegistrable( require, target );


		// 検証
		assertThat( result ).isEqualTo( SupportableEmployeeCheckService.CheckResult.UPPER_LIMIT );

	}

	/**
	 * Target	: isRegistable
	 * Pattern	:
	 * 	- 応援形式	-> 時間帯応援
	 * 	- 既存データ-> 時間帯応援 && 時間帯が重複していない && 応援回数の上限に達していない
	 * Expected	: 登録不可(応援可能な回数が上限に達している)
	 */
	@Test
	public void test_isRegistable_timezone_supportCountIsUnderUpperLimit(
			@Injectable SupportOperationSetting setting
	) {

		val target = Helper.createDummyAsTimezone( GeneralDate.ymd( 2021, 10, 31 ), Helper.createTimespan( 16,  0, 18,  0 ) );
		val dummies = Arrays.asList(
					Helper.createDummyAsTimezone( target.getPeriod().start(), Helper.createTimespan(  8,  0, 10,  0 ) )
				,	Helper.createDummyAsTimezone( target.getPeriod().start(), Helper.createTimespan( 12,  0, 14,  0 ) )
				,	Helper.createDummyAsTimezone( target.getPeriod().start(), Helper.createTimespan( 20,  0, 22,  0 ) )
			);

		new Expectations( setting ) {{
			require.getByPeriod( (EmployeeId)any, (DatePeriod)any );
			result = dummies;

			require.getSetting();
			result = setting;

			setting.getMaxNumberOfSupportOfDay();
			result = new MaximumNumberOfSupport( dummies.size() + 1 );
		}};


		// 実行
		val result = SupportableEmployeeCheckService.isRegistrable( require, target );


		// 検証
		assertThat( result ).isEqualTo( SupportableEmployeeCheckService.CheckResult.REGISTABLE );

	}




	private static class Helper {

		public static SupportableEmployee createDummyAsAllday(DatePeriod period) {
			return SupportableEmployee.createAsAllday(
					new EmployeeId("dummy-emp-id")
				,	TargetOrgIdenInfor.creatIdentifiWorkplace("dummy-wkp-id")
				,	period
			);
		}

		public static SupportableEmployee createDummyAsTimezone(GeneralDate ymd, TimeSpanForCalc timespan) {
			return SupportableEmployee.createAsTimezone(
					new EmployeeId("dummy-emp-id")
				,	TargetOrgIdenInfor.creatIdentifiWorkplace("dummy-wkp-id")
				,	ymd
				,	timespan
			);
		}

		/**
		 * 計算時間帯を作成する
		 * @param startHour 開始(時)
		 * @param startMinute 開始(分)
		 * @param endHour 終了(時)
		 * @param endMinute 終了(分)
		 * @return 計算時間帯
		 */
		public static TimeSpanForCalc createTimespan( int startHour, int startMinute, int endHour, int endMinute ) {
			return new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute( startHour, startMinute )
						,	TimeWithDayAttr.hourMinute( endHour, endMinute )
					);
		}

	}

}
