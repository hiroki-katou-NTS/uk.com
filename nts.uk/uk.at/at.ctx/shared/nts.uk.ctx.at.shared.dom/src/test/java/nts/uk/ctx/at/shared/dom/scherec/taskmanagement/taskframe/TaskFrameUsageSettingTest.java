package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe;


import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(JMockit.class)
public class TaskFrameUsageSettingTest {


    @Test
    public void testGetter() {
        val frameSettingList = HelperTaskFrame.createListDomain();
        val instance = TaskFrameUsageSetting.create(frameSettingList);
        NtsAssert.invokeGetters(instance);
    }

    /**
     * Test case: 枠設定.作業枠NO: Duplicate
     * throw  BusinessException("Msg_2064")
     */
    @Test
    public void testDuplicateTaskFrameNo() {
        val frameSettingList = HelperTaskFrame.createListDomainDuplicate();
        NtsAssert.businessException("Msg_2064", () -> TaskFrameUsageSetting.create(frameSettingList));

    }

    /**
     * Test case: TaskFrameNo == 4: UseAtr : NOT_USE
     * Test case: TaskFrameNo == 5: UseAtr : USE
     * throw  BusinessException("Msg_2063")
     */
    @Test
    public void testCase_TaskFrameNo4_NOT_USE_TaskFrameNo5_USE() {
        val frameSettingList = HelperTaskFrame.createListDomain04();
        NtsAssert.businessException("Msg_2063", () -> TaskFrameUsageSetting.create(frameSettingList));

    }

    /**
     * Test case: TaskFrameNo == 3: UseAtr : NOT_USE
     * Test case: TaskFrameNo == 4: UseAtr : USE
     * throw  BusinessException("Msg_2063")
     */
    @Test
    public void testCase_TaskFrameNo3_NOT_USE_TaskFrameNo4_USE() {
        val frameSettingList = HelperTaskFrame.createListDomain03();
        NtsAssert.businessException("Msg_2063", () -> TaskFrameUsageSetting.create(frameSettingList));

    }

    /**
     * Test case: TaskFrameNo == 2: UseAtr : NOT_USE
     * Test case: TaskFrameNo == 3: UseAtr : USE
     * throw  BusinessException("Msg_2063")
     */
    @Test
    public void testCase_TaskFrameNo2_NOT_USE_TaskFrameNo3_USE() {
        val frameSettingList = HelperTaskFrame.createListDomain02();
        NtsAssert.businessException("Msg_2063", () -> TaskFrameUsageSetting.create(frameSettingList));

    }

    /**
     * Test case: TaskFrameNo == 1: UseAtr : NOT_USE
     * Test case: TaskFrameNo == 2: UseAtr : USE
     * throw  BusinessException("Msg_2063")
     */
    @Test
    public void testCase_TaskFrameNo1_NOT_USE_TaskFrameNo2_USE() {
        val frameSettingList = HelperTaskFrame.createListDomain01();
        NtsAssert.businessException("Msg_2063", () -> TaskFrameUsageSetting.create(frameSettingList));

    }

    /**
     * CASE [CI]  SUCCESS
     */
    @Test
    public void testCaseCreateSuccess() {
        val frameSettingList = HelperTaskFrame.createListDomain();
        val instance = TaskFrameUsageSetting.create(frameSettingList);
        Assertions.assertThat(instance.getFrameSettingList())
                .extracting(d -> d)
                .containsExactlyElementsOf(frameSettingList);
    }

}

