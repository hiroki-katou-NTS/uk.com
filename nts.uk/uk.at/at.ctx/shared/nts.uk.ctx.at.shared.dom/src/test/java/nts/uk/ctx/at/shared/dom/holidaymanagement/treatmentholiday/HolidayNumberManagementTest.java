package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;

import mockit.Expectations;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.days.FourWeekDays;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.shr.com.enumcommon.NotUseAtr;

public class HolidayNumberManagementTest {

	@Injectable
	HolidayNumberManagement.Require require;
	
	@Test
	public void testGetter() {
		HolidayNumberManagement data = new HolidayNumberManagement(NotUseAtr.NOT_USE, 
				new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1)), new FourWeekDays(4.5));
		NtsAssert.invokeGetters(data);
	}
	
	@Test
	public void test_create() {
		HolidayAcqManaPeriod holidayAcqManaPeriod = new HolidayAcqManaPeriod(new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(1)),
				new FourWeekDays(4.0));
		NotUseAtr addNonstatutoryHolidays = NotUseAtr.USE;
		HolidayNumberManagement result = HolidayNumberManagement.create(holidayAcqManaPeriod, addNonstatutoryHolidays);
		assertThat(result.getAddNonstatutoryHolidays()).isEqualTo(addNonstatutoryHolidays);
		assertThat(result.getPeriod()).isEqualTo(holidayAcqManaPeriod.getPeriod());
		assertThat(result.getHolidayDays()).isEqualTo(holidayAcqManaPeriod.getHolidayDays());
	}
	
	/**
	 * 法定外休日を休日取得数に加える == 利用する
	 */
	@Test
	public void test_countNumberHolidays_1() {
		HolidayAcqManaPeriod holidayAcqManaPeriod = new HolidayAcqManaPeriod(new DatePeriod(GeneralDate.today(), GeneralDate.today().addMonths(1)),
				new FourWeekDays(4.0));
		NotUseAtr addNonstatutoryHolidays = NotUseAtr.USE;
		HolidayNumberManagement holidayNumberManagement = HolidayNumberManagement.create(holidayAcqManaPeriod, addNonstatutoryHolidays);
		Map<GeneralDate,WorkInformation> mapWorkInformation = new HashMap<>();
		mapWorkInformation.put(GeneralDate.today(), new WorkInformation("WorkType1", "WorkTime1"));
		mapWorkInformation.put(GeneralDate.today().addDays(3), new WorkInformation("WorkType2", "WorkTime2"));
		mapWorkInformation.put(GeneralDate.today().addMonths(2), new WorkInformation("WorkType3", "WorkTime3"));
		
		WorkType workType1 = new WorkType();
		workType1.setWorkTypeSet(Arrays.asList(new WorkTypeSet("companyId1",
				new WorkTypeCode("WorkType1"),
				WorkAtr.OneDay, null, HolidayAtr.STATUTORY_HOLIDAYS,
				null, null, 1, 1, null, null, null, null)));
		DailyWork dailyWork1 = new DailyWork();
		
		dailyWork1.setOneDay(WorkTypeClassification.Holiday);
		workType1.setDailyWork(dailyWork1);
		workType1.setWorkTypeCode(new WorkTypeCode("WorkType1"));
		
		WorkType workType2 = new WorkType();
		workType2.setWorkTypeSet(Arrays.asList(new WorkTypeSet("companyId1",
				new WorkTypeCode("WorkType2"),
				WorkAtr.OneDay, null, HolidayAtr.NON_STATUTORY_HOLIDAYS,
				null, null, 1, 1, null, null, null, null)));
		DailyWork dailyWork2 = new DailyWork();
		dailyWork2.setOneDay(WorkTypeClassification.Holiday);
		workType2.setDailyWork(dailyWork2);
		workType2.setWorkTypeCode(new WorkTypeCode("WorkType1"));
		
		
		new Expectations() {
            {
            	require.findByPK("WorkType1");
            	result = Optional.of(workType1);
            	
            	require.findByPK("WorkType2");
            	result = Optional.of(workType2);
            }
        };
        int result = holidayNumberManagement.countNumberHolidays(require, mapWorkInformation);
		assertThat(result).isEqualTo(2);
	}
	
	/**
	 * 法定外休日を休日取得数に加える == 利用しない
	 */
	@Test
	public void test_countNumberHolidays_2() {
		HolidayAcqManaPeriod holidayAcqManaPeriod = new HolidayAcqManaPeriod(new DatePeriod(GeneralDate.today(), GeneralDate.today().addMonths(1)),
				new FourWeekDays(4.0));
		NotUseAtr addNonstatutoryHolidays = NotUseAtr.NOT_USE;
		HolidayNumberManagement holidayNumberManagement = HolidayNumberManagement.create(holidayAcqManaPeriod, addNonstatutoryHolidays);
		Map<GeneralDate,WorkInformation> mapWorkInformation = new HashMap<>();
		mapWorkInformation.put(GeneralDate.today(), new WorkInformation("WorkType1", "WorkTime1"));
		mapWorkInformation.put(GeneralDate.today().addDays(3), new WorkInformation("WorkType2", "WorkTime2"));
		mapWorkInformation.put(GeneralDate.today().addDays(4), new WorkInformation("WorkType3", "WorkTime3"));
		
		WorkType workType1 = new WorkType();
		workType1.setWorkTypeSet(Arrays.asList(new WorkTypeSet("companyId1",
				new WorkTypeCode("WorkType1"),
				WorkAtr.OneDay, null, HolidayAtr.STATUTORY_HOLIDAYS,
				null, null, 1, 1, null, null, null, null)));
		DailyWork dailyWork1 = new DailyWork();
		
		dailyWork1.setOneDay(WorkTypeClassification.Holiday);
		workType1.setDailyWork(dailyWork1);
		workType1.setWorkTypeCode(new WorkTypeCode("WorkType2"));
		
		WorkType workType2 = new WorkType();
		workType2.setWorkTypeSet(new ArrayList<>());
		DailyWork dailyWork2 = new DailyWork();
		dailyWork2.setOneDay(WorkTypeClassification.Holiday);
		workType2.setDailyWork(dailyWork2);
		workType2.setWorkTypeCode(new WorkTypeCode("WorkType2"));
		
		WorkType workType3 = new WorkType();
		DailyWork dailyWork3 = new DailyWork();
		dailyWork3.setOneDay(WorkTypeClassification.Attendance);
		workType3.setDailyWork(dailyWork3);
		workType3.setWorkTypeCode(new WorkTypeCode("WorkType3"));
		
		
		new Expectations() {
            {
            	require.findByPK("WorkType1");
            	result = Optional.of(workType1);
            	
            	require.findByPK("WorkType2");
            	result = Optional.of(workType2);
            	
            	require.findByPK("WorkType3");
            	result = Optional.of(workType3);
            }
        };
        int result = holidayNumberManagement.countNumberHolidays(require, mapWorkInformation);
		assertThat(result).isEqualTo(1);
	}
	@Test
	public void test_redundantMissingHoliday() {
		HolidayAcqManaPeriod holidayAcqManaPeriod = new HolidayAcqManaPeriod(new DatePeriod(GeneralDate.today(), GeneralDate.today().addMonths(1)),
				new FourWeekDays(4.0));
		NotUseAtr addNonstatutoryHolidays = NotUseAtr.NOT_USE;
		HolidayNumberManagement holidayNumberManagement = HolidayNumberManagement.create(holidayAcqManaPeriod, addNonstatutoryHolidays);
		Map<GeneralDate,WorkInformation> mapWorkInformation = new HashMap<>();
		mapWorkInformation.put(GeneralDate.today(), new WorkInformation("WorkType1", "WorkTime1"));
		mapWorkInformation.put(GeneralDate.today().addDays(3), new WorkInformation("WorkType2", "WorkTime2"));
		mapWorkInformation.put(GeneralDate.today().addDays(4), new WorkInformation("WorkType3", "WorkTime3"));
		
		WorkType workType1 = new WorkType();
		workType1.setWorkTypeSet(Arrays.asList(new WorkTypeSet("companyId1",
				new WorkTypeCode("WorkType1"),
				WorkAtr.OneDay, null, HolidayAtr.STATUTORY_HOLIDAYS,
				null, null, 1, 1, null, null, null, null)));
		DailyWork dailyWork1 = new DailyWork();
		
		dailyWork1.setOneDay(WorkTypeClassification.Holiday);
		workType1.setDailyWork(dailyWork1);
		workType1.setWorkTypeCode(new WorkTypeCode("WorkType2"));
		
		WorkType workType2 = new WorkType();
		workType2.setWorkTypeSet(new ArrayList<>());
		DailyWork dailyWork2 = new DailyWork();
		dailyWork2.setOneDay(WorkTypeClassification.Holiday);
		workType2.setDailyWork(dailyWork2);
		workType2.setWorkTypeCode(new WorkTypeCode("WorkType2"));
		
		WorkType workType3 = new WorkType();
		DailyWork dailyWork3 = new DailyWork();
		dailyWork3.setOneDay(WorkTypeClassification.Attendance);
		workType3.setDailyWork(dailyWork3);
		workType3.setWorkTypeCode(new WorkTypeCode("WorkType3"));
		
		
		new Expectations() {
            {
            	require.findByPK("WorkType1");
            	result = Optional.of(workType1);
            	
            	require.findByPK("WorkType2");
            	result = Optional.of(workType2);
            	
            	require.findByPK("WorkType3");
            	result = Optional.of(workType3);
            }
        };
        int result = holidayNumberManagement.redundantMissingHoliday(require, mapWorkInformation);
		assertThat(result).isEqualTo(3);
	}

}

