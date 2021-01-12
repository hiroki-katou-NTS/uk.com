package nts.uk.screen.at.app.command.kbt.outputexecutionhistory;

import lombok.Builder;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogGetMemento;
import nts.uk.ctx.at.schedule.dom.executionlog.ScheduleErrorLogSetMemento;

@Data
@Builder
public class ScheduleErrorLogDto implements ScheduleErrorLogGetMemento, ScheduleErrorLogSetMemento {
    /** The error content. */
    // エラー内容
    private String errorContent;

    /** The execution id. */
    // 実行ID
    private String executionId;

    /** The date. */
    // 年月日
    private GeneralDate date;

    /** The employee id. */
    // 社員ID
    private String employeeId;
}
