package nts.uk.ctx.at.schedule.app.query.schedule.alarm.banholidaytogether;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BanHolidayTogetherQueryDto {
    /** コード */
    private String banHolidayTogetherCode;

    /** 名称 */
    private String banHolidayTogetherName;

    /** 稼働日の参照先 */
    private String workDayReference;

    /** 最低限出勤すべき人数 */
    private Integer minOfWorkingEmpTogether;
}
