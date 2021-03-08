package nts.uk.ctx.at.shared.dom.taskmanagement.aggregateroot.taskframe;


import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(JMockit.class)
public class TaskFrameUsageSettingTest {


    @Test
    public void testGetter() {
        val frameSettingList = Helper.createListDomain();
        val instance = new TaskFrameUsageSetting(frameSettingList);
        NtsAssert.invokeGetters(instance);
    }

    /**
     * Test case: 枠設定.作業枠NO: Duplicate
     * throw  BusinessException("Msg_2064")
     */
    @Test
    public void testDuplicateTaskFrameNo() {
        val frameSettingList = Helper.createListDomainDuplicate();
        NtsAssert.businessException("Msg_2064", () -> new TaskFrameUsageSetting(frameSettingList));

    }

    /**
     * Test case: TaskFrameNo == 4: UseAtr : NOT_USE
     * Test case: TaskFrameNo == 5: UseAtr : USE
     * throw  BusinessException("Msg_2063")
     */
    @Test
    public void testCase_TaskFrameNo4_NOT_USE_TaskFrameNo5_USE() {
        val frameSettingList = Helper.createListDomain04();
        NtsAssert.businessException("Msg_2063", () -> new TaskFrameUsageSetting(frameSettingList));

    }
    /**
     * Test case: TaskFrameNo == 3: UseAtr : NOT_USE
     * Test case: TaskFrameNo == 4: UseAtr : USE
     * throw  BusinessException("Msg_2063")
     */
    @Test
    public void testCase_TaskFrameNo3_NOT_USE_TaskFrameNo4_USE() {
        val frameSettingList = Helper.createListDomain03();
        NtsAssert.businessException("Msg_2063", () -> new TaskFrameUsageSetting(frameSettingList));

    }

    /**
     * Test case: TaskFrameNo == 2: UseAtr : NOT_USE
     * Test case: TaskFrameNo == 3: UseAtr : USE
     * throw  BusinessException("Msg_2063")
     */
    @Test
    public void testCase_TaskFrameNo2_NOT_USE_TaskFrameNo3_USE() {
        val frameSettingList = Helper.createListDomain02();
        NtsAssert.businessException("Msg_2063", () -> new TaskFrameUsageSetting(frameSettingList));

    }

    /**
     * Test case: TaskFrameNo == 1: UseAtr : NOT_USE
     * Test case: TaskFrameNo == 2: UseAtr : USE
     * throw  BusinessException("Msg_2063")
     */
    @Test
    public void testCase_TaskFrameNo1_NOT_USE_TaskFrameNo2_USE() {
        val frameSettingList = Helper.createListDomain01();
        NtsAssert.businessException("Msg_2063", () -> new TaskFrameUsageSetting(frameSettingList));

    }
    public static class Helper {
         static List<TaskFrameSetting> createListDomain() {
            return Arrays.asList(
                    new TaskFrameSetting(
                            new TaskFrameNo(1),
                            new TaskFrameName("Name01"),
                            UseAtr.USE
                    ),
                    new TaskFrameSetting(
                            new TaskFrameNo(5),
                            new TaskFrameName("Name05"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(3),
                            new TaskFrameName("Name03"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(4),
                            new TaskFrameName("Name04"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(2),
                            new TaskFrameName("Name02"),
                            UseAtr.USE

                    )
            );
        }

        static List<TaskFrameSetting> createListDomain04() {
            return Arrays.asList(
                    new TaskFrameSetting(
                            new TaskFrameNo(1),
                            new TaskFrameName("Name01"),
                            UseAtr.USE
                    ),
                    new TaskFrameSetting(
                            new TaskFrameNo(5),
                            new TaskFrameName("Name05"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(3),
                            new TaskFrameName("Name03"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(4),
                            new TaskFrameName("Name04"),
                            UseAtr.NOTUSE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(2),
                            new TaskFrameName("Name02"),
                            UseAtr.USE

                    )
            );
        }
        static List<TaskFrameSetting> createListDomain03() {
            return Arrays.asList(
                    new TaskFrameSetting(
                            new TaskFrameNo(1),
                            new TaskFrameName("Name01"),
                            UseAtr.USE
                    ),
                    new TaskFrameSetting(
                            new TaskFrameNo(5),
                            new TaskFrameName("Name05"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(3),
                            new TaskFrameName("Name03"),
                            UseAtr.NOTUSE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(4),
                            new TaskFrameName("Name04"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(2),
                            new TaskFrameName("Name02"),
                            UseAtr.USE

                    )
            );
        }
        static List<TaskFrameSetting> createListDomain02() {
            return Arrays.asList(
                    new TaskFrameSetting(
                            new TaskFrameNo(1),
                            new TaskFrameName("Name01"),
                            UseAtr.USE
                    ),
                    new TaskFrameSetting(
                            new TaskFrameNo(5),
                            new TaskFrameName("Name05"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(3),
                            new TaskFrameName("Name03"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(4),
                            new TaskFrameName("Name04"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(2),
                            new TaskFrameName("Name02"),
                            UseAtr.NOTUSE

                    )
            );
        }    static List<TaskFrameSetting> createListDomain01() {
            return Arrays.asList(
                    new TaskFrameSetting(
                            new TaskFrameNo(1),
                            new TaskFrameName("Name01"),
                            UseAtr.NOTUSE
                    ),
                    new TaskFrameSetting(
                            new TaskFrameNo(5),
                            new TaskFrameName("Name05"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(3),
                            new TaskFrameName("Name03"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(4),
                            new TaskFrameName("Name04"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(2),
                            new TaskFrameName("Name02"),
                            UseAtr.USE

                    )
            );
        }
        static List<TaskFrameSetting> createListDomainDuplicate() {
            return Arrays.asList(
                    new TaskFrameSetting(
                            new TaskFrameNo(1),
                            new TaskFrameName("Name01"),
                            UseAtr.USE
                    ),
                    new TaskFrameSetting(
                            new TaskFrameNo(2),
                            new TaskFrameName("Name02"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(3),
                            new TaskFrameName("Name03"),
                            UseAtr.USE

                    ), new TaskFrameSetting(
                            new TaskFrameNo(3),
                            new TaskFrameName("Name04"),
                            UseAtr.USE

                    )
            );
        }


    }
}

