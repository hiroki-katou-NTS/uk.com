package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.StrictExpectations;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ValueType;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.BreakoutFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodEnum;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.AgreementOneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthErrorAlarmTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.onemonth.OneMonthTime;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSettingForCalc;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTCalMed;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.OutsideOTBRDItem;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.ProductNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumExtra60HRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.holiday.PremiumRate;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.overtime.OvertimeNo;
import nts.uk.shr.com.context.AppContexts;

public class AgreementTimeOfManagePeriodTest {

	@Injectable
	private AgreementTimeOfManagePeriod.RequireM2 require;
	
	private String sid = "sid";
	private GeneralDate ymd = GeneralDate.today();
	private YearMonth ym = ymd.yearMonth();
	private String cid = "cid";

	private BasicAgreementSettingForCalc agreementSet = new BasicAgreementSettingForCalc(
			new BasicAgreementSetting(new AgreementOneMonth(
						OneMonthTime.of(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50 * 60), 
								new AgreementOneMonthTime(40* 60)), new AgreementOneMonthTime(60* 60)), 
						OneMonthTime.of(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(80* 60), 
								new AgreementOneMonthTime(75* 60)), new AgreementOneMonthTime(100* 60))), 
					null, null, null), false);

	private BasicAgreementSettingForCalc agreementSetWithEmpSet = new BasicAgreementSettingForCalc(
			new BasicAgreementSetting(new AgreementOneMonth(
						OneMonthTime.of(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(50 * 60), 
								new AgreementOneMonthTime(40* 60)), new AgreementOneMonthTime(60* 60)), 
						OneMonthTime.of(OneMonthErrorAlarmTime.of(new AgreementOneMonthTime(80* 60), 
								new AgreementOneMonthTime(75* 60)), new AgreementOneMonthTime(100* 60))), 
					null, null, null), true);
	
	List<OutsideOTBRDItem> osItems = Arrays.asList(new OutsideOTBRDItem(UseClassification.UseClass_Use, 
			BreakdownItemNo.ONE, null, ProductNumber.ONE, Arrays.asList(35),
			Arrays.asList(new PremiumExtra60HRate(new PremiumRate(1), OvertimeNo.ONE))));
	
	OutsideOTSetting otSet = new OutsideOTSetting(cid, null, osItems,
			OutsideOTCalMed.TIME_SERIES, new ArrayList<>(), Optional.empty());
	
	List<RoleOfOpenPeriod> holWorkRole = Arrays.asList(
			new RoleOfOpenPeriod(cid, new BreakoutFrameNo(1), RoleOfOpenPeriodEnum.STATUTORY_HOLIDAYS),
			new RoleOfOpenPeriod(cid, new BreakoutFrameNo(2), RoleOfOpenPeriodEnum.NON_STATUTORY_HOLIDAYS));
	
	@Mocked
	private MonthlyCalculation monthlyCalc;
	
	@Injectable
	private MonthlyRecordToAttendanceItemConverter converter;
	
    @Mocked
    AppContexts appContexts;

    @Before
    public void init() {
        new Expectations() {{
            AppContexts.user().companyId();
            result = cid;
        }};
    }
	
	@Test
	public void test1() {
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.empty();
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("時間外超過設定が取得できないイケース")
				.isEqualTo(AgreementTimeStatusOfMonthly.NORMAL);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test2() {
		int overTime = 1;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月アラームより小さい")
				.isEqualTo(AgreementTimeStatusOfMonthly.NORMAL);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test3() {
		int overTime = 42;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月アラーム以上エラーより小さい")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test4() {
		int overTime = 55;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月エラー以上上限より小さい")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test5() {
		int overTime = 70;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月上限以上特例アラームより小さい")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test6() {
		int overTime = 76;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月特例アラーム以上")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void test7() {
		int overTime = 82;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月特例エラー以上")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test8() {
		int overTime = 102;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月特例上限以上")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test9() {
		int overTime = 1;
		int holidayWork = 1;
		
		List<ItemValue> itemValues = Arrays.asList(
				ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60), /**　残業時間１*/
				ItemValue.builder().itemId(165).valueType(ValueType.TIME).value(holidayWork * 60), /**　法定内休出時間1*/
				ItemValue.builder().itemId(175).valueType(ValueType.TIME).value(0)); /**　法定内振替休出時間1*/
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = holWorkRole;
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在するケース　かつ　36協定1か月アラーム以下")
				.isEqualTo(AgreementTimeStatusOfMonthly.NORMAL);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test10() {
		int overTime = 1;
		int holidayWork = 41;
		List<ItemValue> itemValues = Arrays.asList(
				ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60), /**　残業時間１*/
				ItemValue.builder().itemId(165).valueType(ValueType.TIME).value(holidayWork * 60), /**　法定内休出時間1*/
				ItemValue.builder().itemId(175).valueType(ValueType.TIME).value(0)); /**　法定内振替休出時間1*/
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = holWorkRole;
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在するケース　かつ　36協定対象時間は小さい、法定上限時間が大きい（基本アラームより大きい）")
				.isEqualTo(AgreementTimeStatusOfMonthly.NORMAL);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test11() {
		int overTime = 1;
		int holidayWork = 54;

		List<ItemValue> itemValues = Arrays.asList(
				ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60), /**　残業時間１*/
				ItemValue.builder().itemId(165).valueType(ValueType.TIME).value(holidayWork * 60), /**　法定内休出時間1*/
				ItemValue.builder().itemId(175).valueType(ValueType.TIME).value(0)); /**　法定内振替休出時間1*/
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = holWorkRole;
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在するケース　かつ　36協定対象時間は小さい、法定上限時間が大きい（基本エラーより大きい）")
				.isEqualTo(AgreementTimeStatusOfMonthly.NORMAL);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test12() {
		int overTime = 1;
		int holidayWork = 69;

		List<ItemValue> itemValues = Arrays.asList(
				ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60), /**　残業時間１*/
				ItemValue.builder().itemId(165).valueType(ValueType.TIME).value(holidayWork * 60), /**　法定内休出時間1*/
				ItemValue.builder().itemId(175).valueType(ValueType.TIME).value(0)); /**　法定内振替休出時間1*/
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = holWorkRole;
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在するケース　かつ　36協定対象時間は小さい、法定上限時間が大きい（基本上限より大きい）")
				.isEqualTo(AgreementTimeStatusOfMonthly.NORMAL);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test13() {
		int overTime = 1;
		int holidayWork = 75;

		List<ItemValue> itemValues = Arrays.asList(
				ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60), /**　残業時間１*/
				ItemValue.builder().itemId(165).valueType(ValueType.TIME).value(holidayWork * 60), /**　法定内休出時間1*/
				ItemValue.builder().itemId(175).valueType(ValueType.TIME).value(0)); /**　法定内振替休出時間1*/
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = holWorkRole;
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在するケース　かつ　36協定1か月特例アラーム以上")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void test14() {
		int overTime = 1;
		int holidayWork = 81;

		List<ItemValue> itemValues = Arrays.asList(
				ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60), /**　残業時間１*/
				ItemValue.builder().itemId(165).valueType(ValueType.TIME).value(holidayWork * 60), /**　法定内休出時間1*/
				ItemValue.builder().itemId(175).valueType(ValueType.TIME).value(0)); /**　法定内振替休出時間1*/
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = holWorkRole;
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在するケース　かつ　36協定1か月特例エラー以上")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test15() {
		int overTime = 1;
		int holidayWork = 101;

		List<ItemValue> itemValues = Arrays.asList(
				ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60), /**　残業時間１*/
				ItemValue.builder().itemId(165).valueType(ValueType.TIME).value(holidayWork * 60), /**　法定内休出時間1*/
				ItemValue.builder().itemId(175).valueType(ValueType.TIME).value(0)); /**　法定内振替休出時間1*/
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = holWorkRole;
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在するケース　かつ　36協定1か月特例上限以上")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test16() {
		int overTime = 1;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSetWithEmpSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月アラーム以下　かつ　社員設定あり")
				.isEqualTo(AgreementTimeStatusOfMonthly.NORMAL_SPECIAL);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test17() {
		int overTime = 42;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSetWithEmpSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月アラーム以上エラー以下　かつ　社員設定あり")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ALARM_SP);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test18() {
		int overTime = 55;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSetWithEmpSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月エラー以上上限以下　かつ　社員設定あり")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test19() {
		int overTime = 70;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSetWithEmpSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月上限以上特例アラーム以下　かつ　社員設定あり")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test20() {
		int overTime = 76;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSetWithEmpSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月特例アラーム以上　かつ　社員設定あり")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	@SuppressWarnings("unchecked")
	@Test
	public void test21() {
		int overTime = 82;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSetWithEmpSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月特例エラー以上　かつ　社員設定あり")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void test22() {
		int overTime = 102;
		int holidayWork = 0;
		
		List<ItemValue> itemValues = Arrays.asList(ItemValue.builder().itemId(35).valueType(ValueType.TIME).value(overTime * 60));
		
		new StrictExpectations() {{
			require.outsideOTSetting(cid);
			result = Optional.of(otSet);
			
			require.monthRoundingSet(cid);
			result = Optional.empty();
			
			require.roleOfOpenPeriod(cid);
			result = new ArrayList<>();
			
			require.createMonthlyConverter();
			result = converter;
			
			converter.withAttendanceTime((AttendanceTimeOfMonthly) any);
			
			converter.convert((List<Integer>) any);
			result = itemValues;
			
			require.basicAgreementSetting(cid, sid, ym, ymd);
			result = agreementSetWithEmpSet;
		}};
		
		val result = AgreementTimeOfManagePeriod.aggregate(require, sid, ymd, ym, monthlyCalc);
		
		assertThat(result.getStatus()).as("法定休出が存在しないケース　かつ　36協定1か月特例上限以上　かつ　社員設定あり")
				.isEqualTo(AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY);
		
		assertThat(result.getBreakdown().calcAgreementTime().valueAsMinutes()).isEqualTo(overTime*60);
		
		assertThat(result.getBreakdown().calcLegalLimitTime().valueAsMinutes()).isEqualTo(overTime*60 + holidayWork*60);
	}
}
