package nts.uk.file.at.app.export.schedule.personalscheduleindividual;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ObjectPeriod;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalScheduleByIndividualQuery {

    /**
     * 社員ID
     */
    private String employeeId;

    /**
     * 社員コード
     */
    private String employeeCode;

    /**
     * 対象年月日
     */
    private GeneralDate date;

    /**
     * 対象期間 start date - end date
     */
    private ObjectPeriod period;

    /**
     * 開始曜日
     */
    private int startDate;

    /**
     * 週合計判定
     */
    private boolean isTotalDisplay;
}