package nts.uk.ctx.at.schedule.dom.schedule.alarmsetting.alarmlist.daily;

import org.eclipse.persistence.internal.xr.ValueObject;

import java.util.List;

/**
 * スケジュール日次のアラームチェック条件
 */
public class ScheduleAlarmCheckCond extends ValueObject {

    // スケジュール日次の任意抽出条件
    private List<String> listOptionalItem;

    // スケジュール日次の固定抽出条件
    private List<String> listFixedItem;
}
