package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Helper {
    public static class MasterNameInfo {
        public static List<WorkplaceInfor> workplaceInfos(boolean isCodeNull, boolean isNameNull) {
            return Arrays.asList(
                    new WorkplaceInfor("001", "hierarchy01", isCodeNull ? null : "wkpCode01", "wkpName1",
                            isNameNull ? null : "wkpName1", "", "workplaceExternalCode1")
            );
        }

        public static List<EmployeeInfoImport> empInfos(boolean isCodeNull, boolean isNameNull) {
            return Arrays.asList(
                    new EmployeeInfoImport("001", isCodeNull ? null : "CD_01", isNameNull ? null : "Name_1")
            );
        }

        public static List<TaskImport> tasks(boolean isCodeNull, boolean isNameNull) {
            return Arrays.asList(
                    new TaskImport(isCodeNull ? null : "001", 01, isNameNull ? null : "Name_1")
            );
        }

        public static List<WorkplaceInfor> affWkps = Collections.singletonList(new WorkplaceInfor("001", "hierarchy01", "wkpCode01", "wkpName1",
                "wkpName1", "", "workplaceExternalCode1"));

        public static List<WorkplaceInfor> wkps = Collections.singletonList(new WorkplaceInfor("002", "hierarchy02", "wkpCode02", "wkpName2",
                "wkpName2", "", "workplaceExternalCode2"));

        public static List<EmployeeInfoImport> emps = Collections.singletonList(new EmployeeInfoImport("003", "CD_03", "Name_3"));

        public static List<TaskImport> tasks1 = Collections.singletonList(new TaskImport("004", 01, "Name_4"));

        public static List<TaskImport> tasks2 = Collections.singletonList(new TaskImport("005", 01, "Name_5"));

        public static List<TaskImport> tasks3 = Collections.singletonList(new TaskImport("006", 01, "Name_6"));

        public static List<TaskImport> tasks4 = Collections.singletonList(new TaskImport("007", 01, "Name_7"));

        public static List<TaskImport> tasks5 = Collections.singletonList(new TaskImport("008", 01, "Name_8"));
    }

    public static List<WorkDetailData> workDetailDataList(int maxSize) {
        List<WorkDetailData> lstResult = new ArrayList<>();
        for (int i = 1; i <= maxSize; i++) {
            lstResult.add(new WorkDetailData(
                    "00" + i,
                    GeneralDate.today(),
                    i,
                    "00" + i,
                    "00" + i,
                    "00" + i,
                    "00" + i,
                    "00" + i,
                    "00" + i,
                    "00" + i,
                    i * 2
            ));
        }
        return lstResult;
    }
}
