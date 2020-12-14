package nts.uk.ctx.at.shared.dom.worktime.predset;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredTimeStgTestHelper.Timezone;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * Test for PrescribedTimezoneSetting
 * @author kumiko_otake
 */
public class PrescribedTimezoneSettingTest {

	@Test
	public void testGetters() {
		NtsAssert.invokeGetters(PredTimeStgTestHelper.PredTimeStg.createDummyPrscTzStg());
	}


	/**
	 * Target	: getEarlierTimezoneThanThreshold
	 * Pattern	: 時間帯.開始時刻＞基準時刻
	 * Output	: Optional.empty
	 */
	@Test
	public void testGetEarlierTimezoneThanThreshold_StartIsLaterThanThreshold() {

		// 時間帯
		val timezone = new TimezoneUse(
						TimeWithDayAttr.hourMinute( 10, 16 )
					,	TimeWithDayAttr.hourMinute( 12,  0 )
					,	UseSetting.USE
					,	1
				);
		// 基準時刻
		val threshold = TimeWithDayAttr.hourMinute( 10, 15 );

		// Execute
		@SuppressWarnings("unchecked")
		val result = (Optional<TimezoneUse>)NtsAssert.Invoke.privateMethod(
								PredTimeStgTestHelper.PredTimeStg.createDummyPrscTzStg()
							,	"getEarlierTimezoneThanThreshold"
							,	timezone, threshold
						);

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getEarlierTimezoneThanThreshold
	 * Pattern	: 時間帯.終了時刻＜基準時刻
	 * Output	: 時間帯
	 */
	@Test
	public void testGetEarlierTimezoneThanThreshold_EndIsEarlierThanThreshold() {

		// 時間帯
		val timezone = new TimezoneUse(
						TimeWithDayAttr.hourMinute( 14,  0 )
					,	TimeWithDayAttr.hourMinute( 21, 45 )
					,	UseSetting.USE
					,	1
				);
		// 基準時刻
		val threshold = TimeWithDayAttr.hourMinute( 21, 46 );

		// Execute
		@SuppressWarnings("unchecked")
		val result = (Optional<TimezoneUse>)NtsAssert.Invoke.privateMethod(
								PredTimeStgTestHelper.PredTimeStg.createDummyPrscTzStg()
							,	"getEarlierTimezoneThanThreshold"
							,	timezone, threshold
						);

		// Assertion
		assertThat( result ).isPresent();

		assertThat( result.get().getWorkNo() ).isEqualTo( timezone.getWorkNo() );
		assertThat( result.get().getUseAtr() ).isEqualTo( timezone.getUseAtr() );
		assertThat( result.get().getStart() ).isEqualTo( timezone.getStart() );
		assertThat( result.get().getEnd() ).isEqualTo( timezone.getEnd() );

	}

	/**
	 * Target	: getEarlierTimezoneThanThreshold
	 * Pattern	: 時間帯.開始時刻＜基準時刻＜時間帯.終了時刻
	 * Output	: 時間帯.開始時刻～基準時刻
	 */
	@Test
	public void testGetEarlierTimezoneThanThreshold_ThresholdIsBetweenStartAndEnd() {

		// 時間帯
		val timezone = new TimezoneUse(
						TimeWithDayAttr.hourMinute( 10,  0 )
					,	TimeWithDayAttr.hourMinute( 20,  0 )
					,	UseSetting.USE
					,	1
				);
		// 基準時刻
		val threshold = TimeWithDayAttr.hourMinute( 12, 31 );

		// Execute
		@SuppressWarnings("unchecked")
		val result = (Optional<TimezoneUse>)NtsAssert.Invoke.privateMethod(
								PredTimeStgTestHelper.PredTimeStg.createDummyPrscTzStg()
							,	"getEarlierTimezoneThanThreshold"
							,	timezone, threshold
						);

		// Assertion
		assertThat( result ).isPresent();

		assertThat( result.get().getWorkNo() ).isEqualTo( timezone.getWorkNo() );
		assertThat( result.get().getUseAtr() ).isEqualTo( timezone.getUseAtr() );
		assertThat( result.get().getStart() ).isEqualTo( timezone.getStart() );
		assertThat( result.get().getEnd() ).isEqualTo( threshold );

	}


	/**
	 * Target	: getLaterTimezoneThanThreshold
	 * Pattern	: 時間帯.終了時刻＜基準時刻
	 * Output	: Optional.empty
	 */
	@Test
	public void testGetLaterTimezoneThanThreshold_EndIsEarlierThanThreshold() {

		// 時間帯
		val timezone = new TimezoneUse(
						TimeWithDayAttr.hourMinute(  8, 58 )
					,	TimeWithDayAttr.hourMinute( 14, 13 )
					,	UseSetting.USE
					,	1
				);
		// 基準時刻
		val threshold = TimeWithDayAttr.hourMinute( 14, 14 );

		// Execute
		@SuppressWarnings("unchecked")
		val result = (Optional<TimezoneUse>)NtsAssert.Invoke.privateMethod(
								PredTimeStgTestHelper.PredTimeStg.createDummyPrscTzStg()
							,	"getLaterTimezoneThanThreshold"
							,	timezone, threshold
						);

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getLaterTimezoneThanThreshold
	 * Pattern	: 時間帯.開始時刻＞基準時刻
	 * Output	: 時間帯
	 */
	@Test
	public void testGetLaterTimezoneThanThreshold_EndIsLaterThanThreshold() {

		// 時間帯
		val timezone = new TimezoneUse(
						TimeWithDayAttr.hourMinute(  9, 22 )
					,	TimeWithDayAttr.hourMinute( 18,  4 )
					,	UseSetting.USE
					,	1
				);
		// 基準時刻
		val threshold = TimeWithDayAttr.hourMinute(  9, 21 );

		// Execute
		@SuppressWarnings("unchecked")
		val result = (Optional<TimezoneUse>)NtsAssert.Invoke.privateMethod(
								PredTimeStgTestHelper.PredTimeStg.createDummyPrscTzStg()
							,	"getLaterTimezoneThanThreshold"
							,	timezone, threshold
						);

		// Assertion
		assertThat( result ).isPresent();

		assertThat( result.get().getWorkNo() ).isEqualTo( timezone.getWorkNo() );
		assertThat( result.get().getUseAtr() ).isEqualTo( timezone.getUseAtr() );
		assertThat( result.get().getStart() ).isEqualTo( timezone.getStart() );
		assertThat( result.get().getEnd() ).isEqualTo( timezone.getEnd() );

	}

	/**
	 * Target	: getLaterTimezoneThanThreshold
	 * Pattern	: 時間帯.開始時刻＜基準時刻＜時間帯.終了時刻
	 * Output	: 基準時刻～時間帯.終了時刻
	 */
	@Test
	public void testGetLaterTimezoneThanThreshold_ThresholdIsBetweenStartAndEnd() {

		// 時間帯
		val timezone = new TimezoneUse(
						TimeWithDayAttr.hourMinute( 10,  0 )
					,	TimeWithDayAttr.hourMinute( 20,  0 )
					,	UseSetting.USE
					,	1
				);
		// 基準時刻
		val threshold = TimeWithDayAttr.hourMinute( 12, 29 );

		// Execute
		@SuppressWarnings("unchecked")
		val result = (Optional<TimezoneUse>)NtsAssert.Invoke.privateMethod(
								PredTimeStgTestHelper.PredTimeStg.createDummyPrscTzStg()
							,	"getLaterTimezoneThanThreshold"
							,	timezone, threshold
						);

		// Assertion
		assertThat( result ).isPresent();

		assertThat( result.get().getWorkNo() ).isEqualTo( timezone.getWorkNo() );
		assertThat( result.get().getUseAtr() ).isEqualTo( timezone.getUseAtr() );
		assertThat( result.get().getStart() ).isEqualTo( threshold );
		assertThat( result.get().getEnd() ).isEqualTo( timezone.getEnd() );

	}


	/**
	 * Target	: getUseableTimeZone
	 * Pattern	: 1勤目：使用しない / 2勤目：使用しない
	 * Output	: List.empty
	 */
	@Test
	public void testGetUseableTimeZone_1stAnd2ndAreUnused() {

		// 時間帯
		val timezones = Arrays.asList(
				Timezone.createUnused(1)
			,	Timezone.createUnused(2)
		);

		// Execute
		val result = new PrescribedTimezoneSetting(
								TimeWithDayAttr.hourMinute( 12,  0 )
							,	TimeWithDayAttr.hourMinute( 13,  0 )
							,	timezones
						).getUseableTimeZone();

		// Assertion
		assertThat( result ).isEmpty();

	}

	/**
	 * Target	: getUseableTimeZone
	 * Pattern	: 1勤目：使用する / 2勤目：使用しない
	 * Output	: 1勤目のみ
	 */
	@Test
	public void testGetUseableTimeZone_Only1stIsUsed() {

		// 時間帯
		val timezones = Arrays.asList(
				Timezone.createUsed(1, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  5,  0 ), TimeWithDayAttr.hourMinute( 23, 30 )))
			,	Timezone.createUnused(2)
		);

		// Execute
		val result = new PrescribedTimezoneSetting(
								TimeWithDayAttr.hourMinute( 12,  0 )
							,	TimeWithDayAttr.hourMinute( 13,  0 )
							,	timezones
						).getUseableTimeZone();

		// Assertion
		assertThat( result ).hasSize( 1 );

		assertThat( result.get(0).getWorkNo() ).isEqualTo( timezones.get(0).getWorkNo() );
		assertThat( result.get(0).getUseAtr() ).isEqualTo( timezones.get(0).getUseAtr() );
		assertThat( result.get(0).getStart() ).isEqualTo( timezones.get(0).getStart() );
		assertThat( result.get(0).getEnd() ).isEqualTo( timezones.get(0).getEnd() );

	}

