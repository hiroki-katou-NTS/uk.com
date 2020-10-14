package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import lombok.*;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.WorkInformation;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 	反映イメージ
 */
public class ReflectionImage {

    private HashMap<GeneralDate, RefImageEachDay> day;

    // [C-1] 作る
    public static ReflectionImage create() {
        return new ReflectionImage(new HashMap<>());
    }

    // Private constructor
    private ReflectionImage(HashMap<GeneralDate, RefImageEachDay> days){
        this.day = days;
    }

    /**
     * [1] 週間勤務で追加する
     * @param date
     * @param workInformation
     */
    public void addByWeeklyWorking(GeneralDate date, WorkInformation workInformation){
        this.day.put(date, new RefImageEachDay(WorkCreateMethod.WEEKLY_WORK.value, workInformation, date));
    }

    /**
     * [2] 祝日で追加する
     * @param date
     * @param workInformation
     */
    public void addHolidays(GeneralDate date, WorkInformation workInformation) {
        val refEachDay = this.day.get(date);
        if (refEachDay != null && refEachDay.getWorkCreateMethod().value == WorkCreateMethod.WEEKLY_WORK.value) {
            return;
        }
        this.day.put(date, new RefImageEachDay(WorkCreateMethod.PUB_HOLIDAY.value, workInformation, date));
    }

    /**
     * [3] 勤務サイクルで追加する
     * @param date
     * @param workInformation
     * @return
     */
    public boolean addInWorkCycle(GeneralDate date, WorkInformation workInformation) {
        if (this.day.containsKey(date))
            return false;
        this.day.put(date, new RefImageEachDay(WorkCreateMethod.WORK_CYCLE.value, workInformation, date));
        return true;
    }

    /**
     * [4] 年月日順序のリストを返す
     * @return 	List<一日分の反映イメージ>
     */
    public List<RefImageEachDay> getListRefOrdByDate() {
        return this.day.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(i -> i.getValue()).collect(Collectors.toList());
    }

}
