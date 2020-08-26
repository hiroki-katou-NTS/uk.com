package nts.uk.ctx.at.schedule.dom.shift.pattern.service;

import lombok.AllArgsConstructor;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingGetMemento;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeAbbreviationName;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeName;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class WorkMonthlySettingServiceTest {

    @Injectable
    private WorkMonthlySettingService.Require require;

    @Injectable
    private WorkInformation.Require requireWorkInfo;

    private final String CID = "000-001";

    private final boolean IS_OVER_WRITE_TRUE = true;

    private final boolean IS_OVER_WRITE_FALSE = false;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void test_error_WORKTYPE_WAS_DELETE() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);

        NtsAssert.businessException("Msg_435", () ->{
                    new WorkMonthlySettingService()
                            .register(requireWorkInfo,require,workMonthlySetting,IS_OVER_WRITE_TRUE);
                }
        );
    }

    @Test
    public void test_error_WORKTIME_ARE_REQUIRE_NOT_SET() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);
        nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode workTypeCode = new nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode("123") ;
        WorkType workType =  new WorkType(CID, workTypeCode, new WorkTypeName("name"), new WorkTypeAbbreviationName("123"));
        workMonthlySetting.getWorkInformation().setWorkTimeCode(null);
        new Expectations(){{
            requireWorkInfo.findByPK(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = Optional.of(workType);
            requireWorkInfo.checkNeededOfWorkTimeSetting(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = SetupType.REQUIRED;
        }};
        NtsAssert.businessException("Msg_435", () ->{
                    new WorkMonthlySettingService()
                            .register(requireWorkInfo,require,workMonthlySetting,IS_OVER_WRITE_TRUE);
                }
        );
    }

    @Test
    public void test_error_WORKTIME_ARE_SET_WHEN_UNNECESSARY() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);
        nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode workTypeCode = new nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode("123") ;
        WorkType workType =  new WorkType(CID, workTypeCode, new WorkTypeName("name"), new WorkTypeAbbreviationName("123"));
        new Expectations(){{
            requireWorkInfo.findByPK(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = Optional.of(workType);
            requireWorkInfo.checkNeededOfWorkTimeSetting(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = SetupType.NOT_REQUIRED;
        }};
        NtsAssert.businessException("Msg_435", () ->{
                    new WorkMonthlySettingService()
                            .register(requireWorkInfo,require,workMonthlySetting,IS_OVER_WRITE_TRUE);
                }
        );
    }

    @Test
    public void test_error_WORKTIME_WAS_DELETE() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);
        nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode workTypeCode = new nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode("123") ;
        WorkType workType =  new WorkType(CID, workTypeCode, new WorkTypeName("name"), new WorkTypeAbbreviationName("123"));
        new Expectations(){{
            requireWorkInfo.findByPK(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = Optional.of(workType);
            requireWorkInfo.checkNeededOfWorkTimeSetting(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = SetupType.OPTIONAL;
            requireWorkInfo.findByCode(workMonthlySetting.getWorkInformation().getWorkTimeCode().v());
            result = Optional.empty();
        }};
        NtsAssert.businessException("Msg_435", () ->{
                    new WorkMonthlySettingService()
                            .register(requireWorkInfo,require,workMonthlySetting,IS_OVER_WRITE_FALSE);
                }
        );
    }


    @Test
    public void test_non_error_condition_non_update() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);
        nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode workTypeCode = new nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode("123") ;
        WorkType workType =  new WorkType(CID, workTypeCode, new WorkTypeName("name"), new WorkTypeAbbreviationName("123"));
        WorkTimeSetting workTimeSetting = new WorkTimeSetting();
        Optional<AtomTask> expected = Optional.empty();

        expectationsDataForUpdateCase(workMonthlySetting, workType, workTimeSetting);

        assertThat(expected.equals(new WorkMonthlySettingService()
                .register(requireWorkInfo,require,workMonthlySetting,IS_OVER_WRITE_FALSE)));
    }

    @Test
    public void test_non_error_condition_update() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);
        nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode workTypeCode = new nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode("123") ;
        WorkType workType =  new WorkType(CID, workTypeCode, new WorkTypeName("name"), new WorkTypeAbbreviationName("123"));
        WorkTimeSetting workTimeSetting = new WorkTimeSetting();

        expectationsDataForUpdateCase(workMonthlySetting, workType, workTimeSetting);

        NtsAssert.atomTask(() -> new WorkMonthlySettingService()
                .register(requireWorkInfo,require,workMonthlySetting,IS_OVER_WRITE_TRUE).get(),
                any -> require.update(any.get())
        );
    }

    @Test
    public void test_non_error_condition_add() {
        WorkMonthlySettingGetMemento memento = new WorkMonthlySettingGetMementoImpl(CID);
        WorkMonthlySetting workMonthlySetting =  new WorkMonthlySetting(memento);
        nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode workTypeCode = new nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode("123") ;
        WorkType workType =  new WorkType(CID, workTypeCode, new WorkTypeName("name"), new WorkTypeAbbreviationName("123"));
        WorkTimeSetting workTimeSetting = new WorkTimeSetting();

        new Expectations(){{
            //workMonthlySetting.checkForErrors();
            requireWorkInfo.findByPK(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = Optional.of(workType);
            requireWorkInfo.checkNeededOfWorkTimeSetting(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = SetupType.REQUIRED;
            requireWorkInfo.findByCode(workMonthlySetting.getWorkInformation().getWorkTimeCode().v());
            result = Optional.of(workTimeSetting);
            require.exists(workMonthlySetting.getCompanyId().v(), workMonthlySetting.getMonthlyPatternCode().v(), workMonthlySetting.getYmdk());
            result = false;
        }};

        NtsAssert.atomTask(() -> new WorkMonthlySettingService()
                        .register(requireWorkInfo,require,workMonthlySetting,IS_OVER_WRITE_TRUE).get(),
                any -> require.add(any.get())
        );
    }

    private Expectations expectationsDataForUpdateCase(WorkMonthlySetting workMonthlySetting, WorkType workType, WorkTimeSetting workTimeSetting){
        return new Expectations(){{
            //workMonthlySetting.checkForErrors();
            requireWorkInfo.findByPK(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = Optional.of(workType);
            requireWorkInfo.checkNeededOfWorkTimeSetting(workMonthlySetting.getWorkInformation().getWorkTypeCode().v());
            result = SetupType.REQUIRED;
            requireWorkInfo.findByCode(workMonthlySetting.getWorkInformation().getWorkTimeCode().v());
            result = Optional.of(workTimeSetting);
            require.exists(workMonthlySetting.getCompanyId().v(), workMonthlySetting.getMonthlyPatternCode().v(), workMonthlySetting.getYmdk());
            result = true;
        }};
    }

    @AllArgsConstructor
    class WorkMonthlySettingGetMementoImpl implements WorkMonthlySettingGetMemento{

        private String cid;

        @Override
        public CompanyId getCompanyId() {
            return new CompanyId(cid);
        }

        @Override
        public WorkTypeCode getWorkTypeCode() {
            return new WorkTypeCode("A");
        }

        @Override
        public WorkingCode getWorkingCode() {
            return new WorkingCode("code-1");
        }

        @Override
        public GeneralDate getYmdK() {
            return GeneralDate.today();
        }

        @Override
        public MonthlyPatternCode getMonthlyPatternCode() {
            return new MonthlyPatternCode("COD");
        }
    }
}

