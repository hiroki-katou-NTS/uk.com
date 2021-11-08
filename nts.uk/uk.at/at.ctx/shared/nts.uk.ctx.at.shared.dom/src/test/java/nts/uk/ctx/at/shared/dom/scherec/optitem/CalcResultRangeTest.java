package nts.uk.ctx.at.shared.dom.scherec.optitem;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.i18n.TextResource;
/**
 * 
 * @author tutk
 *
 */
@RunWith(JMockit.class)
public class CalcResultRangeTest {

	@Test
	public void getters() {
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.NOT_SET, CalcRangeCheck.NOT_SET, Optional.empty(), Optional.empty(),Optional.empty());
		NtsAssert.invokeGetters(calcResultRange);
	}
	
	/**
	 * @上限値チェック = 設定する && @下限値チェック = 設定しない
	 */
	@Test
	public void testCreateInputRangeErrorMsg_1(@Mocked final TextResource tr) {
		Optional<TimeRange> timeRange = Optional.empty();
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.NOT_SET, Optional.empty(), timeRange,Optional.empty());
		ControlRangeValue controlRangeValue = new ControlRangeValue(Optional.of(new BigDecimal(8.0)), Optional.empty());
		
		new Expectations() {
            {
            	TextResource.localize("Msg_2293","8.0");
            	result =  "8.0以下の値で入力してください。";
            }
        };
		
		String result = calcResultRange.createInputRangeErrorMsg(controlRangeValue);
		assertThat(result).isEqualTo(TextResource.localize("Msg_2293","8.0"));
	}
	
	/**
	 * @上限値チェック = 設定しない && @下限値チェック = 設定する
	 */
	@Test
	public void testCreateInputRangeErrorMsg_2(@Mocked final TextResource tr) {
		Optional<TimeRange> timeRange = Optional.empty();
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.NOT_SET, CalcRangeCheck.SET, Optional.empty(), timeRange,Optional.empty());
		ControlRangeValue controlRangeValue = new ControlRangeValue(Optional.empty(), Optional.of(new BigDecimal(4.0)));
		
		new Expectations() {
            {
            	TextResource.localize("Msg_2292","4.0");
            	result =  "4.0以上の値で入力してください。";
            }
        };
		
		String result = calcResultRange.createInputRangeErrorMsg(controlRangeValue);
		assertThat(result).isEqualTo(TextResource.localize("Msg_2292","4.0"));
	}
	
	/**
	 * @上限値チェック = 設定する && @下限値チェック = 設定する
	 */
	@Test
	public void testCreateInputRangeErrorMsg_3(@Mocked final TextResource tr) {
		Optional<TimeRange> timeRange = Optional.empty();
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.SET, Optional.empty(), timeRange,Optional.empty());
		ControlRangeValue controlRangeValue = new ControlRangeValue(Optional.of(new BigDecimal(8.0)), Optional.of(new BigDecimal(4.0)));
		
		new Expectations() {
            {
            	TextResource.localize("Msg_2291","4.0","8.0");
            	result =  "4.0～8.0の範囲以内で入力してください。";
            }
        };
		
		String result = calcResultRange.createInputRangeErrorMsg(controlRangeValue);
		assertThat(result).isEqualTo(TextResource.localize("Msg_2291","4.0","8.0"));
	}
	
	/**
	 * 時間範囲 is empty
	 * 任意項目の属性 == 時間
	 * 任意項目利用区分 == 日別実績
	 */
	@Test
	public void testGetUpperLimit_1() {
		Optional<TimeRange> timeRange = Optional.empty();
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.SET, Optional.empty(), timeRange,Optional.empty());
		PerformanceAtr performanceAtr = PerformanceAtr.DAILY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.TIME;
		ControlRangeValue result = calcResultRange.getUpperLimit(performanceAtr, optionalItemAtr);
		assertThat(result.getUpperLimit()).isEmpty();
		assertThat(result.getLowerLimit()).isEmpty();
	}
	
	/**
	 * 時間範囲 is empty
	 * 任意項目の属性 == 時間
	 * 任意項目利用区分 == 月別実績
	 */
	@Test
	public void testGetUpperLimit_2() {
		Optional<TimeRange> timeRange = Optional.empty();
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.SET, Optional.empty(), timeRange,Optional.empty());
		PerformanceAtr performanceAtr = PerformanceAtr.MONTHLY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.TIME;
		ControlRangeValue result = calcResultRange.getUpperLimit(performanceAtr, optionalItemAtr);
		assertThat(result.getUpperLimit()).isEmpty();
		assertThat(result.getLowerLimit()).isEmpty();
	}
	
	/**
	 * 時間範囲 not empty
	 * 任意項目の属性 == 時間
	 * 任意項目利用区分 == 日別実績
	 */
	@Test
	public void testGetUpperLimit_3() {
		DailyTimeRange dailyTimeRange = new DailyTimeRange(2000, 1000);
		MonthlyTimeRange monthlyTimeRange = new MonthlyTimeRange(3000, 1500);
		TimeRange timeRange = new TimeRange(Optional.of(dailyTimeRange), Optional.of(monthlyTimeRange));
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.SET, Optional.empty(), Optional.of(timeRange),Optional.empty());
		PerformanceAtr performanceAtr = PerformanceAtr.DAILY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.TIME;
		ControlRangeValue result = calcResultRange.getUpperLimit(performanceAtr, optionalItemAtr);
		assertThat(result.getUpperLimit().get()).isEqualTo(new BigDecimal(2000));
		assertThat(result.getLowerLimit().get()).isEqualTo(new BigDecimal(1000));
	}
	
	/**
	 * 時間範囲 not empty
	 * 任意項目の属性 == 時間
	 * 任意項目利用区分 == 月別実績
	 */
	@Test
	public void testGetUpperLimit_4() {
		DailyTimeRange dailyTimeRange = new DailyTimeRange(2000, 1000);
		MonthlyTimeRange monthlyTimeRange = new MonthlyTimeRange(3000, 1500);
		TimeRange timeRange = new TimeRange(Optional.of(dailyTimeRange), Optional.of(monthlyTimeRange));
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.SET, Optional.empty(), Optional.of(timeRange),Optional.empty());
		PerformanceAtr performanceAtr = PerformanceAtr.MONTHLY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.TIME;
		ControlRangeValue result = calcResultRange.getUpperLimit(performanceAtr, optionalItemAtr);
		assertThat(result.getUpperLimit().get()).isEqualTo(new BigDecimal(3000));
		assertThat(result.getLowerLimit().get()).isEqualTo(new BigDecimal(1500));
	}

	/**
	 * 時間範囲 not empty
	 * 任意項目の属性 == 回数
	 * 任意項目利用区分 == 日別実績
	 */
	@Test
	public void testGetUpperLimit_5() {
		DailyTimesRange dailyTimesRange = new DailyTimesRange(new BigDecimal(22.22),new BigDecimal(11.11));
		MonthlyTimesRange monthlyTimesRange = new MonthlyTimesRange(44.44,33.33);
		NumberRange numberRange = new NumberRange(Optional.of(dailyTimesRange), Optional.of(monthlyTimesRange));
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.SET, Optional.of(numberRange), Optional.empty(),Optional.empty());
		PerformanceAtr performanceAtr = PerformanceAtr.DAILY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.NUMBER;
		ControlRangeValue result = calcResultRange.getUpperLimit(performanceAtr, optionalItemAtr);
		assertThat(result.getUpperLimit().get()).isEqualTo(new BigDecimal(22.22));
		assertThat(result.getLowerLimit().get()).isEqualTo(new BigDecimal(11.11));
	}
	
	/**
	 * 時間範囲 not empty
	 * 任意項目の属性 == 回数
	 * 任意項目利用区分 == 月別実績
	 */
	@Test
	public void testGetUpperLimit_6() {
		DailyTimesRange dailyTimesRange = new DailyTimesRange(new BigDecimal(22.22),new BigDecimal(11.11));
		MonthlyTimesRange monthlyTimesRange = new MonthlyTimesRange(44.44,33.33);
		NumberRange numberRange = new NumberRange(Optional.of(dailyTimesRange), Optional.of(monthlyTimesRange));
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.SET, Optional.of(numberRange), Optional.empty(),Optional.empty());
		PerformanceAtr performanceAtr = PerformanceAtr.MONTHLY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.NUMBER;
		ControlRangeValue result = calcResultRange.getUpperLimit(performanceAtr, optionalItemAtr);
		assertThat(result.getUpperLimit().get()).isEqualTo(new BigDecimal("44.44"));
		assertThat(result.getLowerLimit().get()).isEqualTo(new BigDecimal("33.33"));
	}
	
	/**
	 * 時間範囲 not empty
	 * 任意項目の属性 == 金額
	 * 任意項目利用区分 == 日別実績
	 */
	@Test
	public void testGetUpperLimit_7() {
		DailyAmountRange dailyAmountRange = new DailyAmountRange(888,666);
		MonthlyAmountRange monthlyAmountRange = new MonthlyAmountRange(999,777);
		AmountRange amountRange = new AmountRange(Optional.of(dailyAmountRange), Optional.of(monthlyAmountRange));
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.SET, Optional.empty(), Optional.empty(),Optional.of(amountRange));
		PerformanceAtr performanceAtr = PerformanceAtr.DAILY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.AMOUNT;
		ControlRangeValue result = calcResultRange.getUpperLimit(performanceAtr, optionalItemAtr);
		assertThat(result.getUpperLimit().get()).isEqualTo(new BigDecimal(888));
		assertThat(result.getLowerLimit().get()).isEqualTo(new BigDecimal(666));
	}
	
	/**
	 * 時間範囲 not empty
	 * 任意項目の属性 == 金額
	 * 任意項目利用区分 == 月別実績
	 */
	@Test
	public void testGetUpperLimit_8() {
		DailyAmountRange dailyAmountRange = new DailyAmountRange(888,666);
		MonthlyAmountRange monthlyAmountRange = new MonthlyAmountRange(999,777);
		AmountRange amountRange = new AmountRange(Optional.of(dailyAmountRange), Optional.of(monthlyAmountRange));
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.SET, Optional.empty(), Optional.empty(),Optional.of(amountRange));
		PerformanceAtr performanceAtr = PerformanceAtr.MONTHLY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.AMOUNT;
		ControlRangeValue result = calcResultRange.getUpperLimit(performanceAtr, optionalItemAtr);
		assertThat(result.getUpperLimit().get()).isEqualTo(new BigDecimal(999));
		assertThat(result.getLowerLimit().get()).isEqualTo(new BigDecimal(777));
	}
	
	/**
	 * 上限値チェック = 設定しない  && 下限値チェック = 設定しない
	 */
	@Test
	public void testCheckInputRange_1() {
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.NOT_SET, CalcRangeCheck.NOT_SET, Optional.empty(), Optional.empty(),Optional.empty());
		PerformanceAtr performanceAtr = PerformanceAtr.DAILY_PERFORMANCE;//dummy
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.AMOUNT;//dummy
		ValueCheckResult result = calcResultRange.checkInputRange(new BigDecimal(9.0), performanceAtr, optionalItemAtr);
		assertThat(result.isCheckResult()).isTrue();
		assertThat(result.getErrorContent()).isEmpty();
	}
	
	/**
	 * 上限値チェック = 設定する  && 下限値チェック = 設定しない
	 * 実績区分 == 日別実績
	 * 任意項目の属性 == 金額
	 * 入力範囲チェック return true
	 */
	@Test
	public void testCheckInputRange_2() {
		DailyAmountRange dailyAmountRange = new DailyAmountRange(888,666);
		AmountRange amountRange = new AmountRange(Optional.of(dailyAmountRange), Optional.empty());
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.NOT_SET, Optional.empty(), Optional.empty(),Optional.of(amountRange));
		PerformanceAtr performanceAtr = PerformanceAtr.DAILY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.AMOUNT;
		BigDecimal inputValue = new BigDecimal(777);
		ValueCheckResult result = calcResultRange.checkInputRange(inputValue, performanceAtr, optionalItemAtr);
		assertThat(result.isCheckResult()).isTrue();
		assertThat(result.getErrorContent()).isEmpty();
	}
	
	/**
	 * 上限値チェック = 設定する  && 下限値チェック = 設定しない
	 * 実績区分 == 日別実績
	 * 任意項目の属性 == 金額
	 * 入力範囲チェック return false (msg_2293)
	 */
	@Test
	public void testCheckInputRange_3(@Mocked final TextResource tr) {
		DailyAmountRange dailyAmountRange = new DailyAmountRange(888,666);
		AmountRange amountRange = new AmountRange(Optional.of(dailyAmountRange), Optional.empty());
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.NOT_SET, Optional.empty(), Optional.empty(),Optional.of(amountRange));
		PerformanceAtr performanceAtr = PerformanceAtr.DAILY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.AMOUNT;
		BigDecimal inputValue = new BigDecimal(999);
		new Expectations() {
            {
            	TextResource.localize("Msg_2293","888.0");
            	result =  "888.0以下の値で入力してください。";
            }
        };
		ValueCheckResult result = calcResultRange.checkInputRange(inputValue, performanceAtr, optionalItemAtr);
		assertThat(result.isCheckResult()).isFalse();
		assertThat(result.getErrorContent().get()).isEqualTo(TextResource.localize("Msg_2293","888.0"));
	}
	
	/**
	 * 上限値チェック = 設定しない  && 下限値チェック = 設定する
	 * 実績区分 == 日別実績
	 * 任意項目の属性 == 金額
	 * 入力範囲チェック return true 
	 */
	@Test
	public void testCheckInputRange_4() {
		DailyAmountRange dailyAmountRange = new DailyAmountRange(888,666);
		AmountRange amountRange = new AmountRange(Optional.of(dailyAmountRange), Optional.empty());
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.NOT_SET, CalcRangeCheck.SET, Optional.empty(), Optional.empty(),Optional.of(amountRange));
		PerformanceAtr performanceAtr = PerformanceAtr.DAILY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.AMOUNT;
		BigDecimal inputValue = new BigDecimal(800);
		ValueCheckResult result = calcResultRange.checkInputRange(inputValue, performanceAtr, optionalItemAtr);
		assertThat(result.isCheckResult()).isTrue();
		assertThat(result.getErrorContent()).isEmpty();
	}
	
	/**
	 * 上限値チェック = 設定しない  && 下限値チェック = 設定する
	 * 実績区分 == 日別実績
	 * 任意項目の属性 == 金額
	 * 入力範囲チェック return false (msg_2292)
	 */
	@Test
	public void testCheckInputRange_5(@Mocked final TextResource tr) {
		DailyAmountRange dailyAmountRange = new DailyAmountRange(888,666);
		AmountRange amountRange = new AmountRange(Optional.of(dailyAmountRange), Optional.empty());
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.NOT_SET, CalcRangeCheck.SET, Optional.empty(), Optional.empty(),Optional.of(amountRange));
		PerformanceAtr performanceAtr = PerformanceAtr.DAILY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.AMOUNT;
		BigDecimal inputValue = new BigDecimal(900);
		new Expectations() {
            {
            	TextResource.localize("Msg_2292","666.0");
            	result =  "666.0以上の値で入力してください。";
            }
        };
		ValueCheckResult result = calcResultRange.checkInputRange(inputValue, performanceAtr, optionalItemAtr);
		assertThat(result.isCheckResult()).isFalse();
		assertThat(result.getErrorContent().get()).isEqualTo(TextResource.localize("Msg_2292","666.0"));
	}
	
	/**
	 * 上限値チェック = 設定する  && 下限値チェック = 設定する
	 * 実績区分 == 日別実績
	 * 任意項目の属性 == 金額
	 * 入力範囲チェック return true 
	 */
	@Test
	public void testCheckInputRange_6() {
		DailyAmountRange dailyAmountRange = new DailyAmountRange(888,666);
		AmountRange amountRange = new AmountRange(Optional.of(dailyAmountRange), Optional.empty());
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.SET, Optional.empty(), Optional.empty(),Optional.of(amountRange));
		PerformanceAtr performanceAtr = PerformanceAtr.DAILY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.AMOUNT;
		BigDecimal inputValue = new BigDecimal(696);
		ValueCheckResult result = calcResultRange.checkInputRange(inputValue, performanceAtr, optionalItemAtr);
		assertThat(result.isCheckResult()).isTrue();
		assertThat(result.getErrorContent()).isEmpty();
	}
	
	/**
	 * 上限値チェック = 設定する  && 下限値チェック = 設定する
	 * 実績区分 == 日別実績
	 * 任意項目の属性 == 金額
	 * 入力範囲チェック return false (msg_2291)
	 */
	@Test
	public void testCheckInputRange_7(@Mocked final TextResource tr) {
		DailyAmountRange dailyAmountRange = new DailyAmountRange(888,666);
		AmountRange amountRange = new AmountRange(Optional.of(dailyAmountRange), Optional.empty());
		CalcResultRange calcResultRange = new CalcResultRange(CalcRangeCheck.SET, CalcRangeCheck.SET, Optional.empty(), Optional.empty(),Optional.of(amountRange));
		PerformanceAtr performanceAtr = PerformanceAtr.DAILY_PERFORMANCE;
		OptionalItemAtr optionalItemAtr = OptionalItemAtr.AMOUNT;
		BigDecimal inputValue = new BigDecimal(1000);
		new Expectations() {
            {
            	TextResource.localize("Msg_2291","666.0","888.0");
            	result =  "666.0～888.0の範囲以内で入力してください。";
            }
        };
		ValueCheckResult result = calcResultRange.checkInputRange(inputValue, performanceAtr, optionalItemAtr);
		assertThat(result.isCheckResult()).isFalse();
		assertThat(result.getErrorContent().get()).isEqualTo(TextResource.localize("Msg_2291","666.0","888.0"));
	}
	
}
