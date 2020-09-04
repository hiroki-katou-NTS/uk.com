package nts.uk.ctx.at.schedule.dom.shift.workcycle;

import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.WeeklyWorkDayPattern;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.WorkdayPatternItem;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.weeklywrkday.DayOfWeek;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

import java.util.ArrayList;
import java.util.List;

public class WorkCycleTestHelper {

    public static class WorkCycleHelper {

        public static WorkCycle createWorkCycleForTest(List<WorkCycleInfo> infos) {
            return WorkCycle.create(
                    "cid",
                    "cyclecode",
                    "cyclename",
                    infos
            );
        }

        public static WeeklyWorkDayPattern createWeeklyWorkDayPattern() {
            List<WorkdayPatternItem> listWorkdayPatternItem = new ArrayList<>();
            listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.MONDAY, WorkdayDivision.WORKINGDAYS));
            listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.TUESDAY, WorkdayDivision.WORKINGDAYS));
            listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.WEDNESDAY, WorkdayDivision.WORKINGDAYS));
            listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.THURSDAY, WorkdayDivision.WORKINGDAYS));
            listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.FRIDAY, WorkdayDivision.WORKINGDAYS));
            listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SATURDAY, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL));
            listWorkdayPatternItem.add(new WorkdayPatternItem(DayOfWeek.SUNDAY, WorkdayDivision.NON_WORKINGDAY_INLAW));
            return new WeeklyWorkDayPattern(new CompanyId("00001"), listWorkdayPatternItem);
        }

    }

    public static class WorkCycleInfoHelper {

        public static List<WorkCycleInfo> createListForTest(int size) {
            List<WorkCycleInfo> result = new ArrayList<>();
            for (int i=0 ; i < size ; i++) {
                WorkCycleInfo item = WorkCycleInfo.create(
                        i,
                        new WorkInformation("0" + String.valueOf(i),
                                "0" + String.valueOf(i))
                );
                result.add(item);
            }
            return result;
        }

    }

}
