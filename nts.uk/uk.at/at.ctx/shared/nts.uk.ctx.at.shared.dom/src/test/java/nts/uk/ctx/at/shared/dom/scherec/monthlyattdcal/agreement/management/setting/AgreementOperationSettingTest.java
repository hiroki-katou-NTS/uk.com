package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AggregatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.StartingMonthType;
import nts.uk.shr.com.time.calendar.date.ClosureDate;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AgreementOperationSettingTest {

	@Test
	public void getters() {

		AgreementOperationSetting agreementOperationSetting =
				new AgreementOperationSetting("cid",StartingMonthType.FEBRUARY, new ClosureDate(1,false),true,true);
		NtsAssert.invokeGetters(agreementOperationSetting);
	}

	@Test
	public void getAggrPeriodTest_1() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid", StartingMonthType.MARCH, new ClosureDate(30,true),true,true);

		DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(2020, 9, 1), GeneralDate.ymd(2020, 9, 30));
		AggregatePeriod result = target.getAggregatePeriod(datePeriod);

		assertThat(result.getPeriod()).isEqualTo(datePeriod);
		assertThat(result.getYearMonth()).isEqualTo(new YearMonth(202009));
		assertThat(result.getYear()).isEqualTo(new Year(2020));
	}

	@Test
	public void getAggrPeriodTest_2() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.MARCH, new ClosureDate(15,false),true,true);

		DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(2020, 9, 1), GeneralDate.ymd(2020, 9, 20));
		AggregatePeriod result = target.getAggregatePeriod(datePeriod);

		assertThat(result.getPeriod()).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 8, 17), GeneralDate.ymd(2020, 9, 16)));
		assertThat(result.getYearMonth()).isEqualTo(new YearMonth(202009));
		assertThat(result.getYear()).isEqualTo(new Year(2020));
	}

	@Test
	public void getAggrPeriodTest_3() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.MARCH, new ClosureDate(29,false),true,true);

		DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(2020, 9, 1), GeneralDate.ymd(2020, 9, 10));
		AggregatePeriod result = target.getAggregatePeriod(datePeriod);

		assertThat(result.getPeriod()).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 8, 31), GeneralDate.ymd(2020, 9, 30)));
		assertThat(result.getYearMonth()).isEqualTo(new YearMonth(202009));
		assertThat(result.getYear()).isEqualTo(new Year(2020));
	}

	@Test
	public void getAggrPeriodTest_4() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.MARCH, new ClosureDate(29,false),true,true);

		DatePeriod datePeriod = new DatePeriod(GeneralDate.ymd(2020, 1, 1), GeneralDate.ymd(2020, 1, 10));
		AggregatePeriod result = target.getAggregatePeriod(datePeriod);

		assertThat(result.getPeriod()).isEqualTo(new DatePeriod(GeneralDate.ymd(2019, 12, 31), GeneralDate.ymd(2020, 1, 30)));
		assertThat(result.getYearMonth()).isEqualTo(new YearMonth(202001));
		assertThat(result.getYear()).isEqualTo(new Year(2019));
	}

	@Test
	public void getPeriodByYearMonthTest_1() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.MARCH, new ClosureDate(30,true),true,true);

		YearMonth yearMonth = new YearMonth(202009);
		DatePeriod result = target.getAggregatePeriodByYearMonth(yearMonth);

		assertThat(result).isEqualTo(new DatePeriod(GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1),
				yearMonth.lastGeneralDate()));
	}

	@Test
	public void getPeriodByYearMonthTest_2() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.MARCH, new ClosureDate(30,true),true,true);

		YearMonth yearMonth = new YearMonth(202009);
		DatePeriod result = target.getAggregatePeriodByYearMonth(yearMonth);

		assertThat(result).isEqualTo(new DatePeriod(GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1),
				yearMonth.lastGeneralDate()));
	}

	@Test
	public void getYearMonthOfAgrPeriodTest_1() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.MARCH, new ClosureDate(30,true),true,true);

		YearMonth result = target.getYearMonthOfAgreementPeriod(new YearMonth(202001));

		assertThat(result).isEqualTo(new YearMonth(201901));
	}

	@Test
	public void getYearMonthOfAgrPeriodTest_2() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.MARCH, new ClosureDate(30,true),true,true);

		YearMonth result = target.getYearMonthOfAgreementPeriod(new YearMonth(202009));

		assertThat(result).isEqualTo(new YearMonth(202009));
	}

	@Test
	public void getPeriodByYMPeriodTest_1() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.MARCH, new ClosureDate(30,true),true,true);

		DatePeriod result = target.getAgreementPeriodByYMPeriod(new YearMonthPeriod(new YearMonth(202001), new YearMonth(202005)));

		assertThat(result).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 1 ,1), GeneralDate.ymd(2020, 5, 31)));
	}

	@Test
	public void getPeriodByYMPeriodTest_2() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.MARCH, new ClosureDate(25,false),true,true);

		DatePeriod result = target.getAgreementPeriodByYMPeriod(new YearMonthPeriod(new YearMonth(202003), new YearMonth(202005)));

		assertThat(result).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 2 ,27), GeneralDate.ymd(2020, 5, 26)));
	}

	@Test
	public void getYearMonthPeriodTest_1() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.JANUARY, new ClosureDate(31,true),true,true);

		YearMonthPeriod result = target.getYearMonthPeriod(new Year(2020));

		assertThat(result).isEqualTo(new YearMonthPeriod(new YearMonth(202002), new YearMonth(202101)));
	}

	@Test
	public void getYearMonthPeriodTest_2() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.MARCH, new ClosureDate(25,false),true,true);

		YearMonthPeriod result = target.getYearMonthPeriod(new Year(2020));

		assertThat(result).isEqualTo(new YearMonthPeriod(new YearMonth(202004), new YearMonth(202103)));
	}

	@Test
	public void getPeriodFromYearTest_1() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid", StartingMonthType.JANUARY, new ClosureDate(31,true),true,true);

		DatePeriod result = target.getPeriodFromYear(new Year(2020));

		assertThat(result).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 2 ,29), GeneralDate.ymd(2021, 1, 29)));
	}

	@Test
	public void getPeriodFromYearTest_2() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.MARCH, new ClosureDate(25,false),true,true);

		DatePeriod result = target.getPeriodFromYear(new Year(2020));

		assertThat(result).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 4 ,25), GeneralDate.ymd(2021, 3, 25)));
	}

	@Test
	public void getPeriodYearTest_1() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.MARCH, new ClosureDate(25,false),true,true);

		YearMonthPeriod result = target.getPeriodYear(GeneralDate.ymd(2020, 2, 25));

		assertThat(result).isEqualTo(new YearMonthPeriod(new YearMonth(201904), new YearMonth(202003)));
	}

