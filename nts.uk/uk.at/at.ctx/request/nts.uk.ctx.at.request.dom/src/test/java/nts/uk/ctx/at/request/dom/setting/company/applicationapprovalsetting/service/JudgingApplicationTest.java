package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.core.Application;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.service.ApplicationAvailable.Require;
import nts.uk.ctx.at.shared.dom.scherec.application.common.ApplicationTypeShare;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeAbbreviationName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeMemo;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSymbolicName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class JudgingApplicationTest {

	@Injectable
	private JudgingApplication.Require require;

	@Injectable
	private ApplicationAvailable.Require require2;

	private String cid = "cid";
	private String sid = "sid";
	private String workTypeCode = "workTypeCode";
	private GeneralDate date = GeneralDate.today();
	private ApplicationTypeShare targetApp = EnumAdaptor.valueOf(6, ApplicationTypeShare.class);

	private ApplicationTypeShare targetApp2 = EnumAdaptor.valueOf(0, ApplicationTypeShare.class);

	// if !$申請承認設定.isPresent()
	@Test
	public void test1() {

		new Expectations() {
			{
				require.findByPK(anyString, anyString);
				result = Optional.empty();
			}
		};

		Optional<ApplicationTypeShare> reuslt = JudgingApplication.toDecide(require, cid, sid, date, workTypeCode);

		assertThat(reuslt).isEmpty();
	}

	// return Optional.empty();
	@Test
	public void test2() {

		DailyWork dailyWork = new DailyWork(EnumAdaptor.valueOf(1, WorkTypeUnit.class),
				EnumAdaptor.valueOf(11, WorkTypeClassification.class),
				EnumAdaptor.valueOf(1, WorkTypeClassification.class),
				EnumAdaptor.valueOf(1, WorkTypeClassification.class));

		WorkType workType = new WorkType(new WorkTypeCode(workTypeCode), new WorkTypeSymbolicName("dummy"),
				new WorkTypeName("dummy"), new WorkTypeAbbreviationName("dummy"), new WorkTypeMemo("dummy"), dailyWork);

		new Expectations() {
			{
				require.findByPK(anyString, anyString);
				result = Optional.of(workType);
			}
		};

		Optional<ApplicationTypeShare> reuslt = JudgingApplication.toDecide(require, cid, sid, date, workTypeCode);

		assertThat(reuslt).isEmpty();
	}

	// workType.get().isHolidayWork()
	// workType.get().getDailyWork().getOneDay().isHoliday()
	// result = ApplicationTypeShare.HOLIDAY_WORK_APPLICATION;
	// return Optional.empty();
	@Test
	public void test3() {

		DailyWork dailyWork = new DailyWork(EnumAdaptor.valueOf(0, WorkTypeUnit.class),
				EnumAdaptor.valueOf(1, WorkTypeClassification.class),
				EnumAdaptor.valueOf(1, WorkTypeClassification.class),
				EnumAdaptor.valueOf(1, WorkTypeClassification.class));

		WorkType workType = new WorkType(new WorkTypeCode(workTypeCode), new WorkTypeSymbolicName("dummy"),
				new WorkTypeName("dummy"), new WorkTypeAbbreviationName("dummy"), new WorkTypeMemo("dummy"), dailyWork);

		new Expectations() {
			{
				require.findByPK(anyString, anyString);
				result = Optional.of(workType);
			}
		};

		Optional<ApplicationTypeShare> reuslt = JudgingApplication.toDecide(require, cid, sid, date, workTypeCode);

		assertThat(reuslt).isEmpty();
	}

	// workType.get().isHolidayWork()
	// workType.get().getDailyWork().getOneDay().isHoliday()
	// result = ApplicationTypeShare.HOLIDAY_WORK_APPLICATION;
	// return Optional.of(result);
	@Test
	public void test4() {

		DailyWork dailyWork = new DailyWork(EnumAdaptor.valueOf(0, WorkTypeUnit.class),
				EnumAdaptor.valueOf(1, WorkTypeClassification.class),
				EnumAdaptor.valueOf(1, WorkTypeClassification.class),
				EnumAdaptor.valueOf(1, WorkTypeClassification.class));

		WorkType workType = new WorkType(new WorkTypeCode(workTypeCode), new WorkTypeSymbolicName("dummy"),
				new WorkTypeName("dummy"), new WorkTypeAbbreviationName("dummy"), new WorkTypeMemo("dummy"), dailyWork);

		new MockUp<ApplicationAvailable>() {
			@Mock
			public boolean get(Require require, String cid, String sid, GeneralDate date,
					ApplicationTypeShare appType) {
				return true;
			}
		};

		new Expectations() {
			{
				require.findByPK(anyString, anyString);
				result = Optional.of(workType);
			}
		};

		Optional<ApplicationTypeShare> reuslt = JudgingApplication.toDecide(require, cid, sid, date, workTypeCode);

		assertThat(reuslt.get().value).isEqualTo(6);
	}

	// workType.get().isHolidayWork()
	// workType.get().getDailyWork().getOneDay().isHoliday()
	// result = ApplicationTypeShare.HOLIDAY_WORK_APPLICATION;
	// return Optional.empty();

	@Test
	public void test5() {

		List<Application> applications = new ArrayList<>();
		applications.add(new Application());

		DailyWork dailyWork = new DailyWork(EnumAdaptor.valueOf(0, WorkTypeUnit.class),
				EnumAdaptor.valueOf(1, WorkTypeClassification.class),
				EnumAdaptor.valueOf(1, WorkTypeClassification.class),
				EnumAdaptor.valueOf(1, WorkTypeClassification.class));

		WorkType workType = new WorkType(new WorkTypeCode(workTypeCode), new WorkTypeSymbolicName("dummy"),
				new WorkTypeName("dummy"), new WorkTypeAbbreviationName("dummy"), new WorkTypeMemo("dummy"), dailyWork);

		new MockUp<ApplicationAvailable>() {
			@Mock
			public boolean get(Require require, String cid, String sid, GeneralDate date,
					ApplicationTypeShare appType) {
				return true;
			}
		};

		new Expectations() {
			{
				require.findByPK(anyString, anyString);
				result = Optional.of(workType);

				require.getAllApplicationByAppTypeAndPrePostAtr(sid, targetApp.value, date, 1);
				result = applications;

			}
		};

		Optional<ApplicationTypeShare> reuslt = JudgingApplication.toDecide(require, cid, sid, date, workTypeCode);

		assertThat(reuslt).isEmpty();
	}

	// workType.get().isHolidayWork()
	// workType.get().getDailyWork().getOneDay().isHoliday()
	// result = ApplicationTypeShare.HOLIDAY_WORK_APPLICATION;
	// return Optional.empty();
	@Test
	public void test6() {

		DailyWork dailyWork = new DailyWork(EnumAdaptor.valueOf(0, WorkTypeUnit.class),
				EnumAdaptor.valueOf(11, WorkTypeClassification.class),
				EnumAdaptor.valueOf(2, WorkTypeClassification.class),
				EnumAdaptor.valueOf(2, WorkTypeClassification.class));

		WorkType workType = new WorkType(new WorkTypeCode(workTypeCode), new WorkTypeSymbolicName("dummy"),
				new WorkTypeName("dummy"), new WorkTypeAbbreviationName("dummy"), new WorkTypeMemo("dummy"), dailyWork);

		new Expectations() {
			{
				require.findByPK(anyString, anyString);
				result = Optional.of(workType);
			}
		};

		Optional<ApplicationTypeShare> reuslt = JudgingApplication.toDecide(require, cid, sid, date, workTypeCode);

		assertThat(reuslt).isEmpty();
	}

	// workType.get().isHolidayWork()
	// workType.get().getDailyWork().getOneDay().isHoliday()
	// result = ApplicationTypeShare.OVER_TIME_APPLICATION;
	// return Optional.of(result);
	@Test
	public void test7() {

		DailyWork dailyWork = new DailyWork(EnumAdaptor.valueOf(0, WorkTypeUnit.class),
				EnumAdaptor.valueOf(0, WorkTypeClassification.class),
				EnumAdaptor.valueOf(2, WorkTypeClassification.class),
				EnumAdaptor.valueOf(2, WorkTypeClassification.class));

		WorkType workType = new WorkType(new WorkTypeCode(workTypeCode), new WorkTypeSymbolicName("dummy"),
				new WorkTypeName("dummy"), new WorkTypeAbbreviationName("dummy"), new WorkTypeMemo("dummy"), dailyWork);

		new MockUp<ApplicationAvailable>() {
			@Mock
			public boolean get(Require require, String cid, String sid, GeneralDate date,
					ApplicationTypeShare appType) {
				return true;
			}
		};

		new Expectations() {
			{
				require.findByPK(anyString, anyString);
				result = Optional.of(workType);
			}
		};

		Optional<ApplicationTypeShare> reuslt = JudgingApplication.toDecide(require, cid, sid, date, workTypeCode);

		assertThat(reuslt.get().value).isEqualTo(ApplicationTypeShare.OVER_TIME_APPLICATION.value);
	}

	// workType.get().isHolidayWork()
	// workType.get().getDailyWork().getOneDay().isHoliday()
	// result = ApplicationTypeShare.OVER_TIME_APPLICATION;
	// return Optional.empty();
	@Test
	public void test8() {

		List<Application> applications = new ArrayList<>();
		applications.add(new Application());

		DailyWork dailyWork = new DailyWork(EnumAdaptor.valueOf(0, WorkTypeUnit.class),
				EnumAdaptor.valueOf(0, WorkTypeClassification.class),
				EnumAdaptor.valueOf(0, WorkTypeClassification.class),
				EnumAdaptor.valueOf(0, WorkTypeClassification.class));

		WorkType workType = new WorkType(new WorkTypeCode(workTypeCode), new WorkTypeSymbolicName("dummy"),
				new WorkTypeName("dummy"), new WorkTypeAbbreviationName("dummy"), new WorkTypeMemo("dummy"), dailyWork);

		new MockUp<ApplicationAvailable>() {
			@Mock
			public boolean get(Require require, String cid, String sid, GeneralDate date,
					ApplicationTypeShare appType) {
				return true;
			}
		};

		new Expectations() {
			{
				require.findByPK(anyString, anyString);
				result = Optional.of(workType);

				require.getAllApplicationByAppTypeAndPrePostAtr(sid, targetApp2.value, date, 1);
				result = applications;

			}
		};

		Optional<ApplicationTypeShare> reuslt = JudgingApplication.toDecide(require, cid, sid, date, workTypeCode);

		assertThat(reuslt).isEmpty();
	}
}