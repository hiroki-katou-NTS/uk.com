package nts.uk.ctx.at.record.dom.standardtime.repository;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.classification.affiliate.AffClassificationSidImport;
import nts.uk.ctx.at.record.dom.adapter.employment.SyEmploymentImport;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.affiliationinfor.ClassificationCode;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfClassification;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfCompany;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfEmployment;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.AgreementTimeOfWorkPlace;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.LaborSystemtAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.enums.UseClassificationAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.limitrule.AgreementMultiMonthAvg;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneMonth;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOneYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.AgreementOverMaxTimes;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.ctx.at.shared.dom.workingcondition.ManageAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class AgreementDomainServiceTest {

	@Injectable
	AgreementDomainService.RequireM3 requireM3;

	@Injectable
	AgreementDomainService.RequireM4 requireM4;

	/**
	 * requireM3.agreementUnitSetting return Optional.empty
	 * AND requireM3.agreementTimeOfCompany return isPresent
	 */
	@Test
	public void getBasicSetWithWorkingSystem_1() {

		BasicAgreementSetting setting = new BasicAgreementSetting(new AgreementOneMonth(), new AgreementOneYear(), new AgreementMultiMonthAvg(), AgreementOverMaxTimes.ZERO_TIMES);
		AgreementTimeOfCompany timeOfCompany = new AgreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM,setting);

		new Expectations() {{
			requireM3.agreementUnitSetting("cid");
			result = Optional.empty();

			requireM3.agreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
			result = Optional.of(timeOfCompany);

		}};

        val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
				GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

		assertThat(data).isEqualToComparingFieldByField(setting);
	}

	/**
	 * requireM3.agreementUnitSetting return Optional.empty
	 * AND requireM3.agreementTimeOfCompany return Optional.empty
	 */
	@Test
	public void getBasicSetWithWorkingSystem_2() {

		BasicAgreementSetting setting = new BasicAgreementSetting(
				new AgreementOneMonth(),
				new AgreementOneYear(),
				new AgreementMultiMonthAvg(),
				AgreementOverMaxTimes.ZERO_TIMES);

		new MockUp<AgreementDomainService>() {
			@Mock
			public BasicAgreementSetting getDefault() {
				return setting;
			}
		};
		new Expectations() {{
			requireM3.agreementUnitSetting("cid");
			result = Optional.empty();

			requireM3.agreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
			result = Optional.empty();

		}};

        val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
				GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

		assertThat(data).isEqualToComparingFieldByField(setting);
	}

	/**
	 * return BasicAgreementSetting with WorkingSystem = VARIABLE_WORKING_TIME_WORK
	 * AND use classification
     * require.affEmployeeClassification return present
     * require.agreementTimeOfClassification return present
	 */
	@Test
	public void getBasicSetWithWorkingSystem_3() {

		AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.USE,UseClassificationAtr.NOT_USE,UseClassificationAtr.NOT_USE);

		AffClassificationSidImport classificationSidImport = new AffClassificationSidImport("sid","code",
				new DatePeriod(GeneralDate.ymd(2020,10,1),GeneralDate.ymd(2020,10,30)));

		BasicAgreementSetting setting = new BasicAgreementSetting(new AgreementOneMonth(), new AgreementOneYear(), new AgreementMultiMonthAvg(), AgreementOverMaxTimes.ZERO_TIMES);

		AgreementTimeOfClassification timeOfClassification = new AgreementTimeOfClassification("cid",
				LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM,new ClassificationCode("code"),setting);

		new Expectations() {{
			requireM3.agreementUnitSetting("cid");
			result = Optional.of(agreementUnitSetting);

			requireM3.affEmployeeClassification("cid","sid",GeneralDate.ymd(2020,10,1));
			result = Optional.of(classificationSidImport);

			requireM3.agreementTimeOfClassification("cid", LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM,"code");
			result = Optional.of(timeOfClassification);
		}};

        val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
				GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

		assertThat(data).isEqualToComparingFieldByField(setting);
	}

	/**
	 * return BasicAgreementSetting with WorkingSystem != VARIABLE_WORKING_TIME_WORK
	 * AND use classification
     * require.affEmployeeClassification return present
     * require.agreementTimeOfClassification return present
	 */
	@Test
	public void getBasicSetWithWorkingSystem_4() {

		AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.USE,UseClassificationAtr.NOT_USE,UseClassificationAtr.NOT_USE);

		AffClassificationSidImport classificationSidImport = new AffClassificationSidImport("sid","code",
				new DatePeriod(GeneralDate.ymd(2020,10,1),GeneralDate.ymd(2020,10,30)));

		BasicAgreementSetting setting = new BasicAgreementSetting(new AgreementOneMonth(), new AgreementOneYear(), new AgreementMultiMonthAvg(), AgreementOverMaxTimes.ZERO_TIMES);

		AgreementTimeOfClassification timeOfClassification = new AgreementTimeOfClassification("cid",
				LaborSystemtAtr.GENERAL_LABOR_SYSTEM,new ClassificationCode("code"),setting);

		new Expectations() {{
			requireM3.agreementUnitSetting("cid");
			result = Optional.of(agreementUnitSetting);

			requireM3.affEmployeeClassification("cid","sid",GeneralDate.ymd(2020,10,1));
			result = Optional.of(classificationSidImport);

			requireM3.agreementTimeOfClassification("cid", LaborSystemtAtr.GENERAL_LABOR_SYSTEM,"code");
			result = Optional.of(timeOfClassification);
		}};

        val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
				GeneralDate.ymd(2020,10,1), WorkingSystem.EXCLUDED_WORKING_CALCULATE);

		assertThat(data).isEqualToComparingFieldByField(setting);
	}

    /**
     * return BasicAgreementSetting with WorkingSystem != VARIABLE_WORKING_TIME_WORK
     * AND use classification
     * require.affEmployeeClassification return empty
     */
    @Test
    public void getBasicSetWithWorkingSystem_4_1() {

        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.USE,UseClassificationAtr.NOT_USE,UseClassificationAtr.NOT_USE);

        new Expectations() {{
            requireM3.agreementUnitSetting("cid");
            result = Optional.of(agreementUnitSetting);

            requireM3.affEmployeeClassification("cid","sid",GeneralDate.ymd(2020,10,1));
            result = Optional.empty();

            requireM3.agreementTimeOfCompany("cid",LaborSystemtAtr.GENERAL_LABOR_SYSTEM);
            result = Optional.empty();
        }};


        val expected = new BasicAgreementSetting(
                new AgreementOneMonth(),
                new AgreementOneYear(),
                new AgreementMultiMonthAvg(),
                AgreementOverMaxTimes.ZERO_TIMES);

        new MockUp<AgreementDomainService>() {
            @Mock
            public BasicAgreementSetting getDefault() {
                return expected;
            }
        };

        BasicAgreementSetting data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
                GeneralDate.ymd(2020,10,1), WorkingSystem.EXCLUDED_WORKING_CALCULATE);

        assertThat(data).isEqualToComparingFieldByField(expected);
    }

    /**
     * return BasicAgreementSetting with WorkingSystem != VARIABLE_WORKING_TIME_WORK
     * AND use classification
     * require.affEmployeeClassification return present
     * require.agreementTimeOfClassification return empty
     */
    @Test
    public void getBasicSetWithWorkingSystem_4_2() {

        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.USE,UseClassificationAtr.NOT_USE,UseClassificationAtr.NOT_USE);

        AffClassificationSidImport classificationSidImport = new AffClassificationSidImport("sid","code",
                new DatePeriod(GeneralDate.ymd(2020,10,1),GeneralDate.ymd(2020,10,30)));

        new Expectations() {{
            requireM3.agreementUnitSetting("cid");
            result = Optional.of(agreementUnitSetting);

            requireM3.affEmployeeClassification("cid","sid",GeneralDate.ymd(2020,10,1));
            result = Optional.of(classificationSidImport);

            requireM3.agreementTimeOfClassification("cid", LaborSystemtAtr.GENERAL_LABOR_SYSTEM,"code");
            result = Optional.empty();

            requireM3.agreementTimeOfCompany("cid",LaborSystemtAtr.GENERAL_LABOR_SYSTEM);
            result = Optional.empty();
        }};


        val expected = new BasicAgreementSetting(
                new AgreementOneMonth(),
                new AgreementOneYear(),
                new AgreementMultiMonthAvg(),
                AgreementOverMaxTimes.ZERO_TIMES);

        new MockUp<AgreementDomainService>() {
            @Mock
            public BasicAgreementSetting getDefault() {
                return expected;
            }
        };

        val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
                GeneralDate.ymd(2020,10,1), WorkingSystem.EXCLUDED_WORKING_CALCULATE);

        assertThat(data).isEqualToComparingFieldByField(expected);
    }

    /**
	 * return BasicAgreementSetting with WorkingSystem != VARIABLE_WORKING_TIME_WORK
	 * AND use classification
	 * require.affEmployeeClassification return present
	 * require.agreementTimeOfClassification return empty
     * AND requireM3.agreementTimeOfCompany return isPresent
     */
    @Test
    public void getBasicSetWithWorkingSystem_4_3() {

		AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.USE,UseClassificationAtr.NOT_USE,UseClassificationAtr.NOT_USE);

		AffClassificationSidImport classificationSidImport = new AffClassificationSidImport("sid","code",
				new DatePeriod(GeneralDate.ymd(2020,10,1),GeneralDate.ymd(2020,10,30)));

		BasicAgreementSetting setting = new BasicAgreementSetting(new AgreementOneMonth(), new AgreementOneYear(), new AgreementMultiMonthAvg(), AgreementOverMaxTimes.ZERO_TIMES);
		AgreementTimeOfCompany timeOfCompany = new AgreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM,setting);

        new Expectations() {{
            requireM3.agreementUnitSetting("cid");
            result = Optional.of(agreementUnitSetting);

            requireM3.affEmployeeClassification("cid","sid",GeneralDate.ymd(2020,10,1));
            result = Optional.of(classificationSidImport);

            requireM3.agreementTimeOfClassification("cid", LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM,"code");
            result = Optional.empty();

            requireM3.agreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
            result = Optional.of(timeOfCompany);

        }};

        val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
                GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

        assertThat(data).isEqualToComparingFieldByField(setting);
    }

	/**
	 * return BasicAgreementSetting with WorkingSystem = VARIABLE_WORKING_TIME_WORK
	 * AND use WorkPlace
     * require.getCanUseWorkplaceForEmp return list size = 1
     * require.agreementTimeOfWorkPlace return present
	 */
	@Test
	public void getBasicSetWithWorkingSystem_5() {

		AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.NOT_USE,UseClassificationAtr.NOT_USE,UseClassificationAtr.USE);

		BasicAgreementSetting setting = new BasicAgreementSetting(new AgreementOneMonth(), new AgreementOneYear(), new AgreementMultiMonthAvg(), AgreementOverMaxTimes.ZERO_TIMES);
		AgreementTimeOfWorkPlace timeOfWorkPlace = new AgreementTimeOfWorkPlace("workplaceId",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM,setting);

		new Expectations() {{
			requireM3.agreementUnitSetting("cid");
			result = Optional.of(agreementUnitSetting);

			requireM3.getCanUseWorkplaceForEmp("cid","sid",GeneralDate.ymd(2020,10,1));
			result = Arrays.asList("workplaceId");

			requireM3.agreementTimeOfWorkPlace("workplaceId",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
			result = Optional.of(timeOfWorkPlace);
		}};

		val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
				GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

		assertThat(data).isEqualToComparingFieldByField(setting);
	}

    /**
     * return BasicAgreementSetting with WorkingSystem = VARIABLE_WORKING_TIME_WORK
     * AND use WorkPlace
     * require.getCanUseWorkplaceForEmp return list size = 2
     * require.agreementTimeOfWorkPlace[0] return empty
     * require.agreementTimeOfWorkPlace[1] return value
     */
    @Test
    public void getBasicSetWithWorkingSystem_5_1() {

        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.NOT_USE,UseClassificationAtr.NOT_USE,UseClassificationAtr.USE);

		BasicAgreementSetting setting = new BasicAgreementSetting(new AgreementOneMonth(), new AgreementOneYear(), new AgreementMultiMonthAvg(), AgreementOverMaxTimes.ZERO_TIMES);
        AgreementTimeOfWorkPlace timeOfWorkPlace = new AgreementTimeOfWorkPlace("workplaceId",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM,setting);

        new Expectations() {{
            requireM3.agreementUnitSetting("cid");
            result = Optional.of(agreementUnitSetting);

            requireM3.getCanUseWorkplaceForEmp("cid","sid",GeneralDate.ymd(2020,10,1));
            result = Arrays.asList("workplaceId","workplaceId1");

            requireM3.agreementTimeOfWorkPlace("workplaceId",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
            result = Optional.empty();

            requireM3.agreementTimeOfWorkPlace("workplaceId1",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
            result = Optional.of(timeOfWorkPlace);
        }};

        val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
                GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

        assertThat(data).isEqualToComparingFieldByField(setting);
    }

    /**
     * return BasicAgreementSetting with WorkingSystem = VARIABLE_WORKING_TIME_WORK
     * AND use WorkPlace
     * require.getCanUseWorkplaceForEmp return list
     * require.agreementTimeOfWorkPlace return empty
     */
    @Test
    public void getBasicSetWithWorkingSystem_5_2() {

        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.NOT_USE,UseClassificationAtr.NOT_USE,UseClassificationAtr.USE);

        new Expectations() {{
            requireM3.agreementUnitSetting("cid");
            result = Optional.of(agreementUnitSetting);

            requireM3.getCanUseWorkplaceForEmp("cid","sid",GeneralDate.ymd(2020,10,1));
            result = Arrays.asList("workplaceId");

            requireM3.agreementTimeOfWorkPlace("workplaceId",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
            result = Optional.empty();

            requireM3.agreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
            result = Optional.empty();
        }};

        val expected = new BasicAgreementSetting(
                new AgreementOneMonth(),
                new AgreementOneYear(),
                new AgreementMultiMonthAvg(),
                AgreementOverMaxTimes.ZERO_TIMES);

        new MockUp<AgreementDomainService>() {
            @Mock
            public BasicAgreementSetting getDefault() {
                return expected;
            }
        };

        val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
                GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

        assertThat(data).isEqualToComparingFieldByField(expected);
    }


    /**
     * return BasicAgreementSetting with WorkingSystem = VARIABLE_WORKING_TIME_WORK
     * AND use WorkPlace
     * require.getCanUseWorkplaceForEmp return empty list
     */
    @Test
    public void getBasicSetWithWorkingSystem_5_3() {

        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.NOT_USE,UseClassificationAtr.NOT_USE,UseClassificationAtr.USE);

        new Expectations() {{
            requireM3.agreementUnitSetting("cid");
            result = Optional.of(agreementUnitSetting);

            requireM3.getCanUseWorkplaceForEmp("cid","sid",GeneralDate.ymd(2020,10,1));
            result = new ArrayList<>();

            requireM3.agreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
            result = Optional.empty();

        }};
        val expected = new BasicAgreementSetting(
                new AgreementOneMonth(),
                new AgreementOneYear(),
                new AgreementMultiMonthAvg(),
                AgreementOverMaxTimes.ZERO_TIMES);

        new MockUp<AgreementDomainService>() {
            @Mock
            public BasicAgreementSetting getDefault() {
                return expected;
            }
        };

        val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
                GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

        assertThat(data).isEqualToComparingFieldByField(expected);
    }
	/**
	 * return BasicAgreementSetting with WorkingSystem = VARIABLE_WORKING_TIME_WORK
	 * AND use WorkPlace
	 * require.getCanUseWorkplaceForEmp return empty list
	 * AND requireM3.agreementTimeOfCompany return isPresent
	 */
	@Test
	public void getBasicSetWithWorkingSystem_5_4() {
		AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.NOT_USE,UseClassificationAtr.NOT_USE,UseClassificationAtr.USE);
		BasicAgreementSetting setting = new BasicAgreementSetting(new AgreementOneMonth(), new AgreementOneYear(), new AgreementMultiMonthAvg(), AgreementOverMaxTimes.ZERO_TIMES);
		AgreementTimeOfCompany timeOfCompany = new AgreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM,setting);

		new Expectations() {{
			requireM3.agreementUnitSetting("cid");
			result = Optional.of(agreementUnitSetting);

			requireM3.getCanUseWorkplaceForEmp("cid","sid",GeneralDate.ymd(2020,10,1));
			result = new ArrayList<>();

			requireM3.agreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
			result = Optional.of(timeOfCompany);

		}};

		val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
				GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

		assertThat(data).isEqualToComparingFieldByField(setting);
	}

	/**
	 * return BasicAgreementSetting with WorkingSystem = VARIABLE_WORKING_TIME_WORK
	 * AND use Employment
     * require.employment return present
     * require.agreementTimeOfEmployment return present
	 */
	@Test
	public void getBasicSetWithWorkingSystem_6() {

		AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.NOT_USE,UseClassificationAtr.USE,UseClassificationAtr.NOT_USE);

		BasicAgreementSetting setting = new BasicAgreementSetting(new AgreementOneMonth(), new AgreementOneYear(), new AgreementMultiMonthAvg(), AgreementOverMaxTimes.ZERO_TIMES);
		AgreementTimeOfEmployment timeOfEmployment = new AgreementTimeOfEmployment("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM,new EmploymentCode("code"),setting);

		SyEmploymentImport syEmploymentImport = new SyEmploymentImport("sid","code",
				"name",new DatePeriod(GeneralDate.ymd(2020,10,1),GeneralDate.ymd(2020,10,30)));

		new Expectations() {{
			requireM3.agreementUnitSetting("cid");
			result = Optional.of(agreementUnitSetting);

			requireM3.employment("cid","sid",GeneralDate.ymd(2020,10,1));
			result = Optional.of(syEmploymentImport);

			requireM3.agreementTimeOfEmployment("cid","code",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
			result = Optional.of(timeOfEmployment);
		}};

		val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
				GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

		assertThat(data).isEqualToComparingFieldByField(setting);
	}

    /**
     * return BasicAgreementSetting with WorkingSystem = VARIABLE_WORKING_TIME_WORK
     * AND use Employment
     * require.employment return present
     * require.agreementTimeOfEmployment return empty
     *
     */
    @Test
    public void getBasicSetWithWorkingSystem_6_1() {

        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.NOT_USE,UseClassificationAtr.USE,UseClassificationAtr.NOT_USE);

        SyEmploymentImport syEmploymentImport = new SyEmploymentImport("sid","code",
                "name",new DatePeriod(GeneralDate.ymd(2020,10,1),GeneralDate.ymd(2020,10,30)));

        new Expectations() {{
            requireM3.agreementUnitSetting("cid");
            result = Optional.of(agreementUnitSetting);

            requireM3.employment("cid","sid",GeneralDate.ymd(2020,10,1));
            result = Optional.of(syEmploymentImport);

            requireM3.agreementTimeOfEmployment("cid","code",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
            result = Optional.empty();

            requireM3.agreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
            result = Optional.empty();
        }};
        val expected = new BasicAgreementSetting(
                new AgreementOneMonth(),
                new AgreementOneYear(),
                new AgreementMultiMonthAvg(),
                AgreementOverMaxTimes.ZERO_TIMES);

        new MockUp<AgreementDomainService>() {
            @Mock
            public BasicAgreementSetting getDefault() {
                return expected;
            }
        };
        val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
                GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

        assertThat(data).isEqualToComparingFieldByField(expected);
    }

    /**
     * return BasicAgreementSetting with WorkingSystem = VARIABLE_WORKING_TIME_WORK
     * AND use Employment
     * require.employment return empty
     */
    @Test
    public void getBasicSetWithWorkingSystem_6_2() {

        AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.NOT_USE,UseClassificationAtr.USE,UseClassificationAtr.NOT_USE);

        new Expectations() {{
            requireM3.agreementUnitSetting("cid");
            result = Optional.of(agreementUnitSetting);

            requireM3.employment("cid","sid",GeneralDate.ymd(2020,10,1));
            result = Optional.empty();

            requireM3.agreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
            result = Optional.empty();
        }};
        val expected = new BasicAgreementSetting(
                new AgreementOneMonth(),
                new AgreementOneYear(),
                new AgreementMultiMonthAvg(),
                AgreementOverMaxTimes.ZERO_TIMES);

        new MockUp<AgreementDomainService>() {
            @Mock
            public BasicAgreementSetting getDefault() {
                return expected;
            }
        };
        val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
                GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

        assertThat(data).isEqualToComparingFieldByField(expected);
    }

	/**
	 * return BasicAgreementSetting with WorkingSystem = VARIABLE_WORKING_TIME_WORK
	 * AND use Employment
	 * require.employment return empty
	 * requireM3.agreementTimeOfCompany return value
	 */
	@Test
	public void getBasicSetWithWorkingSystem_6_3() {

		AgreementUnitSetting agreementUnitSetting = new AgreementUnitSetting("cid", UseClassificationAtr.NOT_USE,UseClassificationAtr.USE,UseClassificationAtr.NOT_USE);
		BasicAgreementSetting setting = new BasicAgreementSetting(new AgreementOneMonth(), new AgreementOneYear(), new AgreementMultiMonthAvg(), AgreementOverMaxTimes.ZERO_TIMES);
		AgreementTimeOfCompany timeOfCompany = new AgreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM,setting);

		new Expectations() {{
			requireM3.agreementUnitSetting("cid");
			result = Optional.of(agreementUnitSetting);

			requireM3.employment("cid","sid",GeneralDate.ymd(2020,10,1));
			result = Optional.empty();

			requireM3.agreementTimeOfCompany("cid",LaborSystemtAtr.DEFORMATION_WORKING_TIME_SYSTEM);
			result = Optional.of(timeOfCompany);
		}};

		val data = AgreementDomainService.getBasicSet(requireM3, "cid","sid",
				GeneralDate.ymd(2020,10,1), WorkingSystem.VARIABLE_WORKING_TIME_WORK);

		assertThat(data).isEqualToComparingFieldByField(setting);
	}


	/**
	 * workingConditionItem not isPresent
	 */
	@Test
	public void getBasicSetTest_1() {

		BasicAgreementSetting setting = new BasicAgreementSetting(
				new AgreementOneMonth(),
				new AgreementOneYear(),
				new AgreementMultiMonthAvg(),
				AgreementOverMaxTimes.ZERO_TIMES);

		new MockUp<AgreementDomainService>() {
			@Mock
			public BasicAgreementSetting getDefault() {
				return setting;
			}
		};

		new Expectations() {{
			requireM4.workingConditionItem("sid", GeneralDate.ymd(2020,10,1));
			result = Optional.empty();

		}};

		BasicAgreementSetting data = AgreementDomainService.getBasicSet(requireM4, "cid","sid",
				GeneralDate.ymd(2020,10,1));

		assertThat(data).isEqualToComparingFieldByField(setting);
	}

	/**
	 * workingConditionItem isPresent
	 */
	@Test
	public void getBasicSetTest_2() {

		BasicAgreementSetting setting = new BasicAgreementSetting(
				new AgreementOneMonth(),
				new AgreementOneYear(),
				new AgreementMultiMonthAvg(),
				AgreementOverMaxTimes.ZERO_TIMES);

		new MockUp<AgreementDomainService>() {
			@Mock
			public BasicAgreementSetting getBasicSet(AgreementDomainService.RequireM3 require, String companyId, String employeeId, GeneralDate criteriaDate,
													 WorkingSystem workingSystem) {
				return setting;
			}
		};

		WorkingConditionItem conditionItem = new WorkingConditionItem("histId", ManageAtr.USE,"employeeId");
		new Expectations() {{
			requireM4.workingConditionItem("sid", GeneralDate.ymd(2020,10,1));
			result = Optional.of(conditionItem);

		}};

		BasicAgreementSetting data = AgreementDomainService.getBasicSet(requireM4, "cid","sid",
				GeneralDate.ymd(2020,10,1));

		assertThat(data).isEqualToComparingFieldByField(setting);
	}

}