//	@Test
//	public void getPeriodYearTest_2() {
//
//		AgreementOperationSetting target =
//				new AgreementOperationSetting("cid",StartingMonthType.APRIL, new ClosureDate(1,true),true,true);
//
//		// Mock up
//		val setting = new DatePeriod(GeneralDate.ymd(2020, 3 ,25), GeneralDate.ymd(2021, 2, 25));
//		new MockUp<AgreementOperationSetting>() {
//			@Mock
//			public DatePeriod getAgreementPeriodByYMPeriod(YearMonthPeriod yearMonthPeriod) {
//				return setting;
//			}
//		};
//
//		YearMonthPeriod result = target.getPeriodYear(GeneralDate.ymd(2020, 2, 25));
//
//		assertThat(result).isEqualTo(new YearMonthPeriod(new YearMonth(202001), new YearMonth(202012)));
//	}

	@Test
	public void getPeriodYearTest_3() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.JANUARY, new ClosureDate(1,false),true,true);

		YearMonthPeriod result = target.getPeriodYear(GeneralDate.ymd(2019, 12, 25));

		assertThat(result).isEqualTo(new YearMonthPeriod(new YearMonth(201902), new YearMonth(202001)));
	}

	@Test
	public void getAgrTargetDayTest_1() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.JANUARY, new ClosureDate(31,true),true,true);

		YearMonth result = target.getAgreementYMBytargetDay(GeneralDate.ymd(2019, 12, 25));

		assertThat(result).isEqualTo(new YearMonth(201912));
	}

	@Test
	public void getAgrTargetDayTest_2() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.JANUARY, new ClosureDate(20,false),true,true);

		YearMonth result = target.getAgreementYMBytargetDay(GeneralDate.ymd(2019, 12, 25));

		assertThat(result).isEqualTo(new YearMonth(202001));
	}

	@Test
	public void getAgrTargetDayTest_3() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.JANUARY, new ClosureDate(20,false),true,true);

		YearMonth result = target.getAgreementYMBytargetDay(GeneralDate.ymd(2019, 12, 15));

		assertThat(result).isEqualTo(new YearMonth(201912));
	}

	@Test
	public void getYearTest_1() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.APRIL, new ClosureDate(20,false),true,true);

		Year result = target.getYear(new YearMonth(202001));

		assertThat(result).isEqualTo(new Year(2019));
	}

	@Test
	public void getYearTest_2() {

		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.APRIL, new ClosureDate(20,false),true,true);

		Year result = target.getYear(new YearMonth(202007));

		assertThat(result).isEqualTo(new Year(2020));
	}

	@Test
	public void setYearTest_1() {

		AgreementTimeOfManagePeriod agreementTime = new AgreementTimeOfManagePeriod("sid",new YearMonth(202001));
		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.APRIL, new ClosureDate(20,false),true,true);

		AgreementTimeOfManagePeriod result = target.setYear(agreementTime);

		assertThat(result.getYm()).isEqualTo(new YearMonth(201901));
	}

	@Test
	public void setYearTest_2() {

		AgreementTimeOfManagePeriod agreementTime = new AgreementTimeOfManagePeriod("sid",new YearMonth(202007));
		AgreementOperationSetting target =
				new AgreementOperationSetting("cid",StartingMonthType.APRIL, new ClosureDate(20,false),true,true);

		AgreementTimeOfManagePeriod result = target.setYear(agreementTime);

		assertThat(result.getYm()).isEqualTo(new YearMonth(202007));
	}

}