	/**
	 * Target	: getUseableTimeZone
	 * Pattern	: 1勤目：使用しない / 2勤目：使用する
	 * Output	: 2勤目のみ
	 */
	@Test
	public void testGetUseableTimeZone_Only2ndIsUsed() {

		// 時間帯
		val timezones = Arrays.asList(
				Timezone.createUnused(1)
			,	Timezone.createUsed(2, new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 10, 30 ), TimeWithDayAttr.hourMinute( 25,  0 )))
		);

		// Execute
		val result = new PrescribedTimezoneSetting(
								TimeWithDayAttr.hourMinute( 12,  0 )
							,	TimeWithDayAttr.hourMinute( 13,  0 )
							,	timezones
						).getUseableTimeZone();

		// Assertion
		assertThat( result ).hasSize( 1 );

		assertThat( result.get(0).getWorkNo() ).isEqualTo( timezones.get(1).getWorkNo() );
		assertThat( result.get(0).getUseAtr() ).isEqualTo( timezones.get(1).getUseAtr() );
		assertThat( result.get(0).getStart() ).isEqualTo( timezones.get(1).getStart() );
		assertThat( result.get(0).getEnd() ).isEqualTo( timezones.get(1).getEnd() );

	}

	/**
	 * Target	: getUseableTimeZone
	 * Pattern	: 1勤目：使用する / 2勤目：使用する
	 * Output	: [0] 1勤目, [1] 2勤目
	 */
	@Test
	public void testGetUseableTimeZone_1stAnd2ndAreUsed() {

		// 時間帯 ※1件目＝2勤目, 2件目＝1勤目
		val timezones = Arrays.asList(
				Timezone.createUsed(2, new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 18,  0 ), TimeWithDayAttr.hourMinute( 25,  0 )))
			,	Timezone.createUsed(1, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  8, 30 ), TimeWithDayAttr.hourMinute( 15, 30 )))
		);

		// Execute
		val result = new PrescribedTimezoneSetting(
								TimeWithDayAttr.hourMinute( 12,  0 )
							,	TimeWithDayAttr.hourMinute( 13,  0 )
							,	timezones
						).getUseableTimeZone();

		// Assertion
		assertThat( result ).hasSameSizeAs( timezones );

		// 1件目＝1勤目
		{
			val actual = result.get(0);
			val excepted = timezones.get(1);
			assertThat( actual.getWorkNo() ).isEqualTo( excepted.getWorkNo() );
			assertThat( actual.getUseAtr() ).isEqualTo( excepted.getUseAtr() );
			assertThat( actual.getStart() ).isEqualTo( excepted.getStart() );
			assertThat( actual.getEnd() ).isEqualTo( excepted.getEnd() );
		}

		// 2件目＝2勤目
		{
			val actual = result.get(1);
			val excepted = timezones.get(0);
			assertThat( actual.getWorkNo() ).isEqualTo( excepted.getWorkNo() );
			assertThat( actual.getUseAtr() ).isEqualTo( excepted.getUseAtr() );
			assertThat( actual.getStart() ).isEqualTo( excepted.getStart() );
			assertThat( actual.getEnd() ).isEqualTo( excepted.getEnd() );
		}

	}


	/**
	 * Target	: getUseableTimeZoneInAm
	 * Pattern	: 午前終了時刻が1勤目の間
	 * Output	: [0] 1勤目 開始～午前終了時刻
	 */
	@Test
	public void testGetUseableTimeZoneInAm_EndOfAmIsBetween1st() {

		// 午前終了時刻
		val endOfAm = TimeWithDayAttr.hourMinute( 13,  0 );
		// 時間帯
		val timezones = Arrays.asList(
				Timezone.createUsed(1, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  8,  0 ), TimeWithDayAttr.hourMinute( 14, 45 )))
			,	Timezone.createUsed(2, new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 15, 30 ), TimeWithDayAttr.hourMinute( 23, 15 )))
		);

		// Execute
		val result = new PrescribedTimezoneSetting(endOfAm, endOfAm.forwardByHours(1), timezones)
						.getUseableTimeZoneInAm();

		// Assertion
		assertThat( result ).hasSize( 1 );

		assertThat( result.get(0).getWorkNo() ).isEqualTo( timezones.get(0).getWorkNo() );
		assertThat( result.get(0).getUseAtr() ).isEqualTo( timezones.get(0).getUseAtr() );
		assertThat( result.get(0).getStart() ).isEqualTo( timezones.get(0).getStart() );
		assertThat( result.get(0).getEnd() ).isEqualTo( endOfAm );

	}

	/**
	 * Target	: getUseableTimeZoneInAm
	 * Pattern	: 午前終了時刻が2勤目の間
	 * Output	: [0] 1勤目 開始～終了, [1] 2勤目 開始～午前終了時刻
	 */
	@Test
	public void testGetUseableTimeZoneInAm_EndOfAmIsBetween2nd() {

		// 午前終了時刻
		val endOfAm = TimeWithDayAttr.hourMinute( 15,  0 );
		// 時間帯
		val timezones = Arrays.asList(
				Timezone.createUsed(1, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  5,  0 ), TimeWithDayAttr.hourMinute( 12, 15 )))
			,	Timezone.createUsed(2, new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 12, 45 ), TimeWithDayAttr.hourMinute( 22,  0 )))
		);

		// Execute
		val result = new PrescribedTimezoneSetting(endOfAm, endOfAm.forwardByHours(1), timezones)
						.getUseableTimeZoneInAm();

		// Assertion
		assertThat( result ).hasSize( 2 );

		// 1勤目
		{
			val actual = result.get(0);
			val excepted = timezones.get(0);
			assertThat( actual.getWorkNo() ).isEqualTo( excepted.getWorkNo() );
			assertThat( actual.getUseAtr() ).isEqualTo( excepted.getUseAtr() );
			assertThat( actual.getStart() ).isEqualTo( excepted.getStart() );
			assertThat( actual.getEnd() ).isEqualTo( excepted.getEnd() );
		}

		// 2勤目
		{
			val actual = result.get(1);
			val excepted = timezones.get(1);
			assertThat( actual.getWorkNo() ).isEqualTo( excepted.getWorkNo() );
			assertThat( actual.getUseAtr() ).isEqualTo( excepted.getUseAtr() );
			assertThat( actual.getStart() ).isEqualTo( excepted.getStart() );
			assertThat( actual.getEnd() ).isEqualTo( endOfAm );
		}

	}


	/**
	 * Target	: getUseableTimeZoneInPm
	 * Pattern	: 午後開始時刻が1勤目の間
	 * Output	: [0] 午後開始時刻～1勤目 終了, [1] 2勤目 開始～終了
	 */
	@Test
	public void testGetUseableTimeZoneInAm_StartOfPmIsBetween1st() {

		// 午後開始時刻
		val endOfPm = TimeWithDayAttr.hourMinute( 11, 30 );
		// 時間帯
		val timezones = Arrays.asList(
				Timezone.createUsed(1, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  5,  0 ), TimeWithDayAttr.hourMinute( 12, 15 )))
			,	Timezone.createUsed(2, new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 12, 45 ), TimeWithDayAttr.hourMinute( 22,  0 )))
		);

		// Execute
		val result = new PrescribedTimezoneSetting(endOfPm.backByHours(1), endOfPm, timezones)
						.getUseableTimeZoneInPm();

		// Assertion
		assertThat( result ).hasSize( 2 );

		// 1勤目
		{
			val actual = result.get(0);
			val excepted = timezones.get(0);
			assertThat( actual.getWorkNo() ).isEqualTo( excepted.getWorkNo() );
			assertThat( actual.getUseAtr() ).isEqualTo( excepted.getUseAtr() );
			assertThat( actual.getStart() ).isEqualTo( endOfPm );
			assertThat( actual.getEnd() ).isEqualTo( excepted.getEnd() );
		}

		// 2勤目
		{
			val actual = result.get(1);
			val excepted = timezones.get(1);
			assertThat( actual.getWorkNo() ).isEqualTo( excepted.getWorkNo() );
			assertThat( actual.getUseAtr() ).isEqualTo( excepted.getUseAtr() );
			assertThat( actual.getStart() ).isEqualTo( excepted.getStart() );
			assertThat( actual.getEnd() ).isEqualTo( excepted.getEnd() );
		}

	}

	/**
	 * Target	: getUseableTimeZoneInPm
	 * Pattern	: 午後開始時刻が2勤目の間
	 * Output	: [0] 午後開始時刻～2勤目 終了
	 */
	@Test
	public void testGetUseableTimeZoneInPm_StartOfPmIsBetween2nd() {

		// 午後開始時刻
		val endOfPm = TimeWithDayAttr.hourMinute( 13, 15 );
		// 時間帯
		val timezones = Arrays.asList(
				Timezone.createUsed(1, new TimeSpanForCalc(TimeWithDayAttr.hourMinute(  5,  0 ), TimeWithDayAttr.hourMinute( 12,  0 )))
			,	Timezone.createUsed(2, new TimeSpanForCalc(TimeWithDayAttr.hourMinute( 13,  0 ), TimeWithDayAttr.hourMinute( 22,  0 )))
		);

		// Execute
		val result = new PrescribedTimezoneSetting(endOfPm.backByHours(1), endOfPm, timezones)
						.getUseableTimeZoneInPm();

		// Assertion
		assertThat( result ).hasSize( 1 );

		assertThat( result.get(0).getWorkNo() ).isEqualTo( timezones.get(1).getWorkNo() );
		assertThat( result.get(0).getUseAtr() ).isEqualTo( timezones.get(1).getUseAtr() );
		assertThat( result.get(0).getStart() ).isEqualTo( endOfPm );
		assertThat( result.get(0).getEnd() ).isEqualTo( timezones.get(1).getEnd() );

	}

}
