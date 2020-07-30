package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
/**
 * 	反映イメージ
 */
public class ReflectionImage {

    private HashMap<GeneralDate, RefImageEachDay> day;

    // [C-1] 作る

    /**
     * [1] 週間勤務で追加する
     * @param date
     * @param workInformation
     */
    public void addByWeeklyWorking(GeneralDate date, WorkInformation workInformation){
        val refEachDay = this.getDay().get(date);
        if (refEachDay != null && refEachDay.getWorkCreateMethod().value == WorkCreateMethod.PUB_HOLIDAY.value) {
            return;
        }
        this.getDay().put(date, new RefImageEachDay(WorkCreateMethod.WEEKLY_WORK.value, workInformation, date));
    }

    /**
     * [2] 祝日で追加する
     * @param date
     * @param workInformation
     */
    public void addHolidays(GeneralDate date, WorkInformation workInformation) {
        val refEachDay = this.getDay().get(date);
        if (refEachDay != null && refEachDay.getWorkCreateMethod().value == WorkCreateMethod.WEEKLY_WORK.value) {
            return;
        }
        this.getDay().put(date, new RefImageEachDay(WorkCreateMethod.WEEKLY_WORK.value, workInformation, date));
    }

    /**
     * [3] 勤務サイクルで追加する
     * @param date
     * @param workInformation
     * @return
     */
    public boolean addInWorkCycle(GeneralDate date, WorkInformation workInformation) {
        if (this.getDay().containsKey(date))
            return false;
        this.getDay().put(date, new RefImageEachDay(WorkCreateMethod.WORK_CYCLE.value, workInformation, date));
        return true;
    }

    /**
     * [4] 年月日順序のリストを返す
     * @return 	List<一日分の反映イメージ>
     */
    public List<RefImageEachDay> getListRefOrdByDate() {
        return this.getDay().entrySet().stream().sorted(Map.Entry.comparingByKey()).map(i -> i.getValue()).collect(Collectors.toList());
    }

}
