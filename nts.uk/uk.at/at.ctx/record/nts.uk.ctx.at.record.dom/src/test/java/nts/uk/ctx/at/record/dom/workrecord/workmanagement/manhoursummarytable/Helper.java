package nts.uk.ctx.at.record.dom.workrecord.workmanagement.manhoursummarytable;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.function.alarmworkplace.EmployeeInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.workplace.config.info.WorkplaceInfor;

import java.util.*;

public class Helper {
    public static class MasterNameInfo {
        public static List<WorkplaceInfor> workplaceInfos(boolean isCodeNull, boolean isNameNull) {
            return Collections.singletonList(
                    new WorkplaceInfor("001", "hierarchy01", isCodeNull ? null : "wkpCode01", "wkpName1",
                            isNameNull ? null : "wkpName1", "", "workplaceExternalCode1")
            );
        }

        public static List<EmployeeInfoImport> empInfos(boolean isCodeNull, boolean isNameNull) {
            return Collections.singletonList(
                    new EmployeeInfoImport("001", isCodeNull ? null : "CD_01", isNameNull ? null : "Name_1")
            );
        }

        public static List<TaskImport> tasks(boolean isCodeNull, boolean isNameNull) {
            return Collections.singletonList(
                    new TaskImport(isCodeNull ? null : "001", 1, isNameNull ? null : "Name_1")
            );
        }

        public static List<WorkplaceInfor> affWkps = Collections.singletonList(new WorkplaceInfor("001", "hierarchy01", "wkpCode01", "wkpName1",
                "wkpName1", "", "workplaceExternalCode1"));

        public static List<WorkplaceInfor> wkps = Collections.singletonList(new WorkplaceInfor("002", "hierarchy02", "wkpCode02", "wkpName2",
                "wkpName2", "", "workplaceExternalCode2"));

        public static List<EmployeeInfoImport> emps = Collections.singletonList(new EmployeeInfoImport("003", "CD_03", "Name_3"));

        public static List<TaskImport> tasks1 = Collections.singletonList(new TaskImport("004", 1, "Name_4"));

        public static List<TaskImport> tasks2 = Collections.singletonList(new TaskImport("005", 1, "Name_5"));

        public static List<TaskImport> tasks3 = Collections.singletonList(new TaskImport("006", 1, "Name_6"));

        public static List<TaskImport> tasks4 = Collections.singletonList(new TaskImport("007", 1, "Name_7"));

        public static List<TaskImport> tasks5 = Collections.singletonList(new TaskImport("008", 1, "Name_8"));
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

    public static List<WorkDetailData> workDetailListV2(int maxSize) {
        List<WorkDetailData> lstResult = new ArrayList<>();
        for (int i = 1; i <= maxSize; i++) {
            lstResult.add(new WorkDetailData(
                    "SID_00" + i,
                    GeneralDate.today(),
                    i,
                    "AFF_WKP_00" + i,
                    "WKP_00" + i,
                    "TASK1_00" + i,
                    "TASK2_00" + i,
                    "TASK3_00" + i,
                    "TASK4_00" + i,
                    "TASK5_00" + i,
                    i * 2
            ));
        }
        return lstResult;
    }

    public static WorkDetailData workDetailData = new WorkDetailData(
            "SID_001",
            GeneralDate.today(),
            1,
            "AFF_WKP_001",
            "WKP_001",
            "TASK1_001",
            "TASK2_001",
            "TASK3_001",
            "TASK4_001",
            "TASK5_001",
            2);

    public static class ManHourSummaryTableOutputContent {

    }

    public static List<GeneralDate> dateList = Arrays.asList(
            GeneralDate.fromString("2021/06/01", "yyyy/MM/dd"),
            GeneralDate.fromString("2021/06/02", "yyyy/MM/dd"),
            GeneralDate.fromString("2021/06/03", "yyyy/MM/dd"));

    public static class verticalValueDaily {
        public static List<VerticalValueDaily> getList(int num) {
            return Arrays.asList(
                    new VerticalValueDaily(num, YearMonth.of(6), GeneralDate.fromString("2021/06/01", "yyyy/MM/dd")),
                    new VerticalValueDaily(num * 2, YearMonth.of(6), GeneralDate.fromString("2021/06/02", "yyyy/MM/dd")),
                    new VerticalValueDaily(num * 3, YearMonth.of(6), GeneralDate.fromString("2021/06/03", "yyyy/MM/dd")));
        }
    }

    public static class summaryItemDetail {
        public static List<SummaryItemDetail> getList(int maxSize) {
            List<SummaryItemDetail> lstSummaryItemDetail = new ArrayList<>();
            List<SummaryItemDetail> lstChildSummaryItemDetail = new ArrayList<>();
            for (int i = 1; i <= maxSize; i++) {
                lstChildSummaryItemDetail.add(new SummaryItemDetail(
                        "0" + i + 1,
                        new DisplayInformation("0" + i + 1, "NAME" + i + 1),
                        Collections.emptyList(),
                        Helper.verticalValueDaily.getList(i),
                        Optional.of(i * 2)
                ));

                lstSummaryItemDetail.add(new SummaryItemDetail(
                        "0" + i,
                        new DisplayInformation("0" + i, "NAME" + i),
                        lstChildSummaryItemDetail,
                        Helper.verticalValueDaily.getList(i),
                        Optional.of(i * 2)
                ));
            }
            return lstSummaryItemDetail;
        }
    }


}
