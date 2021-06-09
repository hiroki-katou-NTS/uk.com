package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;

import java.util.Arrays;
import java.util.List;

public class Helper {
    public static class MasterNameInfo {
        public static List<WorkplaceInfor> workplaceInfos(boolean isNameNull){
           return Arrays.asList(
                   new WorkplaceInfor("001", "hierarchy01", "wkpCode01", isNameNull ? null : "wkpName1",
                           "wkpName1", "", "workplaceExternalCode1")
//                new WorkplaceInfor("002", "hierarchy02", "wkpCode03", "wkpName2",
//                        "wkpName2", "", "wkpExternalCode2")
           );
        }

        public static List<EmployeeInfoImport> empInfos = Arrays.asList(
                new EmployeeInfoImport("001", "SID_CD_01", "SID_Name_1")
//                new EmployeeInfoImport("SID_ID_002", "SID_CD_02", "SID_Name_2")
        );

        public static List<TaskImport> tasks = Arrays.asList(
                new TaskImport("001", 01, "FRAME_NAME_1")
//                new TaskImport("TASK_CD_2", 03, "FRAME_NAME_2")
        );
    }
}
