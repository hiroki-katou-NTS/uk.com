package nts.uk.ctx.at.shared.dom.worktime.common;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.common.timerounding.Rounding;
import nts.uk.ctx.at.shared.dom.common.timerounding.TimeRoundingSetting;
import nts.uk.ctx.at.shared.dom.common.timerounding.Unit;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * Test for FixedWorkTimezoneSet
 * @author kumiko_otake
 */
public class FixedWorkTimezoneSetTest {

	@Test
	public void testGetters() {
		val instance = Helper.createInstance(Helper.createDummyEmTzSetList(), Helper.createDummyOtTzSetList());
		NtsAssert.invokeGetters(instance);
	}


	/**
	 * Target	: getWorkingTimezonesForCalc
	 */
	@Test
	public void testGetWorkingTimezonesForCalc() {

		// 就業時間の時間帯リスト
		val timezones = Arrays.asList(
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 18,  0 ), TimeWithDayAttr.hourMinute( 23,  0 ))
			,	new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  5,  0 ), TimeWithDayAttr.hourMinute( 15,  0 ))
		);

		// Execute
		val result = Helper.createInstance(Helper.createEmTzSetList(timezones), Helper.createDummyOtTzSetList())
						.getWorkingTimezonesForCalc();

		// Assertion
		assertThat( result ).containsExactlyElementsOf( timezones );

	}

	/**
	 * Target	: getFirstAndLastTimeOfWorkingTimezone
	 */
	@Test
	public void testGetFirstAndLastTimeOfWorkingTimezone() {

		// 就業時間の時間帯リスト
		val timezones = Arrays.asList(
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 18,  0 ), TimeWithDayAttr.hourMinute( 23, 30 ))
			,	new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  8, 30 ), TimeWithDayAttr.hourMinute( 15,  0 ))
		);

		// Execute
		val result = Helper.createInstance(Helper.createEmTzSetList(timezones), Helper.createDummyOtTzSetList())
						.getFirstAndLastTimeOfWorkingTimezone();

		// Assertion
		assertThat( result.getStart() )
			.isEqualTo( timezones.stream().min(Comparator.comparing(TimeSpanForCalc::getStart)).get().getStart() );
		assertThat( result.getEnd() )
			.isEqualTo( timezones.stream().max(Comparator.comparing(TimeSpanForCalc::getEnd)).get().getEnd() );

	}


	/**
	 * Target	: getOvertimeWorkingTimezonesForCalc
	 */
	@Test
	public void testGetOvertimeWorkingTimezonesForCalc() {

		// 残業時間の時間帯リスト
		val timezones = Arrays.asList(
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 23, 30 ), TimeWithDayAttr.hourMinute( 26,  0 ))
			,	new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  5,  0 ), TimeWithDayAttr.hourMinute(  8, 30 ))
		);

		// Execute
		val result = Helper.createInstance(Helper.createDummyEmTzSetList(), Helper.createOtTzSetList(timezones))
						.getOvertimeWorkingTimezonesForCalc();

		// Assertion
		assertThat( result ).containsExactlyElementsOf( timezones );

	}


	/**
	 * Target	: getFirstAndLastTimeOfOvertimeWorkingTimezone
	 * Pattern	: List is empty
	 */
	@Test
	public void testGetFirstAndLastTimeOfOvertimeWorkingTimezone_ListIsEmpty() {

		// Execute
		val result = Helper.createInstance(Helper.createDummyEmTzSetList(), Collections.emptyList())
						.getFirstAndLastTimeOfOvertimeWorkingTimezone();

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getFirstAndLastTimeOfOvertimeWorkingTimezone
	 * Pattern	: List is present
	 */
	@Test
	public void testGetFirstAndLastTimeOfOvertimeWorkingTimezone_ListIsPresent() {

		// 残業時間の時間帯リスト
		val timezones = Arrays.asList(
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 23, 30 ), TimeWithDayAttr.hourMinute( 26,  0 ))
			,	new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  5,  0 ), TimeWithDayAttr.hourMinute(  8, 30 ))
		);

		// Execute
		val result = Helper.createInstance(Helper.createDummyEmTzSetList(), Helper.createOtTzSetList(timezones))
						.getFirstAndLastTimeOfOvertimeWorkingTimezone();

		// Assertion
		assertThat( result ).isPresent();

		assertThat( result.get().getStart() )
			.isEqualTo( timezones.stream().min(Comparator.comparing(TimeSpanForCalc::getStart)).get().getStart() );
		assertThat( result.get().getEnd() )
			.isEqualTo( timezones.stream().max(Comparator.comparing(TimeSpanForCalc::getEnd)).get().getEnd() );

	}


	protected static class Helper {

		/**
		 * 固定勤務時間帯設定を作成する
		 * @param workingTimezones 就業時間の時間帯設定リスト
		 * @param overtimeTimezones 残業時間の時間帯設定リスト
		 * @return 固定勤務時間帯設定
		 */
		public static FixedWorkTimezoneSet createInstance(List<EmTimeZoneSet> workingTimezones, List<OverTimeOfTimeZoneSet> overtimeTimezones) {
			val instance = new FixedWorkTimezoneSet();
			{
				instance.setLstWorkingTimezone(workingTimezones);
				instance.setLstOTTimezone(overtimeTimezones);
			}
			return instance;
		}


		/**
		 * 就業時間の時間帯設定を作成する
		 * @param frameNo 就業時間枠NO
		 * @param timespan 時間帯
		 * @return
		 */
		public static EmTimeZoneSet createEmTzSet(int frameNo, TimeSpanForCalc timespan) {
			return new EmTimeZoneSet(new EmTimeFrameNo(frameNo), Helper.createTimezoneRounding(timespan));
		}

		/**
		 * 就業時間の時間帯設定リストを作成する
		 * @param timespans 時間帯リスト
		 * @return 就業時間の時間帯設定リスト
		 */
		public static List<EmTimeZoneSet> createEmTzSetList(List<TimeSpanForCalc> timespans) {
			return IntStream.rangeClosed( 1, timespans.size() ).boxed()
						.map( num -> Helper.createEmTzSet(num, timespans.get(num - 1)) )
						.collect(Collectors.toList());
		}

		/**
		 * 就業時間の時間帯設定リスト(DUMMY)を作成する
		 * @return 就業時間の時間帯設定リスト(DUMMY)
		 */
		public static List<EmTimeZoneSet> createDummyEmTzSetList() {
			return Helper.createEmTzSetList(Arrays.asList(
								new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 12,  0 ), TimeWithDayAttr.hourMinute( 12, 45 ))
							,	new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 17, 30 ), TimeWithDayAttr.hourMinute( 18,  0 ))
						));
		}


		/**
		 * 残業時間の時間帯設定を作成する
		 * @param workTimezoneNo 就業時間帯NO
		 * @param timespan 時間帯
		 * @return
		 */
		public static OverTimeOfTimeZoneSet createOtTzSet(int workTimezoneNo, TimeSpanForCalc timespan) {
			val instance = new OverTimeOfTimeZoneSet();
			{
				// 就業時間帯NO
				instance.setWorkTimezoneNo(new EmTimezoneNo(workTimezoneNo));
				// 時間帯
				instance.setTimezone(Helper.createTimezoneRounding(timespan));

				instance.setRestraintTimeUse(true);						// 拘束時間として扱う
				instance.setEarlyOTUse(false);							// 早出残業として扱う
				instance.setOtFrameNo(new OTFrameNo(1));				// 残業枠NO
				instance.setLegalOTframeNo(new OTFrameNo(2));			// 法定内残業枠NO
				instance.setSettlementOrder(new SettlementOrder(1));	// 清算順序
			}
			return instance;
		}

		/**
		 * 残業時間の時間帯設定リストを作成する
		 * @param timespans 時間帯リスト
		 * @return 残業時間の時間帯設定リスト
		 */
		public static List<OverTimeOfTimeZoneSet> createOtTzSetList(List<TimeSpanForCalc> timespans) {
			return IntStream.rangeClosed( 1, timespans.size() ).boxed()
						.map( num -> Helper.createOtTzSet(num, timespans.get(num - 1)) )
						.collect(Collectors.toList());
		}

		/**
		 * 残業時間の時間帯設定リスト(DUMMY)を作成する
		 * @return
		 */
		public static List<OverTimeOfTimeZoneSet> createDummyOtTzSetList() {
			return Helper.createOtTzSetList(Arrays.asList(
								new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  5,  0 ), TimeWithDayAttr.hourMinute(  8, 30 ))
							,	new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 18,  0 ), TimeWithDayAttr.hourMinute( 22,  0 ))
							,	new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 22,  0 ), TimeWithDayAttr.hourMinute( 26,  0 ))
						));
		}


		/**
		 * 時間帯(丸め付き)を作成する
		 * @param timespan 時間帯
		 * @return 時間帯(丸め付き)
		 */
		public static TimeZoneRounding createTimezoneRounding(TimeSpanForCalc timespan) {
			return new TimeZoneRounding(
						timespan.getStart(), timespan.getEnd()
					,	new TimeRoundingSetting(Unit.ROUNDING_TIME_1MIN, Rounding.ROUNDING_UP)
				);
		}
	}

}
