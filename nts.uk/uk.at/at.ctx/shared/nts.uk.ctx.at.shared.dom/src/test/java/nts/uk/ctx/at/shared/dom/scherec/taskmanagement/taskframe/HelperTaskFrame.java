package nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe;

import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.personallaborcondition.UseAtr;

import java.util.Arrays;
import java.util.List;

@Getter
public class HelperTaskFrame {
    public static List<TaskFrameSetting> createListDomainSorted() {
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
                        new TaskFrameNo(4),
                        new TaskFrameName("Name04"),
                        UseAtr.USE

                ), new TaskFrameSetting(
                        new TaskFrameNo(5),
                        new TaskFrameName("Name05"),
                        UseAtr.USE

                )
        );
    }   public static List<TaskFrameSetting> createListDomain() {
        return Arrays.asList(
                new TaskFrameSetting(
                        new TaskFrameNo(4),
                        new TaskFrameName("Name04"),
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
                        new TaskFrameNo(1),
                        new TaskFrameName("Name01"),
                        UseAtr.USE

                ), new TaskFrameSetting(
                        new TaskFrameNo(2),
                        new TaskFrameName("Name02"),
                        UseAtr.USE

                )
        );
    }
    public static List<TaskFrameSetting> createListDomain04() {
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
                        new TaskFrameNo(4),
                        new TaskFrameName("Name04"),
                        UseAtr.NOTUSE

                ), new TaskFrameSetting(
                        new TaskFrameNo(5),
                        new TaskFrameName("Name05"),
                        UseAtr.USE

                )
        );
    }

    public static List<TaskFrameSetting> createListDomain03() {
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
                        UseAtr.NOTUSE

                ), new TaskFrameSetting(
                        new TaskFrameNo(4),
                        new TaskFrameName("Name04"),
                        UseAtr.USE

                ), new TaskFrameSetting(
                        new TaskFrameNo(5),
                        new TaskFrameName("Name05"),
                        UseAtr.USE

                )
        );
    }

    public static List<TaskFrameSetting> createListDomain02() {
        return Arrays.asList(
                new TaskFrameSetting(
                        new TaskFrameNo(1),
                        new TaskFrameName("Name01"),
                        UseAtr.USE
                ),
                new TaskFrameSetting(
                        new TaskFrameNo(2),
                        new TaskFrameName("Name02"),
                        UseAtr.NOTUSE

                ), new TaskFrameSetting(
                        new TaskFrameNo(3),
                        new TaskFrameName("Name03"),
                        UseAtr.USE

                ), new TaskFrameSetting(
                        new TaskFrameNo(4),
                        new TaskFrameName("Name04"),
                        UseAtr.USE

                ), new TaskFrameSetting(
                        new TaskFrameNo(5),
                        new TaskFrameName("Name05"),
                        UseAtr.USE

                )
        );
    }

    public static List<TaskFrameSetting> createListDomain01() {
        return Arrays.asList(
                new TaskFrameSetting(
                        new TaskFrameNo(1),
                        new TaskFrameName("Name01"),
                        UseAtr.NOTUSE
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
                        new TaskFrameNo(4),
                        new TaskFrameName("Name04"),
                        UseAtr.USE

                ), new TaskFrameSetting(
                        new TaskFrameNo(5),
                        new TaskFrameName("Name05"),
                        UseAtr.USE

                )
        );
    }

    public static List<TaskFrameSetting> createListDomainDuplicate() {
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
