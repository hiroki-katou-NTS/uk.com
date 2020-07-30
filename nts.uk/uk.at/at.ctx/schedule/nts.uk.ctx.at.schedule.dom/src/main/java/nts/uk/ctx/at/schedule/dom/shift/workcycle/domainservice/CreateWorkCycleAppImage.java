package nts.uk.ctx.at.schedule.dom.shift.workcycle.domainservice;

import lombok.val;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WeeklyWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.workcycle.WorkCycle;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.service.Period;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;
import java.util.Optional;

/**
 * 勤務サイクルの適用イメージを作成する
 */
public class CreateWorkCycleAppImage {

    /**
     * [1] 作成する
     * @param require
     * @param period
     * @param config
     */
    public void create(Require require, Period period, WorkCycleRefSetting config) {
        // Pending wait Domain 週間勤務設定 Update new design

    }

    /**
     * [prv-1] 週間勤務でイメージを作成する
     * @param require
     * @param reflectionImage
     * @param createPeriod
     * @param config
     */
    private void createImageWeekly(Require require, ReflectionImage reflectionImage, Period createPeriod, WorkCycleRefSetting config) {
        val weeklyWorkSet = require.getWeeklyWorkSetting(AppContexts.user().companyId());
        // Pending wait Domain 週間勤務設定 Update new design
        weeklyWorkSet.stream().forEach(i -> {

        });
    }

    public static interface Require {

        List<WeeklyWorkSetting> getWeeklyWorkSetting(String cid);

        void getHoliday();

        Optional<WorkCycle> getWorkCycle(String cid, String code);

    }

}
